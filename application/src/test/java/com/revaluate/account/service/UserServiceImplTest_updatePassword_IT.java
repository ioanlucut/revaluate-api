package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.domain.*;
import com.revaluate.account.exception.UserException;
import org.junit.Test;

public class UserServiceImplTest_updatePassword_IT extends AbstractIntegrationTests {

    @Test
    public void updatePassword_withValidDetailsAndThenLogin_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        // Update a user
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withNewPassword(NEW_PASSWORD).withNewPasswordConfirmation(NEW_PASSWORD).withOldPassword(OLD_PASSWORD).build();
        userService.updatePassword(updatePasswordDTO, createdUserDTO.getId());

        // Try to login
        LoginDTO loginDTO = new LoginDTOBuilder().withEmail(FAKE_EMAIL).withPassword(NEW_PASSWORD).build();
        userService.login(loginDTO);
    }

    @Test(expected = UserException.class)
    public void updatePassword_oldPasswordDoesNotMatch_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        // Update a user
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withNewPassword(NEW_PASSWORD).withNewPasswordConfirmation(NEW_PASSWORD).withOldPassword(PASSWORD_WRONG).build();
        userService.updatePassword(updatePasswordDTO, createdUserDTO.getId());
    }

    @Test(expected = UserException.class)
    public void updatePassword_newPasswordDoesNotMatchPasswordConfirmation_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        // Update a user
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withNewPassword(NEW_PASSWORD).withNewPasswordConfirmation(PASSWORD_WRONG).withOldPassword(OLD_PASSWORD).build();
        userService.updatePassword(updatePasswordDTO, createdUserDTO.getId());
    }

    @Test(expected = UserException.class)
    public void updatePassword_currentUserNotLoggedIn_exceptionThrown() throws Exception {

        // Update a user
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withNewPassword(NEW_PASSWORD).withNewPasswordConfirmation(PASSWORD_WRONG).withOldPassword(OLD_PASSWORD).build();
        userService.updatePassword(updatePasswordDTO, 99999999);
    }

}
