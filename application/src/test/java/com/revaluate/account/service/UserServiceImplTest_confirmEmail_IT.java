package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.Email;
import com.revaluate.domain.account.*;
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

        Optional<Email> oneEmail = emailRepository.findOneByEmailTypeAndUserIdAndTokenValidatedFalse(EmailType.CREATED_ACCOUNT, createdUserDTO.getId());
        assertThat(oneEmail.isPresent(), is(true));
        Email email = oneEmail.get();
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
        oneEmail = emailRepository.findOneByEmailTypeAndUserId(EmailType.CREATED_ACCOUNT, createdUserDTO.getId());
        email = oneEmail.get();
        assertThat(oneEmail.isPresent(), is(true));
        assertThat(email.isTokenValidated(), is(true));
        assertThat(email.isSent(), is(false));
        assertThat(email.getSentDate(), is(nullValue()));
    }

    @Test
    public void confirmEmail_invalidTokenSentBack_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        Optional<Email> oneEmail = emailRepository.findOneByEmailTypeAndUserIdAndTokenValidatedFalse(EmailType.CREATED_ACCOUNT, createdUserDTO.getId());
        assertThat(oneEmail.isPresent(), is(true));
        Email email = oneEmail.get();
        assertThat(email.isTokenValidated(), is(false));
        assertThat(email.isSent(), is(false));
        assertThat(email.getSentDate(), is(nullValue()));

        //-----------------------------------------------------------------
        // Confirm email with this token
        //-----------------------------------------------------------------
        exception.expect(UserException.class);
        String token = email.getToken();
        userService.validateConfirmationEmailToken(createdUserDTO.getEmail(), INVALID_TOKEN);

        //-----------------------------------------------------------------
        // Token was validated
        //-----------------------------------------------------------------
        oneEmail = emailRepository.findOneByEmailTypeAndUserId(EmailType.CREATED_ACCOUNT, createdUserDTO.getId());
        email = oneEmail.get();
        assertThat(oneEmail.isPresent(), is(true));
        assertThat(email.isTokenValidated(), is(false));
        assertThat(email.isSent(), is(false));
        assertThat(email.getSentDate(), is(nullValue()));
    }
}
