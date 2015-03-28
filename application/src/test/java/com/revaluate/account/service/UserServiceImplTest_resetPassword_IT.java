package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.EmailToken;
import com.revaluate.account.persistence.User;
import com.revaluate.domain.account.*;
import com.revaluate.domain.email.EmailType;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserServiceImplTest_resetPassword_IT extends AbstractIntegrationTests {

    @Test
    public void resetPassword_happyFlow_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Request reset password
        //-----------------------------------------------------------------
        userService.requestResetPassword(TEST_EMAIL);

        //-----------------------------------------------------------------
        // Reset password
        //-----------------------------------------------------------------
        User user = userRepository.findOne(createdUserDTO.getId());
        Optional<EmailToken> oneByEmailTypeAndUserId = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.RESET_PASSWORD, user.getId());
        assertThat(oneByEmailTypeAndUserId.isPresent(), is(true));

        //-----------------------------------------------------------------
        // Reset password with this token
        //-----------------------------------------------------------------
        String token = oneByEmailTypeAndUserId.get().getToken();
        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTOBuilder().withPassword(TEST_NEW_PASSWORD).withPasswordConfirmation(TEST_NEW_PASSWORD).build();
        userService.resetPassword(resetPasswordDTO, TEST_EMAIL, token);

        //-----------------------------------------------------------------
        // Try to login with newest password
        //-----------------------------------------------------------------
        LoginDTO loginDTO = new LoginDTOBuilder().withEmail(TEST_EMAIL).withPassword(TEST_NEW_PASSWORD).build();
        userService.login(loginDTO);

        //-----------------------------------------------------------------
        // Token was removed
        //-----------------------------------------------------------------
        oneByEmailTypeAndUserId = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.RESET_PASSWORD, user.getId());
        assertThat(oneByEmailTypeAndUserId.isPresent(), is(false));
    }

    @Test(expected = UserException.class)
    public void resetPassword_invalidTokenSentBack_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        // Reset password
        userService.requestResetPassword(TEST_EMAIL);
        User user = userRepository.findOne(createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Reset password but return the bad token back
        //-----------------------------------------------------------------
        Optional<EmailToken> oneByEmailTypeAndUserId = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.RESET_PASSWORD, user.getId());
        assertThat(oneByEmailTypeAndUserId.isPresent(), is(true));
        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTOBuilder().withPassword(TEST_NEW_PASSWORD).withPasswordConfirmation(TEST_NEW_PASSWORD).build();
        userService.resetPassword(resetPasswordDTO, TEST_EMAIL, oneByEmailTypeAndUserId.get().getToken() + "x");

        //-----------------------------------------------------------------
        // Token was already invalidated
        //-----------------------------------------------------------------
        oneByEmailTypeAndUserId = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.RESET_PASSWORD, user.getId());
        assertThat(oneByEmailTypeAndUserId.isPresent(), is(false));
    }
}
