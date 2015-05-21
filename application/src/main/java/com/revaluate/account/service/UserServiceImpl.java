package com.revaluate.account.service;

import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.Email;
import com.revaluate.account.persistence.EmailRepository;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.account.utils.TokenGenerator;
import com.revaluate.category.persistence.CategoryRepository;
import com.revaluate.currency.persistence.Currency;
import com.revaluate.currency.persistence.CurrencyRepository;
import com.revaluate.domain.account.*;
import com.revaluate.domain.email.EmailType;
import com.revaluate.expense.persistence.ExpenseRepository;
import com.revaluate.payment.persistence.PaymentStatusRepository;
import org.dozer.DozerBeanMapper;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
public class UserServiceImpl implements UserService {

    public static final String USER_DTO__UPDATE = "UserDTO__Update";

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private EmailAsyncSender emailAsyncSender;

    @Override
    public boolean isUnique(String email) {
        return !userRepository.findOneByEmail(email).isPresent();
    }

    @Override
    @Transactional
    public UserDTO create(UserDTO userDTO) throws UserException {
        if (!isUnique(userDTO.getEmail())) {

            throw new UserException("Email is not unique");
        }

        //-----------------------------------------------------------------
        // Try to find the currency
        //-----------------------------------------------------------------
        Optional<Currency> byCurrencyCode = currencyRepository.findOneByCurrencyCode(userDTO.getCurrency().getCurrencyCode());
        byCurrencyCode.orElseThrow(() -> new UserException("The given currency does not exists"));

        User user = dozerBeanMapper.map(userDTO, User.class);
        //-----------------------------------------------------------------
        // Set the found currency
        //-----------------------------------------------------------------
        user.setCurrency(byCurrencyCode.get());

        //-----------------------------------------------------------------
        // Hash the password and set
        //-----------------------------------------------------------------
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

        //-----------------------------------------------------------------
        // Set status as trial
        //-----------------------------------------------------------------
        user.setUserSubscriptionStatus(UserSubscriptionStatus.TRIAL);

        //-----------------------------------------------------------------
        // Save the user
        //-----------------------------------------------------------------
        User savedUser = userRepository.save(user);

        //-----------------------------------------------------------------
        // Generate a new create email token
        //-----------------------------------------------------------------
        Email createEmail = TokenGenerator.buildEmail(savedUser, EmailType.CREATED_ACCOUNT);
        Email savedCreateEmail = emailRepository.save(createEmail);

        //-----------------------------------------------------------------
        // Try to send email async
        //-----------------------------------------------------------------
        emailAsyncSender.tryToSendEmail(savedCreateEmail);

        return dozerBeanMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO login(LoginDTO loginDTO) throws UserException {
        Optional<User> byEmail = userRepository.findOneByEmail(loginDTO.getEmail());
        User foundUser = byEmail.orElseThrow(() -> new UserException("Invalid email or password"));

        if (!BCrypt.checkpw(loginDTO.getPassword(), foundUser.getPassword())) {
            throw new UserException("Invalid email or password");
        }

        return dozerBeanMapper.map(foundUser, UserDTO.class);
    }

    @Override
    public UserDTO update(UserDTO userDTO, int userId) throws UserException {
        User foundUser = userRepository.findOne(userId);
        if (foundUser == null) {

            throw new UserException("User does not exist");
        }

        //-----------------------------------------------------------------
        // Try to find the currency
        //-----------------------------------------------------------------
        if (userDTO.getCurrency() != null) {
            Optional<Currency> byCurrencyCode = currencyRepository.findOneByCurrencyCode(userDTO.getCurrency().getCurrencyCode());
            foundUser.setCurrency(byCurrencyCode.orElseThrow(() -> new UserException("The given currency does not exists")));
        }

        //-----------------------------------------------------------------
        // Update the user accordingly to DTO
        //-----------------------------------------------------------------
        dozerBeanMapper.map(userDTO, foundUser, USER_DTO__UPDATE);

        User updatedUser = userRepository.save(foundUser);
        return dozerBeanMapper.map(updatedUser, UserDTO.class);
    }

    @Override
    public UserDTO getUserDetails(int userId) throws UserException {
        User foundUser = userRepository.findOne(userId);

        if (foundUser == null) {
            throw new UserException("Could not retrieve user details.");
        }

        return dozerBeanMapper.map(foundUser, UserDTO.class);
    }

    @Override
    @Transactional
    public void remove(int userId) {
        //-----------------------------------------------------------------
        // First, remove all its payment plan, email tokens, then expenses, then categories, then the user
        //-----------------------------------------------------------------
        paymentStatusRepository.removeByUserId(userId);
        emailRepository.removeByUserId(userId);
        expenseRepository.removeByUserId(userId);
        categoryRepository.removeByUserId(userId);
        userRepository.delete(userId);
    }

    @Override
    @Transactional
    public void validateConfirmationEmailToken(String email, String token) throws UserException {
        Optional<User> optionalUserFoundByEmail = userRepository.findOneByEmail(email);
        User user = optionalUserFoundByEmail.orElseThrow(() -> new UserException("No matching of this email"));

        //-----------------------------------------------------------------
        // Try to find a matching email token
        //-----------------------------------------------------------------
        Optional<Email> optionalByUserIdAndTokenValidatedFalse = emailRepository.findOneByEmailTypeAndUserIdAndToken(EmailType.CREATED_ACCOUNT, user.getId(), token);
        Email emailEntry = optionalByUserIdAndTokenValidatedFalse.orElseThrow(() -> new UserException("Confirmation email token is invalid."));

        //-----------------------------------------------------------------
        // If already validated, just return
        //-----------------------------------------------------------------
        if (emailEntry.isTokenValidated()) {
            return;
        }

        //-----------------------------------------------------------------
        // Otherwise, set token as validated
        //-----------------------------------------------------------------
        emailEntry.setTokenValidated(Boolean.TRUE);
        emailRepository.save(emailEntry);

        //-----------------------------------------------------------------
        // If already set as confirmed, return
        //-----------------------------------------------------------------
        if (user.isEmailConfirmed()) {
            return;
        }

        //-----------------------------------------------------------------
        // Set use as having email confirmed
        //-----------------------------------------------------------------
        user.setEmailConfirmed(Boolean.TRUE);
        userRepository.save(user);
    }

    @Override
    public void requestConfirmationEmail(String email) throws UserException {
        Optional<User> byEmail = userRepository.findOneByEmail(email);
        User user = byEmail.orElseThrow(() -> new UserException("No matching of this email"));

        //-----------------------------------------------------------------
        // Generate a new reset email token and save it
        //-----------------------------------------------------------------
        Email confirmEmail = TokenGenerator.buildEmail(user, EmailType.CREATED_ACCOUNT);

        //-----------------------------------------------------------------
        // Try to send email async
        //-----------------------------------------------------------------
        emailAsyncSender.tryToSendEmail(confirmEmail);

        emailRepository.save(confirmEmail);
    }

    @Override
    public UserDTO updatePassword(UpdatePasswordDTO updatePasswordDTO, int currentUserId) throws UserException {
        if (!updatePasswordDTO.getNewPassword().equals(updatePasswordDTO.getNewPasswordConfirmation())) {
            throw new UserException("New password should match new password confirmation");
        }

        User existingUser = userRepository.findOne(currentUserId);
        if (existingUser == null) {
            throw new UserException("Invalid email or password");
        }

        if (!BCrypt.checkpw(updatePasswordDTO.getOldPassword(), existingUser.getPassword())) {
            throw new UserException("Current password is wrong");
        }

        existingUser.setPassword(BCrypt.hashpw(updatePasswordDTO.getNewPassword(), BCrypt.gensalt()));

        User updatedUser = userRepository.save(existingUser);
        return dozerBeanMapper.map(updatedUser, UserDTO.class);
    }

    @Override
    @Transactional
    public void requestResetPassword(String email) throws UserException {
        Optional<User> byEmail = userRepository.findOneByEmail(email);
        User user = byEmail.orElseThrow(() -> new UserException("No matching of this email"));

        //-----------------------------------------------------------------
        // Remove all existing tokens
        //-----------------------------------------------------------------
        emailRepository.deleteAllByEmailTypeAndUserId(EmailType.RESET_PASSWORD, user.getId());

        //-----------------------------------------------------------------
        // Generate a new reset email token and save it
        //-----------------------------------------------------------------
        Email resetEmail = TokenGenerator.buildEmail(user, EmailType.RESET_PASSWORD);

        //-----------------------------------------------------------------
        // Try to send email async
        //-----------------------------------------------------------------
        emailAsyncSender.tryToSendEmail(resetEmail);

        emailRepository.save(resetEmail);
    }

    @Override
    public void validateResetPasswordToken(String email, String token) throws UserException {
        Optional<User> byEmail = userRepository.findOneByEmail(email);
        User user = byEmail.orElseThrow(() -> new UserException("No matching of this email"));

        //-----------------------------------------------------------------
        // Try to find a matching email token
        //-----------------------------------------------------------------
        Optional<Email> oneByUserIdAndTokenValidatedFalse = emailRepository.findOneByEmailTypeAndUserIdAndToken(EmailType.RESET_PASSWORD, user.getId(), token);
        Email emailToken = oneByUserIdAndTokenValidatedFalse.orElseThrow(() -> new UserException("Token is invalid."));

        //-----------------------------------------------------------------
        // If already validated, just return
        //-----------------------------------------------------------------
        if (emailToken.isTokenValidated()) {
            return;
        }

        //-----------------------------------------------------------------
        // Set token as validated
        //-----------------------------------------------------------------
        emailToken.setTokenValidated(Boolean.TRUE);
        emailRepository.save(emailToken);
    }

    @Override
    public void resetPassword(ResetPasswordDTO resetPasswordDTO, String email, String token) throws UserException {
        if (!resetPasswordDTO.getPassword().equals(resetPasswordDTO.getPasswordConfirmation())) {
            throw new UserException("New password should match new password confirmation");
        }

        Optional<User> byEmail = userRepository.findOneByEmail(email);
        User user = byEmail.orElseThrow(() -> new UserException("No matching of this email"));

        //-----------------------------------------------------------------
        // Try to find a matching email token which is validated
        //-----------------------------------------------------------------
        Optional<Email> oneByUserIdAndTokenValidatedFalse = emailRepository.findOneByEmailTypeAndUserIdAndTokenAndTokenValidatedTrue(EmailType.RESET_PASSWORD, user.getId(), token);
        oneByUserIdAndTokenValidatedFalse.orElseThrow(() -> new UserException("Token is invalid."));

        //-----------------------------------------------------------------
        // Finally, reset password and save user
        //-----------------------------------------------------------------
        user.setPassword(BCrypt.hashpw(resetPasswordDTO.getPassword(), BCrypt.gensalt()));
        userRepository.save(user);
    }

    @Override
    public UserDTO updateCurrency(UserDTO userDTO, int userId) throws UserException {
        User foundUser = userRepository.findOne(userId);
        if (foundUser == null) {
            throw new UserException("User does not exist");
        }

        //-----------------------------------------------------------------
        // Try to find the currency
        //-----------------------------------------------------------------
        Optional<Currency> byCurrencyCode = currencyRepository.findOneByCurrencyCode(userDTO.getCurrency().getCurrencyCode());
        byCurrencyCode.orElseThrow(() -> new UserException("The given currency does not exists"));
        foundUser.setCurrency(byCurrencyCode.get());

        User updatedUser = userRepository.save(foundUser);
        return dozerBeanMapper.map(updatedUser, UserDTO.class);
    }
}