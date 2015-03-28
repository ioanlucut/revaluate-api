package com.revaluate.account.service;

import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.EmailToken;
import com.revaluate.account.persistence.EmailTokenRepository;
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
import com.revaluate.email.SendEmailService;
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
    private EmailTokenRepository emailTokenRepository;

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private EmailAsyncSender emailAsyncSender;

    @Override
    public boolean isUnique(String email) {
        return !userRepository.findOneByEmail(email).isPresent();
    }

    @Override
    public UserDTO create(UserDTO userDTO) throws UserException {
        if (userRepository.findOneByEmail(userDTO.getEmail()).isPresent()) {
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
        EmailToken createEmailToken = TokenGenerator.generateTokenFor(savedUser, EmailType.CREATED_ACCOUNT);
        EmailToken savedCreateEmailToken = emailTokenRepository.save(createEmailToken);

        //-----------------------------------------------------------------
        // Try to send email asyncronous
        //-----------------------------------------------------------------
        LOGGER.info("11111111111111111111111" + Thread.currentThread().getName());

        emailAsyncSender.tryToSendEmail(savedCreateEmailToken);

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
        Optional<Currency> byCurrencyCode = currencyRepository.findOneByCurrencyCode(userDTO.getCurrency().getCurrencyCode());
        byCurrencyCode.orElseThrow(() -> new UserException("The given currency does not exists"));
        foundUser.setCurrency(byCurrencyCode.get());

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
        emailTokenRepository.removeByUserId(userId);
        userRepository.delete(userId);
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

        Optional<EmailToken> oneByUserIdAndValidatedFalse = emailTokenRepository.findOneByEmailTypeAndUserIdAndValidatedFalse(EmailType.RESET_PASSWORD, user.getId());

        if (oneByUserIdAndValidatedFalse.isPresent()) {
            emailTokenRepository.delete(oneByUserIdAndValidatedFalse.get());
        }
        //-----------------------------------------------------------------
        // Generate a new reset email token and save it
        //-----------------------------------------------------------------
        EmailToken resetEmailToken = TokenGenerator.generateTokenFor(user, EmailType.RESET_PASSWORD);
        emailTokenRepository.save(resetEmailToken);
    }

    @Override
    public void validateResetPasswordToken(String email, String token) throws UserException {
        Optional<User> byEmail = userRepository.findOneByEmail(email);
        User user = byEmail.orElseThrow(() -> new UserException("No matching of this email"));

        //-----------------------------------------------------------------
        // Try to find a matching email token
        //-----------------------------------------------------------------
        Optional<EmailToken> oneByUserIdAndValidatedFalse = emailTokenRepository.findOneByEmailTypeAndUserIdAndValidatedFalse(EmailType.RESET_PASSWORD, user.getId());
        EmailToken emailToken = oneByUserIdAndValidatedFalse.orElseThrow(() -> new UserException("Token is invalid."));

        //-----------------------------------------------------------------
        // If invalid, delete and throw exception
        //-----------------------------------------------------------------
        if (!emailToken.getToken().equals(token)) {
            emailTokenRepository.delete(emailToken);

            throw new UserException("Token is invalid.");
        }
    }

    @Override
    public void resetPassword(ResetPasswordDTO resetPasswordDTO, String email, String token) throws UserException {
        // Perform again the token validation
        this.validateResetPasswordToken(email, token);

        if (!resetPasswordDTO.getPassword().equals(resetPasswordDTO.getPasswordConfirmation())) {
            throw new UserException("New password should match new password confirmation");
        }

        Optional<User> byEmail = userRepository.findOneByEmail(email);
        User user = byEmail.orElseThrow(() -> new UserException("No matching of this email"));

        //-----------------------------------------------------------------
        // Try to find a matching email token and delete otherwise
        //-----------------------------------------------------------------
        Optional<EmailToken> oneByUserIdAndValidatedFalse = emailTokenRepository.findOneByEmailTypeAndUserIdAndValidatedFalse(EmailType.RESET_PASSWORD, user.getId());
        if (oneByUserIdAndValidatedFalse.isPresent()) {
            emailTokenRepository.delete(oneByUserIdAndValidatedFalse.get());
        }

        user.setPassword(BCrypt.hashpw(resetPasswordDTO.getPassword(), BCrypt.gensalt()));
        userRepository.save(user);
    }
}