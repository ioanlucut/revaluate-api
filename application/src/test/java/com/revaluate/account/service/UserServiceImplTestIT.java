package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.domain.*;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.EmailToken;
import com.revaluate.account.persistence.User;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class UserServiceImplTestIT extends AbstractIntegrationTests {

    @Test
    public void isUniqueEmail_validEmail_ok() throws Exception {
        assertThat(userService.isUnique("abc@def.ghi"), is(true));
    }

    @Test
    public void create_validDetails_ok() throws Exception {
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        assertThat(createdUserDTO, is(notNullValue()));
        assertThat(userDTO.getEmail(), equalTo(createdUserDTO.getEmail()));
        assertThat(userDTO.getFirstName(), equalTo(createdUserDTO.getFirstName()));
        assertThat(userDTO.getLastName(), equalTo(createdUserDTO.getLastName()));
        assertThat(userDTO.getPassword(), not(equalTo(createdUserDTO.getPassword())));
        assertThat(createdUserDTO.getId(), not(equalTo(0)));
        assertThat(createdUserDTO.getPassword(), is(nullValue()));
    }

    @Test
    public void login_validDetails_ok() throws Exception {
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        LoginDTO loginDTO = new LoginDTOBuilder().withEmail("xx@xx.xx").withPassword("1234567").build();

        UserDTO loggedUserDTO = userService.login(loginDTO);
        assertThat(loggedUserDTO, is(notNullValue()));
        assertThat(userDTO.getEmail(), equalTo(loggedUserDTO.getEmail()));
        assertThat(userDTO.getFirstName(), equalTo(loggedUserDTO.getFirstName()));
        assertThat(userDTO.getLastName(), equalTo(loggedUserDTO.getLastName()));
        assertThat(userDTO.getPassword(), not(equalTo(loggedUserDTO.getPassword())));
        assertThat(loggedUserDTO.getId(), not(equalTo(0)));
        assertThat(loggedUserDTO.getPassword(), is(nullValue()));
    }

    @Test
    public void getDetails_validDetails_ok() throws Exception {
        // Create a user
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        UserDTO userDetailsDTO = userService.getUserDetails(userDTO.getId());

        assertThat(userDetailsDTO, is(notNullValue()));
        // email excluded
        assertThat(userDetailsDTO.getEmail(), equalTo(userDTO.getEmail()));
        assertThat(userDetailsDTO.getFirstName(), equalTo(userDTO.getFirstName()));
        assertThat(userDetailsDTO.getLastName(), equalTo(userDTO.getLastName()));
        assertThat(userDetailsDTO.getId(), not(equalTo(0)));
        assertThat(userDetailsDTO.getId(), equalTo(userDTO.getId()));
        assertThat(userDetailsDTO.getPassword(), not(equalTo(userDTO.getPassword())));
    }

    @Test
    public void remove_validDetails_ok() throws Exception {
        // Create a user
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        // Remove
        userService.remove(userDTO.getId());

        // Assertions
        boolean exists = userRepository.exists(userDTO.getId());
        assertThat(exists, not(true));
    }

    @Test
    public void update_validDetails_ok() throws Exception {
        // Create a user
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        // Update a user
        UserDTO userDTOToUpdate = new UserDTOBuilder().withEmail("xx@xx.xx2").withFirstName("fn2").withLastName("ln2").withPassword("12345672").build();
        UserDTO updatedUserDTO = userService.update(userDTOToUpdate, createdUserDTO.getId());

        assertThat(updatedUserDTO, is(notNullValue()));
        // email excluded
        assertThat(updatedUserDTO.getEmail(), not(equalTo(userDTOToUpdate.getEmail())));
        assertThat(updatedUserDTO.getFirstName(), equalTo(userDTOToUpdate.getFirstName()));
        assertThat(updatedUserDTO.getLastName(), equalTo(userDTOToUpdate.getLastName()));
        assertThat(updatedUserDTO.getId(), not(equalTo(0)));
        assertThat(updatedUserDTO.getId(), equalTo(userDTO.getId()));
        assertThat(updatedUserDTO.getPassword(), not(equalTo(userDTOToUpdate.getPassword())));
    }

    @Test
    public void updateAndLogin_validDetails_ok() throws Exception {
        // Create a user
        String oldPassword = "1234567";
        String newPassword = "9999999";

        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword(oldPassword).build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        // Update a user
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withNewPassword(newPassword).withNewPasswordConfirmation(newPassword).withOldPassword(oldPassword).build();
        userService.updatePassword(updatePasswordDTO, createdUserDTO.getId());

        // Try to login
        LoginDTO loginDTO = new LoginDTOBuilder().withEmail("xx@xx.xx").withPassword(newPassword).build();
        userService.login(loginDTO);
    }

    @Test(expected = UserException.class)
    public void updatePassword_oldPasswordDoesNotMatch_exceptionThrown() throws Exception {
        // Create a user
        String oldPassword = "1234567";
        String newPassword = "9999999";

        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword(oldPassword).build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        // Update a user
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withNewPassword(newPassword).withNewPasswordConfirmation(newPassword).withOldPassword(oldPassword + "2").build();
        userService.updatePassword(updatePasswordDTO, createdUserDTO.getId());
    }

    @Test(expected = UserException.class)
    public void updatePassword_newPasswordDoesNotMatchPasswordConfirmation_exceptionThrown() throws Exception {
        // Create a user
        String oldPassword = "1234567";
        String newPassword = "9999999";

        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword(oldPassword).build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        // Update a user
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withNewPassword(newPassword).withNewPasswordConfirmation(newPassword + "2").withOldPassword(oldPassword).build();
        userService.updatePassword(updatePasswordDTO, createdUserDTO.getId());
    }

    @Test(expected = UserException.class)
    public void updatePassword_currentUserNotLoggedIn_exceptionThrown() throws Exception {
        String oldPassword = "1234567";
        String newPassword = "9999999";

        // Update a user
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withNewPassword(newPassword).withNewPasswordConfirmation(newPassword + "2").withOldPassword(oldPassword).build();
        userService.updatePassword(updatePasswordDTO, 99999999);
    }

    @Test
    public void sendVerificationEmailToken_validDetails_ok() throws Exception {
        // Create a user
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        userService.requestResetPassword(userDTO.getEmail());

        UserDTO userDTOWithResetPasswordRequest = userService.getUserDetails(userDTO.getId());

        assertThat(userDTOWithResetPasswordRequest, is(notNullValue()));
        // email excluded
        assertThat(userDTOWithResetPasswordRequest.getEmail(), equalTo(userDTO.getEmail()));
        assertThat(userDTOWithResetPasswordRequest.getFirstName(), equalTo(userDTO.getFirstName()));
        assertThat(userDTOWithResetPasswordRequest.getLastName(), equalTo(userDTO.getLastName()));
        assertThat(userDTOWithResetPasswordRequest.getId(), not(equalTo(0)));
        assertThat(userDTOWithResetPasswordRequest.getId(), equalTo(userDTO.getId()));
        assertThat(userDTOWithResetPasswordRequest.getPassword(), not(equalTo(userDTO.getPassword())));
    }

    @Test
    public void requestResetPassword_firstTokenIsOverridden_ok() throws Exception {
        // Create a user
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        // First time
        userService.requestResetPassword(userDTO.getEmail());
        EmailToken firstEmailToken = userRepository.findOne(userDTO.getId()).getEmailTokens().get(0);

        // Second time
        userService.requestResetPassword(userDTO.getEmail());
        EmailToken secondEmailToken = userRepository.findOne(userDTO.getId()).getEmailTokens().get(0);

        assertThat(secondEmailToken.getToken(), not(equalTo(firstEmailToken.getToken())));
    }

    @Test
    public void requestResetPassword_detailsValid_ok() throws Exception {
        // Create a user
        String oldPassword = "1234567";
        String email = "xx@xx.xx";

        userDTO = new UserDTOBuilder().withEmail(email).withFirstName("fn").withLastName("ln").withPassword(oldPassword).build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        // Reset password
        userService.requestResetPassword(email);
    }

    @Test(expected = UserException.class)
    public void requestResetPassword_userDoesNotExists_throwsException() throws Exception {
        userService.requestResetPassword("abcdefg@xx.com");
    }

    @Test
    public void validateResetPasswordToken_validDetails_ok() throws Exception {
        // Create a user
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        // Reset password
        userService.requestResetPassword("xx@xx.xx");
        User user = userRepository.findOne(userDTO.getId());

        // validate token
        userService.validateResetPasswordToken("xx@xx.xx", user.getEmailTokens().get(0).getToken());
    }

    @Test(expected = UserException.class)
    public void validateResetPasswordToken_EmailDoesNotExist_exceptionThrown() throws Exception {
        userService.validateResetPasswordToken("xx@xx.xx", null);
    }

    @Test(expected = UserException.class)
    public void validateResetPasswordToken_invalidToken_exceptionThrown() throws Exception {
        // Create a user
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        // Reset password
        userService.requestResetPassword("xx@xx.xx");
        User user = userRepository.findOne(userDTO.getId());

        // validate token
        userService.validateResetPasswordToken("xx@xx.xx", user.getEmailTokens().get(0).getToken() + "x");
    }

    @Test(expected = UserException.class)
    public void validateResetPasswordToken_invalidFirstTimeAndSecondTimeValidOneIsInvalidatedToo_exceptionThrown() throws Exception {
        // Create a user
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        // Reset password
        userService.requestResetPassword("xx@xx.xx");
        User user = userRepository.findOne(userDTO.getId());

        // validate token
        try {
            userService.validateResetPasswordToken("xx@xx.xx", user.getEmailTokens().get(0).getToken() + "x");
        } catch (UserException ex) {
            // do nothing
        }

        userService.validateResetPasswordToken("xx@xx.xx", user.getEmailTokens().get(0).getToken());
    }

    @Test
    public void testResetPassword_detailsValid_ok() throws Exception {
        // Create a user
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        // Reset password
        userService.requestResetPassword("xx@xx.xx");
        User user = userRepository.findOne(userDTO.getId());

        // reset password
        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTOBuilder().withPassword("2345678").withPasswordConfirmation("2345678").build();
        userService.resetPassword(resetPasswordDTO, "xx@xx.xx", user.getEmailTokens().get(0).getToken());

        // Try to login
        LoginDTO loginDTO = new LoginDTOBuilder().withEmail("xx@xx.xx").withPassword("2345678").build();
        userService.login(loginDTO);

        User userWithUpdatedPassword = userRepository.findOne(userDTO.getId());
        assertThat(userWithUpdatedPassword.getEmailTokens().size(), is(0));
    }

    @Test(expected = UserException.class)
    public void resetPassword_invalidTokenSentBack_exceptionThrown() throws Exception {
        // Create a user
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        // Reset password
        userService.requestResetPassword("xx@xx.xx");
        User user = userRepository.findOne(userDTO.getId());

        // reset password
        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTOBuilder().withPassword("2345678").withPasswordConfirmation("2345678").build();
        userService.resetPassword(resetPasswordDTO, "xx@xx.xx", user.getEmailTokens().get(0).getToken() + "x");
    }
}
