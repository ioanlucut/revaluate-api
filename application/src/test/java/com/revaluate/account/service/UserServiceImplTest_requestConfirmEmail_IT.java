package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.Email;
import com.revaluate.account.persistence.User;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.email.EmailType;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

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
        List<Email> emails = emailRepository.findAllByEmailTypeAndUserId(EmailType.CREATED_ACCOUNT, foundUser.getId());
        assertThat(emails.size(), is(2));
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
        List<Email> emails = emailRepository.findAllByEmailTypeAndUserId(EmailType.CREATED_ACCOUNT, foundUser.getId());
        assertThat(emails.size(), is(2));

        // Second time
        userService.requestConfirmationEmail(createdUserDTO.getEmail());

        //-----------------------------------------------------------------
        // Assert that reset email token is added - SECOND ONE
        //-----------------------------------------------------------------
        emails = emailRepository.findAllByEmailTypeAndUserId(EmailType.CREATED_ACCOUNT, foundUser.getId());
        assertThat(emails.size(), is(3));
    }

    @Test(expected = UserException.class)
    public void requestConfirmationEmail_userDoesNotExists_throwsException() throws Exception {
        userService.requestConfirmationEmail(TEST_EMAIL);
    }
}
