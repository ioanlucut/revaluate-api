package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.EmailToken;
import com.revaluate.account.persistence.User;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.email.EmailType;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

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
        List<EmailToken> emailTokens = emailTokenRepository.findAllByEmailTypeAndUserId(EmailType.RESET_PASSWORD, foundUser.getId());
        assertThat(emailTokens.size(), is(1));
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
        assertThat(oneByEmailTypeAndUserId.isPresent(), is(true));
        EmailToken firstEmailResetToken = oneByEmailTypeAndUserId.get();

        // Second time
        userService.requestResetPassword(createdUserDTO.getEmail());

        //-----------------------------------------------------------------
        // Assert that reset email token is added - SECOND ONE
        //-----------------------------------------------------------------
        foundUser = userRepository.findOne(createdUserDTO.getId());
        oneByEmailTypeAndUserId = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.RESET_PASSWORD, foundUser.getId());
        assertThat(oneByEmailTypeAndUserId.isPresent(), is(true));
        EmailToken aDifferentResetToken = oneByEmailTypeAndUserId.get();
        assertThat(aDifferentResetToken.getToken(), Matchers.not(Matchers.equalTo(firstEmailResetToken.getToken())));
    }

    @Test(expected = UserException.class)
    public void requestResetPassword_userDoesNotExists_throwsException() throws Exception {
        userService.requestResetPassword(TEST_EMAIL);
    }
}
