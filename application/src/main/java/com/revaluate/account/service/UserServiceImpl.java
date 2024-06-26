package com.revaluate.account.service;

import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserPartialUpdateEnum;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.account.utils.TokenGenerator;
import com.revaluate.category.persistence.CategoryRepository;
import com.revaluate.config.AppConfig;
import com.revaluate.core.annotations.EmailSenderQualifier;
import com.revaluate.currency.persistence.Currency;
import com.revaluate.currency.persistence.CurrencyRepository;
import com.revaluate.domain.account.*;
import com.revaluate.domain.email.EmailType;
import com.revaluate.domain.payment.PaymentStatusDTO;
import com.revaluate.email.persistence.EmailFeedback;
import com.revaluate.email.persistence.EmailRepository;
import com.revaluate.email.persistence.EmailToken;
import com.revaluate.email.persistence.EmailTokenRepository;
import com.revaluate.email.service.EmailAsyncSender;
import com.revaluate.expense.persistence.ExpenseRepository;
import com.revaluate.payment.persistence.PaymentStatusRepository;
import com.revaluate.payment.service.PaymentStatusService;
import org.dozer.DozerBeanMapper;
import org.joda.time.LocalDateTime;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private EmailTokenRepository emailTokenRepository;

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    @Autowired
    private PaymentStatusService paymentStatusService;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    @EmailSenderQualifier(value = EmailSenderQualifier.EmailSenderType.TO_USER)
    private EmailAsyncSender<EmailToken> emailTokenAsyncSender;

    @Autowired
    @EmailSenderQualifier(value = EmailSenderQualifier.EmailSenderType.FEEDBACK)
    private EmailAsyncSender<EmailFeedback> feedbackEmailAsyncSender;

    @Override
    public boolean isUnique(String email) {
        return !userRepository.findOneByEmailIgnoreCase(email).isPresent();
    }

    private User prepareCandidateUser(UserDTO userDTO) throws UserException {
        if (!isUnique(userDTO.getEmail())) {

            throw new UserException("Email is not unique");
        }

        //-----------------------------------------------------------------
        // Try to find the currency
        //-----------------------------------------------------------------
        Currency byCurrencyCode = currencyRepository
                .findOneByCurrencyCode(userDTO.getCurrency().getCurrencyCode())
                .orElseThrow(() -> new UserException("The given currency does not exists"));

        User user = dozerBeanMapper.map(userDTO, User.class);
        //-----------------------------------------------------------------
        // Set the found currency
        //-----------------------------------------------------------------
        user.setCurrency(byCurrencyCode);

        //-----------------------------------------------------------------
        // Set status as trial and end trial date.
        //-----------------------------------------------------------------
        user.setUserSubscriptionStatus(UserSubscriptionStatus.TRIAL);
        user.setEndTrialDate(LocalDateTime.now().plusDays(AppConfig.TRIAL_DAYS));
        return user;
    }

    @Override
    @Transactional
    public UserDTO create(UserDTO userDTO) throws UserException {
        User user = prepareCandidateUser(userDTO);

        //-----------------------------------------------------------------
        // Standard user type
        //-----------------------------------------------------------------
        user.setUserType(UserType.SIGN_UP);

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
        EmailToken createEmail = TokenGenerator.buildEmail(savedUser, EmailType.CREATED_ACCOUNT);
        EmailToken savedCreateEmail = emailTokenRepository.save(createEmail);

        //-----------------------------------------------------------------
        // Try to send email async
        //-----------------------------------------------------------------
        emailTokenAsyncSender.tryToSendEmail(savedCreateEmail);

        return dozerBeanMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO createViaOauth(UserDTO userDTO) throws UserException {
        User user = prepareCandidateUser(userDTO);

        //-----------------------------------------------------------------
        // Users coming via OAUTH do not have to confirm they email
        //-----------------------------------------------------------------
        user.setEmailConfirmed(Boolean.TRUE);

        //-----------------------------------------------------------------
        // Set this flag to be able to know if user comes via OAUTH
        //-----------------------------------------------------------------
        user.setConnectedViaOauth(Boolean.TRUE);

        //-----------------------------------------------------------------
        // Save the user
        //-----------------------------------------------------------------
        User savedUser = userRepository.save(user);

        return dozerBeanMapper.map(savedUser, UserDTO.class);
    }

    @Override
    public UserDTO login(LoginDTO loginDTO) throws UserException {
        User foundUser = userRepository
                .findOneByEmailIgnoreCaseAndUserType(loginDTO.getEmail(), UserType.SIGN_UP)
                .orElseThrow(() -> new UserException("Invalid email or password"));

        if (!BCrypt.checkpw(loginDTO.getPassword(), foundUser.getPassword())) {

            throw new UserException("Invalid email or password");
        }

        return dozerBeanMapper.map(foundUser, UserDTO.class);
    }

    @Override
    public UserDTO loginViaOauth(String email, UserType userType) throws UserException {
        User foundUser = userRepository
                .findOneByEmailIgnoreCase(email)
                .orElseThrow(() -> new UserException("Invalid email or password"));

        if (!foundUser.isConnectedViaOauth() && !foundUser.isEmailConfirmed()) {

            throw new UserException("User is not using oauth and does not have email confirmed");
        }

        return dozerBeanMapper.map(foundUser, UserDTO.class);
    }

    @Override
    public UserDTO update(UserDTO userDTO, int userId, UserPartialUpdateEnum userPartialUpdateEnum) throws UserException {
        User foundUser = userRepository
                .findOneById(userId)
                .orElseThrow(() -> new UserException("User does not exist"));

        //-----------------------------------------------------------------
        // Try to find the currency
        //-----------------------------------------------------------------
        if (userDTO.getCurrency() != null) {
            Currency currency = currencyRepository
                    .findOneByCurrencyCode(userDTO.getCurrency().getCurrencyCode())
                    .orElseThrow(() -> new UserException("The given currency does not exists"));

            foundUser.setCurrency(currency);
        }

        //-----------------------------------------------------------------
        // Update the user accordingly to DTO
        //-----------------------------------------------------------------
        dozerBeanMapper.map(userDTO, foundUser, userPartialUpdateEnum.getMapId());

        User updatedUser = userRepository.save(foundUser);
        return dozerBeanMapper.map(updatedUser, UserDTO.class);
    }

    @Override
    public UserDTO getUserDetails(int userId) throws UserException {
        User foundUser = userRepository
                .findOneById(userId)
                .orElseThrow(() -> new UserException("Could not retrieve user details."));

        return dozerBeanMapper.map(foundUser, UserDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(int userId) throws Exception {

        //-----------------------------------------------------------------
        // If there is a payment status defined, fetch it
        //-----------------------------------------------------------------
        Optional<PaymentStatusDTO> paymentStatusOptional = Optional.empty();
        if (paymentStatusService.isPaymentStatusDefined(userId)) {

            paymentStatusOptional = Optional.ofNullable(paymentStatusService.findPaymentStatus(userId));
        }

        //-----------------------------------------------------------------
        // First, remove all its payment plan, email tokens, then expenses, then categories, then the user
        //-----------------------------------------------------------------
        paymentStatusRepository.removeByUserId(userId);
        emailRepository.removeByUserId(userId);
        expenseRepository.removeByUserId(userId);
        categoryRepository.removeByUserId(userId);
        userRepository.delete(userId);

        //-----------------------------------------------------------------
        // Finally, try to delete the customer from Braintree. It has to be the last action as we rollback database
        // changes if this call is not successful.
        //-----------------------------------------------------------------
        if (paymentStatusOptional.isPresent()) {
            paymentStatusService.deleteCustomerWithId(paymentStatusOptional.get().getCustomerId());
        }
    }

    @Override
    @Transactional
    public void validateConfirmationEmailToken(String email, String token) throws UserException {
        User user = userRepository
                .findOneByEmailIgnoreCaseAndUserType(email, UserType.SIGN_UP)
                .orElseThrow(() -> new UserException("No matching of this email"));

        //-----------------------------------------------------------------
        // Try to find a matching email token
        //-----------------------------------------------------------------
        EmailToken emailEntry = emailTokenRepository
                .findOneByEmailTypeAndUserIdAndToken(EmailType.CREATED_ACCOUNT, user.getId(), token)
                .orElseThrow(() -> new UserException("Confirmation email token is invalid."));

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
        emailTokenRepository.save(emailEntry);

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
        User user = userRepository
                .findOneByEmailIgnoreCaseAndUserType(email, UserType.SIGN_UP)
                .orElseThrow(() -> new UserException("No matching of this email"));

        //-----------------------------------------------------------------
        // Generate a new reset email token and save it
        //-----------------------------------------------------------------
        EmailToken confirmEmail = TokenGenerator.buildEmail(user, EmailType.CREATED_ACCOUNT);

        //-----------------------------------------------------------------
        // Try to send email async
        //-----------------------------------------------------------------
        emailTokenAsyncSender.tryToSendEmail(confirmEmail);

        emailTokenRepository.save(confirmEmail);
    }

    @Override
    public UserDTO updatePassword(UpdatePasswordDTO updatePasswordDTO, int userId) throws UserException {
        if (!updatePasswordDTO.getNewPassword().equals(updatePasswordDTO.getNewPasswordConfirmation())) {

            throw new UserException("New password should match new password confirmation");
        }

        User existingUser = userRepository
                .findOneById(userId)
                .orElseThrow(() -> new UserException("Invalid email or password"));

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
        User user = userRepository
                .findOneByEmailIgnoreCaseAndUserType(email, UserType.SIGN_UP)
                .orElseThrow(() -> new UserException("No matching of this email"));

        //-----------------------------------------------------------------
        // Remove all existing tokens
        //-----------------------------------------------------------------
        emailRepository.deleteAllByEmailTypeAndUserId(EmailType.RESET_PASSWORD, user.getId());

        //-----------------------------------------------------------------
        // Generate a new reset email token and save it
        //-----------------------------------------------------------------
        EmailToken resetEmail = TokenGenerator.buildEmail(user, EmailType.RESET_PASSWORD);

        //-----------------------------------------------------------------
        // Try to send email async
        //-----------------------------------------------------------------
        emailTokenAsyncSender.tryToSendEmail(resetEmail);

        emailTokenRepository.save(resetEmail);
    }

    @Override
    public void validateResetPasswordToken(String email, String token) throws UserException {
        User user = userRepository
                .findOneByEmailIgnoreCaseAndUserType(email, UserType.SIGN_UP)
                .orElseThrow(() -> new UserException("No matching of this email"));

        //-----------------------------------------------------------------
        // Try to find a matching email token
        //-----------------------------------------------------------------
        EmailToken emailToken = emailTokenRepository
                .findOneByEmailTypeAndUserIdAndToken(EmailType.RESET_PASSWORD, user.getId(), token)
                .orElseThrow(() -> new UserException("Token is invalid."));

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
        emailTokenRepository.save(emailToken);
    }

    @Override
    public void resetPassword(ResetPasswordDTO resetPasswordDTO, String email, String token) throws UserException {
        if (!resetPasswordDTO.getPassword().equals(resetPasswordDTO.getPasswordConfirmation())) {

            throw new UserException("New password should match new password confirmation");
        }

        User user = userRepository
                .findOneByEmailIgnoreCaseAndUserType(email, UserType.SIGN_UP)
                .orElseThrow(() -> new UserException("No matching of this email"));

        //-----------------------------------------------------------------
        // Try to find a matching email token which is validated
        //-----------------------------------------------------------------
        emailTokenRepository
                .findOneByEmailTypeAndUserIdAndTokenAndTokenValidatedTrue(EmailType.RESET_PASSWORD, user.getId(), token)
                .orElseThrow(() -> new UserException("Token is invalid."));

        //-----------------------------------------------------------------
        // Finally, reset password and save user
        //-----------------------------------------------------------------
        user.setPassword(BCrypt.hashpw(resetPasswordDTO.getPassword(), BCrypt.gensalt()));
        userRepository.save(user);
    }

    @Override
    public UserDTO updateCurrency(UserDTO userDTO, int userId) throws UserException {
        User foundUser = userRepository
                .findOneById(userId)
                .orElseThrow(() -> new UserException("User does not exist"));

        //-----------------------------------------------------------------
        // Try to find the currency
        //-----------------------------------------------------------------
        Currency byCurrencyCode = currencyRepository
                .findOneByCurrencyCode(userDTO.getCurrency().getCurrencyCode())
                .orElseThrow(() -> new UserException("The given currency does not exists"));
        foundUser.setCurrency(byCurrencyCode);

        User updatedUser = userRepository.save(foundUser);
        return dozerBeanMapper.map(updatedUser, UserDTO.class);
    }

    @Override
    public void sendFeedback(FeedbackDTO feedbackDTO, int userId) throws UserException {
        User user = userRepository
                .findOneById(userId)
                .orElseThrow(() -> new UserException("User does not exist"));

        //-----------------------------------------------------------------
        // Generate a feedback email message
        //-----------------------------------------------------------------
        EmailFeedback feedbackMessage = TokenGenerator.buildFeedbackEmail(user, EmailType.FEEDBACK_MESSAGE, feedbackDTO);

        //-----------------------------------------------------------------
        // Try to send email async
        //-----------------------------------------------------------------
        feedbackEmailAsyncSender.tryToSendEmail(feedbackMessage);

        emailRepository.save(feedbackMessage);
    }
}