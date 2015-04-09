package com.revaluate.account.service;

import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.Email;
import com.revaluate.account.persistence.EmailRepository;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.account.utils.TokenGenerator;
import com.revaluate.currency.persistence.Currency;
import com.revaluate.currency.persistence.CurrencyRepository;
import com.revaluate.domain.account.LoginDTO;
import com.revaluate.domain.account.ResetPasswordDTO;
import com.revaluate.domain.account.UpdatePasswordDTO;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.email.EmailType;
import org.dozer.DozerBeanMapper;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    private EmailAsyncSender emailAsyncSender;

    @Override
    public boolean isUnique(String email) {
        return !userRepository.findOneByEmail(email).isPresent();
    }

    @Override
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
            throw new UserException();
        }

        //-----------------------------------------------------------------
        // Try to find the currency
        //-----------------------------------------------------------------
        if (userDTO.getCurrency() != null) {
            Optional<Currency> byCurrencyCode = currencyRepository.findOneByCurrencyCode(userDTO.getCurrency().getCurrencyCode());
            byCurrencyCode.orElseThrow(() -> new UserException("The given currency does not exists"));
            foundUser.setCurrency(byCurrencyCode.get());
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

        if (foundUser != null) {

            return dozerBeanMapper.map(foundUser, UserDTO.class);
        }

        throw new UserException("Could not retrieve user details.");
    }

    @Override
    public void remove(int userId) {
        //-----------------------------------------------------------------
        // First, remove all its email tokens, then the user
        //-----------------------------------------------------------------
        emailRepository.removeByUserId(userId);
        userRepository.delete(userId);
    }

    @Override
    public void validateConfirmationEmailToken(String email, String token) throws UserException {
        Optional<User> byEmail = userRepository.findOneByEmail(email);
        User user = byEmail.orElseThrow(() -> new UserException("No matching of this email"));

        //-----------------------------------------------------------------
        // Try to find a matching email token
        //-----------------------------------------------------------------
        Optional<Email> oneByUserIdAndTokenValidatedFalse = emailRepository.findOneByEmailTypeAndUserIdAndToken(EmailType.CREATED_ACCOUNT, user.getId(), token);
        Email emailToken = oneByUserIdAndTokenValidatedFalse.orElseThrow(() -> new UserException("Confirmation email token is invalid."));

        //-----------------------------------------------------------------
        // If already validated, just return
        //-----------------------------------------------------------------
        if (emailToken.isTokenValidated()) {
            return;
        }

        //-----------------------------------------------------------------
        // Otherwise, set token as validated
        //-----------------------------------------------------------------
        emailToken.setTokenValidated(Boolean.TRUE);
        emailRepository.save(emailToken);
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
}