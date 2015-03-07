package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.domain.UserDTO;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.EmailToken;
import com.revaluate.account.persistence.EmailType;
import com.revaluate.account.persistence.User;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class UserServiceImplTest_requestResetPassword_IT extends AbstractIntegrationTests {

    @Test
    public void requestResetPassword_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();
        userService.requestResetPassword(createdUserDTO.getEmail());

        //-----------------------------------------------------------------
        // Assertions
        //-----------------------------------------------------------------
        UserDTO userDTOWithResetPasswordRequest = userService.getUserDetails(createdUserDTO.getId());
        assertThat(userDTOWithResetPasswordRequest, is(notNullValue()));
        // email excluded
        assertThat(userDTOWithResetPasswordRequest.getEmail(), equalTo(createdUserDTO.getEmail()));
        assertThat(userDTOWithResetPasswordRequest.getFirstName(), equalTo(createdUserDTO.getFirstName()));
        assertThat(userDTOWithResetPasswordRequest.getLastName(), equalTo(createdUserDTO.getLastName()));
        assertThat(userDTOWithResetPasswordRequest.getId(), not(equalTo(0)));
        assertThat(userDTOWithResetPasswordRequest.getId(), equalTo(createdUserDTO.getId()));
        assertThat(userDTOWithResetPasswordRequest.getPassword(), is(nullValue()));
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

    @Test(expected = UserException.class)
    public void requestResetPassword_userDoesNotExists_throwsException() throws Exception {
        userService.requestResetPassword("abcdefg@xx.com");
    }

}
