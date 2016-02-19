package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.User;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.account.UserType;
import com.revaluate.domain.email.EmailType;
import com.revaluate.email.persistence.EmailToken;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceImplTest_requestConfirmEmail_IT extends AbstractIntegrationTests {

    @Test
    public void requestConfirmationEmail_happyFlow_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        userService.requestConfirmationEmail(createdUserDTO.getEmail());

        //-----------------------------------------------------------------
        // Assert that reset email token is added
        //-----------------------------------------------------------------
        User foundUser = userRepository.findOne(createdUserDTO.getId());
        List<EmailToken> emails = emailTokenRepository.findAllByEmailTypeAndUserId(EmailType.CREATED_ACCOUNT, foundUser.getId());
        assertThat(emails).hasSize(2);
    }

    @Test
    public void requestConfirmationEmail_oauthUserIsNotAllowed_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO(Boolean.TRUE, UserType.OAUTH_FACEBOOK);

        exception.expect(UserException.class);
        userService.requestConfirmationEmail(createdUserDTO.getEmail());
    }

    @Test
    public void requestConfirmationEmail_firstTokenIsOverridden_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        // First time
        userService.requestConfirmationEmail(createdUserDTO.getEmail());

        //-----------------------------------------------------------------
        // Assert that reset email token is added - FIRST ONE
        //-----------------------------------------------------------------
        User foundUser = userRepository.findOne(createdUserDTO.getId());
        List<EmailToken> emails = emailTokenRepository.findAllByEmailTypeAndUserId(EmailType.CREATED_ACCOUNT, foundUser.getId());
        assertThat(emails).hasSize(2);

        // Second time
        userService.requestConfirmationEmail(createdUserDTO.getEmail());

        //-----------------------------------------------------------------
        // Assert that reset email token is added - SECOND ONE
        //-----------------------------------------------------------------
        emails = emailTokenRepository.findAllByEmailTypeAndUserId(EmailType.CREATED_ACCOUNT, foundUser.getId());
        assertThat(emails).hasSize(3);
    }

    @Test(expected = UserException.class)
    public void requestConfirmationEmail_userDoesNotExists_throwsException() throws Exception {
        userService.requestConfirmationEmail(TEST_EMAIL);
    }
}
