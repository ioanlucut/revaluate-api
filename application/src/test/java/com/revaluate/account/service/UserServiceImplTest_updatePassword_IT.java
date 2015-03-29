package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.exception.UserException;
import com.revaluate.domain.account.*;
import org.junit.Test;

import javax.validation.ConstraintViolationException;

public class UserServiceImplTest_updatePassword_IT extends AbstractIntegrationTests {

    @Test
    public void updatePassword_happyFlowAndThenLogin_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        // Update a user
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withNewPassword(TEST_NEW_PASSWORD).withNewPasswordConfirmation(TEST_NEW_PASSWORD).withOldPassword(TEST_PASSWORD).build();
        userService.updatePassword(updatePasswordDTO, createdUserDTO.getId());

        // Try to login
        LoginDTO loginDTO = new LoginDTOBuilder().withEmail(TEST_EMAIL).withPassword(TEST_NEW_PASSWORD).build();
        userService.login(loginDTO);
    }

    @Test(expected = UserException.class)
    public void updatePassword_oldPasswordDoesNotMatch_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withNewPassword(TEST_NEW_PASSWORD).withNewPasswordConfirmation(TEST_NEW_PASSWORD).withOldPassword(TEST_PASSWORD_WRONG).build();
        userService.updatePassword(updatePasswordDTO, createdUserDTO.getId());
    }

    @Test(expected = UserException.class)
    public void updatePassword_newPasswordDoesNotMatchPasswordConfirmation_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withNewPassword(TEST_NEW_PASSWORD).withNewPasswordConfirmation(TEST_PASSWORD_WRONG).withOldPassword(TEST_PASSWORD).build();
        userService.updatePassword(updatePasswordDTO, createdUserDTO.getId());
    }

    @Test(expected = UserException.class)
    public void updatePassword_currentUserNotLoggedIn_exceptionThrown() throws Exception {
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withNewPassword(TEST_NEW_PASSWORD).withNewPasswordConfirmation(TEST_PASSWORD_WRONG).withOldPassword(TEST_PASSWORD).build();
        userService.updatePassword(updatePasswordDTO, 99999999);
    }

    @Test
    public void updatePassword_invalidDetails_handledCorrectly() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Expect constraint violation if creates with an invalid user DTO
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        UpdatePasswordDTO toCreate = new UpdatePasswordDTOBuilder().build();
        userService.updatePassword(toCreate, createdUserDTO.getId());
    }

}
