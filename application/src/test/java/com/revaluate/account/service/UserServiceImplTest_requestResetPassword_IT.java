package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.EmailToken;
import com.revaluate.domain.email.EmailType;
import com.revaluate.account.persistence.User;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

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
        List<EmailToken> emailTokens = getTokenOfType(foundUser, EmailType.RESET_PASSWORD);
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
        boolean onlyOneResetPassword = getTokenOfType(foundUser, EmailType.RESET_PASSWORD).size() == 1;
        assertThat(onlyOneResetPassword, is(true));
        EmailToken firstEmailResetToken = foundUser.getEmailTokens()
                .stream()
                .filter(e -> e.getEmailType() == EmailType.RESET_PASSWORD)
                .findFirst().get();

        // Second time
        userService.requestResetPassword(createdUserDTO.getEmail());

        //-----------------------------------------------------------------
        // Assert that reset email token is added - SECOND ONE
        //-----------------------------------------------------------------
        foundUser = userRepository.findOne(createdUserDTO.getId());
        onlyOneResetPassword = getTokenOfType(foundUser, EmailType.RESET_PASSWORD).size() == 1;
        assertThat(onlyOneResetPassword, is(true));
        boolean aDifferentResetToken = foundUser.getEmailTokens()
                .stream()
                .filter(e -> e.getEmailType() == EmailType.RESET_PASSWORD && !e.getToken().equals(firstEmailResetToken.getToken()))
                .collect(Collectors.toList()).size() == 1;
        assertThat(aDifferentResetToken, is(true));
    }

    @Test(expected = UserException.class)
    public void requestResetPassword_userDoesNotExists_throwsException() throws Exception {
        userService.requestResetPassword("abcdefg@xx.com");
    }
}
