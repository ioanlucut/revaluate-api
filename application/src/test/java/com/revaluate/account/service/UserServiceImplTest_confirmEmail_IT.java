package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.exception.UserException;
import com.revaluate.email.persistence.EmailToken;
import com.revaluate.account.persistence.User;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.email.EmailType;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.Is.is;

public class UserServiceImplTest_confirmEmail_IT extends AbstractIntegrationTests {

    @Test
    public void confirmEmail_happyFlow_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        Optional<EmailToken> oneEmail = emailTokenRepository.findOneByEmailTypeAndUserIdAndTokenValidatedFalse(EmailType.CREATED_ACCOUNT, createdUserDTO.getId());
        assertThat(oneEmail.isPresent(), is(true));
        EmailToken email = oneEmail.get();
        assertThat(email.isTokenValidated(), is(false));
        assertThat(email.isSent(), is(false));
        assertThat(email.getSentDate(), is(nullValue()));

        //-----------------------------------------------------------------
        // Confirm email with this token
        //-----------------------------------------------------------------
        String token = email.getToken();
        userService.validateConfirmationEmailToken(createdUserDTO.getEmail(), token);

        //-----------------------------------------------------------------
        // Token was validated
        //-----------------------------------------------------------------
        oneEmail = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.CREATED_ACCOUNT, createdUserDTO.getId());
        email = oneEmail.get();
        assertThat(oneEmail.isPresent(), is(true));
        assertThat(email.isTokenValidated(), is(true));
        assertThat(email.isSent(), is(false));
        assertThat(email.getSentDate(), is(nullValue()));

        //-----------------------------------------------------------------
        // User has the email confirmed flag
        //-----------------------------------------------------------------
        User one = userRepository.findOne(createdUserDTO.getId());
        assertThat(one.isEmailConfirmed(), is(true));
    }

    @Test
    public void confirmEmail_manyTimes_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        Optional<EmailToken> oneEmail = emailTokenRepository.findOneByEmailTypeAndUserIdAndTokenValidatedFalse(EmailType.CREATED_ACCOUNT, createdUserDTO.getId());
        assertThat(oneEmail.isPresent(), is(true));
        EmailToken email = oneEmail.get();
        assertThat(email.isTokenValidated(), is(false));
        assertThat(email.isSent(), is(false));
        assertThat(email.getSentDate(), is(nullValue()));

        //-----------------------------------------------------------------
        // Confirm email with this token three times.
        //-----------------------------------------------------------------
        String token = email.getToken();
        userService.validateConfirmationEmailToken(createdUserDTO.getEmail(), token);
        userService.validateConfirmationEmailToken(createdUserDTO.getEmail(), token);
        userService.validateConfirmationEmailToken(createdUserDTO.getEmail(), token);

        //-----------------------------------------------------------------
        // Token was validated
        //-----------------------------------------------------------------
        oneEmail = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.CREATED_ACCOUNT, createdUserDTO.getId());
        email = oneEmail.get();
        assertThat(oneEmail.isPresent(), is(true));
        assertThat(email.isTokenValidated(), is(true));
        assertThat(email.isSent(), is(false));
        assertThat(email.getSentDate(), is(nullValue()));

        //-----------------------------------------------------------------
        // User has the email confirmed flag
        //-----------------------------------------------------------------
        User one = userRepository.findOne(createdUserDTO.getId());
        assertThat(one.isEmailConfirmed(), is(true));
    }

    @Test
    public void confirmEmail_invalidTokenSentBack_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        Optional<EmailToken> oneEmail = emailTokenRepository.findOneByEmailTypeAndUserIdAndTokenValidatedFalse(EmailType.CREATED_ACCOUNT, createdUserDTO.getId());
        assertThat(oneEmail.isPresent(), is(true));
        EmailToken email = oneEmail.get();
        assertThat(email.isTokenValidated(), is(false));
        assertThat(email.isSent(), is(false));
        assertThat(email.getSentDate(), is(nullValue()));

        //-----------------------------------------------------------------
        // Confirm email with this token
        //-----------------------------------------------------------------
        exception.expect(UserException.class);
        userService.validateConfirmationEmailToken(createdUserDTO.getEmail(), INVALID_TOKEN);

        //-----------------------------------------------------------------
        // Token was not validated
        //-----------------------------------------------------------------
        oneEmail = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.CREATED_ACCOUNT, createdUserDTO.getId());
        email = oneEmail.get();
        assertThat(oneEmail.isPresent(), is(true));
        assertThat(email.isTokenValidated(), is(false));
        assertThat(email.isSent(), is(false));
        assertThat(email.getSentDate(), is(nullValue()));

        //-----------------------------------------------------------------
        // User has the email confirmed flag
        //-----------------------------------------------------------------
        User one = userRepository.findOne(createdUserDTO.getId());
        assertThat(one.isEmailConfirmed(), is(false));
    }

    @Test
    public void confirmEmail_firstTimeInvalidSecondTimeOk_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        Optional<EmailToken> oneEmail = emailTokenRepository.findOneByEmailTypeAndUserIdAndTokenValidatedFalse(EmailType.CREATED_ACCOUNT, createdUserDTO.getId());
        assertThat(oneEmail.isPresent(), is(true));
        EmailToken email = oneEmail.get();
        assertThat(email.isTokenValidated(), is(false));
        assertThat(email.isSent(), is(false));
        assertThat(email.getSentDate(), is(nullValue()));

        //-----------------------------------------------------------------
        // Confirm email with this token
        //-----------------------------------------------------------------
        exception.expect(UserException.class);
        userService.validateConfirmationEmailToken(createdUserDTO.getEmail(), INVALID_TOKEN);

        //-----------------------------------------------------------------
        // Token was validated
        //-----------------------------------------------------------------
        oneEmail = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.CREATED_ACCOUNT, createdUserDTO.getId());
        email = oneEmail.get();
        assertThat(oneEmail.isPresent(), is(true));
        assertThat(email.isTokenValidated(), is(false));
        assertThat(email.isSent(), is(false));
        assertThat(email.getSentDate(), is(nullValue()));

        //-----------------------------------------------------------------
        // After that, email is confirmed - is ok.
        //-----------------------------------------------------------------
        String token = email.getToken();
        userService.validateConfirmationEmailToken(createdUserDTO.getEmail(), token);

        //-----------------------------------------------------------------
        // Token was validated
        //-----------------------------------------------------------------
        oneEmail = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.CREATED_ACCOUNT, createdUserDTO.getId());
        email = oneEmail.get();
        assertThat(oneEmail.isPresent(), is(true));
        assertThat(email.isTokenValidated(), is(true));
        assertThat(email.isSent(), is(false));
        assertThat(email.getSentDate(), is(nullValue()));

        //-----------------------------------------------------------------
        // User has the email confirmed flag
        //-----------------------------------------------------------------
        User one = userRepository.findOne(createdUserDTO.getId());
        assertThat(one.isEmailConfirmed(), is(true));
    }
}
