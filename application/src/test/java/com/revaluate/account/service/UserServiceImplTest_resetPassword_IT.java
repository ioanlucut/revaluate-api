package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.domain.*;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.EmailToken;
import com.revaluate.account.persistence.EmailType;
import com.revaluate.account.persistence.User;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserServiceImplTest_resetPassword_IT extends AbstractIntegrationTests {

    @Test
    public void resetPassword_detailsValid_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Request reset password
        //-----------------------------------------------------------------
        userService.requestResetPassword(FAKE_EMAIL);

        //-----------------------------------------------------------------
        // Reset password
        //-----------------------------------------------------------------
        User user = userRepository.findOne(createdUserDTO.getId());
        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTOBuilder().withPassword("2345678").withPasswordConfirmation("2345678").build();
        List<EmailToken> emailTokens = getTokenOfType(user, EmailType.RESET_PASSWORD);
        assertThat(emailTokens.size(), is(1));
        String realToken = emailTokens.get(0).getToken();
        userService.resetPassword(resetPasswordDTO, FAKE_EMAIL, realToken);

        //-----------------------------------------------------------------
        // Try to login with newest password
        //-----------------------------------------------------------------
        LoginDTO loginDTO = new LoginDTOBuilder().withEmail(FAKE_EMAIL).withPassword("2345678").build();
        userService.login(loginDTO);

        //-----------------------------------------------------------------
        // Token was removed
        //-----------------------------------------------------------------
        User userWithUpdatedPassword = userRepository.findOne(createdUserDTO.getId());
        emailTokens = getTokenOfType(userWithUpdatedPassword, EmailType.RESET_PASSWORD);
        assertThat(emailTokens.size(), is(0));
    }

    @Test(expected = UserException.class)
    public void resetPassword_invalidTokenSentBack_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        // Reset password
        userService.requestResetPassword(FAKE_EMAIL);
        User user = userRepository.findOne(createdUserDTO.getId());

        // reset password
        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTOBuilder().withPassword("2345678").withPasswordConfirmation("2345678").build();
        userService.resetPassword(resetPasswordDTO, FAKE_EMAIL, user.getEmailTokens().get(0).getToken() + "x");
    }
}
