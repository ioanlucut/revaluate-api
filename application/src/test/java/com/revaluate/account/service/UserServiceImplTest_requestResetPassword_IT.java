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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceImplTest_requestResetPassword_IT extends AbstractIntegrationTests {

    @Test
    public void requestResetPassword_happyFlow_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        // Reset password
        userService.requestResetPassword(TEST_EMAIL);

        //-----------------------------------------------------------------
        // Assert that reset email token is added
        //-----------------------------------------------------------------
        User foundUser = userRepository.findOne(createdUserDTO.getId());
        List<EmailToken> emails = emailTokenRepository.findAllByEmailTypeAndUserId(EmailType.RESET_PASSWORD, foundUser.getId());
        assertThat(emails).hasSize(1);
    }

    @Test
    public void requestResetPassword_oauthUserIsNotAllowed_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO(Boolean.TRUE, UserType.OAUTH_FACEBOOK);

        // Reset password
        exception.expect(UserException.class);
        userService.requestResetPassword(createdUserDTO.getEmail());
    }

    @Test
    public void requestResetPassword_firstTokenIsOverridden_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        // First time
        userService.requestResetPassword(createdUserDTO.getEmail());

        //-----------------------------------------------------------------
        // Assert that reset email token is added - FIRST ONE
        //-----------------------------------------------------------------
        User foundUser = userRepository.findOne(createdUserDTO.getId());
        Optional<EmailToken> oneByEmailTypeAndUserId = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.RESET_PASSWORD, foundUser.getId());
        assertThat(oneByEmailTypeAndUserId.isPresent()).isTrue();
        EmailToken firstEmailResetToken = oneByEmailTypeAndUserId.get();

        // Second time
        userService.requestResetPassword(createdUserDTO.getEmail());

        //-----------------------------------------------------------------
        // Assert that reset email token is added - SECOND ONE
        //-----------------------------------------------------------------
        foundUser = userRepository.findOne(createdUserDTO.getId());
        oneByEmailTypeAndUserId = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.RESET_PASSWORD, foundUser.getId());
        assertThat(oneByEmailTypeAndUserId.isPresent()).isTrue();
        EmailToken aDifferentResetToken = oneByEmailTypeAndUserId.get();
        assertThat(aDifferentResetToken.getToken()).isNotEqualTo(firstEmailResetToken.getToken());
    }

    @Test(expected = UserException.class)
    public void requestResetPassword_userDoesNotExists_throwsException() throws Exception {
        userService.requestResetPassword(TEST_EMAIL);
    }
}
