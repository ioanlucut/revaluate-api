package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.User;
import com.revaluate.domain.account.*;
import com.revaluate.domain.email.EmailType;
import com.revaluate.email.persistence.EmailToken;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
        userService.requestResetPassword(createdUserDTO.getEmail());

        //-----------------------------------------------------------------
        // Reset password is generated
        //-----------------------------------------------------------------
        User user = userRepository.findOne(createdUserDTO.getId());
        Optional<EmailToken> oneByEmailTypeAndUserId = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.RESET_PASSWORD, user.getId());
        assertThat(oneByEmailTypeAndUserId.isPresent()).isTrue();

        //-----------------------------------------------------------------
        // Validate reset password token
        //-----------------------------------------------------------------
        String token = oneByEmailTypeAndUserId.get().getToken();
        userService.validateResetPasswordToken(createdUserDTO.getEmail(), token);

        //-----------------------------------------------------------------
        // Reset password with this token
        //-----------------------------------------------------------------
        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTOBuilder().withPassword(TEST_NEW_PASSWORD).withPasswordConfirmation(TEST_NEW_PASSWORD).build();
        userService.resetPassword(resetPasswordDTO, createdUserDTO.getEmail(), token);

        //-----------------------------------------------------------------
        // Try to login with newest password
        //-----------------------------------------------------------------
        LoginDTO loginDTO = new LoginDTOBuilder().withEmail(createdUserDTO.getEmail()).withPassword(TEST_NEW_PASSWORD).build();
        userService.login(loginDTO);
    }

    @Test
    public void resetPassword_secondRequestRemovesAllPrevious_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Request reset password
        //-----------------------------------------------------------------
        userService.requestResetPassword(createdUserDTO.getEmail());

        //-----------------------------------------------------------------
        // Reset password is generated
        //-----------------------------------------------------------------
        User user = userRepository.findOne(createdUserDTO.getId());
        Optional<EmailToken> oneByEmailTypeAndUserId = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.RESET_PASSWORD, user.getId());
        assertThat(oneByEmailTypeAndUserId.isPresent()).isTrue();

        //-----------------------------------------------------------------
        // Request reset password - second time
        //-----------------------------------------------------------------
        userService.requestResetPassword(createdUserDTO.getEmail());

        oneByEmailTypeAndUserId = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.RESET_PASSWORD, user.getId());
        assertThat(oneByEmailTypeAndUserId.isPresent()).isTrue();
    }

    @Test
    public void resetPassword_invalidTokenSentBack_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Request reset password
        //-----------------------------------------------------------------
        userService.requestResetPassword(createdUserDTO.getEmail());

        //-----------------------------------------------------------------
        // Reset password is generated
        //-----------------------------------------------------------------
        User user = userRepository.findOne(createdUserDTO.getId());
        Optional<EmailToken> oneByEmailTypeAndUserId = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.RESET_PASSWORD, user.getId());
        assertThat(oneByEmailTypeAndUserId.isPresent()).isTrue();

        //-----------------------------------------------------------------
        // Validate reset password token
        //-----------------------------------------------------------------
        String token = oneByEmailTypeAndUserId.get().getToken();
        userService.validateResetPasswordToken(createdUserDTO.getEmail(), token);

        //-----------------------------------------------------------------
        // Reset password with this token
        //-----------------------------------------------------------------
        exception.expect(UserException.class);
        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTOBuilder().withPassword(TEST_NEW_PASSWORD).withPasswordConfirmation(TEST_NEW_PASSWORD).build();
        userService.resetPassword(resetPasswordDTO, createdUserDTO.getEmail(), INVALID_TOKEN);

        //-----------------------------------------------------------------
        // Token is still there, not removed
        //-----------------------------------------------------------------
        oneByEmailTypeAndUserId = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.RESET_PASSWORD, user.getId());
        assertThat(oneByEmailTypeAndUserId.isPresent()).isTrue();
    }

    @Test
    public void resetPassword_tokenNotValidatedBefore_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Request reset password
        //-----------------------------------------------------------------
        userService.requestResetPassword(createdUserDTO.getEmail());

        //-----------------------------------------------------------------
        // Reset password is generated
        //-----------------------------------------------------------------
        User user = userRepository.findOne(createdUserDTO.getId());
        Optional<EmailToken> oneByEmailTypeAndUserId = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.RESET_PASSWORD, user.getId());
        assertThat(oneByEmailTypeAndUserId.isPresent()).isTrue();

        //-----------------------------------------------------------------
        // Reset password with this token
        //-----------------------------------------------------------------
        exception.expect(UserException.class);
        String token = oneByEmailTypeAndUserId.get().getToken();
        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTOBuilder().withPassword(TEST_NEW_PASSWORD).withPasswordConfirmation(TEST_NEW_PASSWORD).build();
        userService.resetPassword(resetPasswordDTO, createdUserDTO.getEmail(), token);

        //-----------------------------------------------------------------
        // Token is still there, not removed
        //-----------------------------------------------------------------
        oneByEmailTypeAndUserId = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.RESET_PASSWORD, user.getId());
        assertThat(oneByEmailTypeAndUserId.isPresent()).isTrue();
    }
}
