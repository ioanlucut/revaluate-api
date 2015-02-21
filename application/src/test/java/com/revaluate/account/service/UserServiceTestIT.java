/*
package com.revaluate.account.service;

import com.revaluate.account.domain.*;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserEmailToken;
import com.revaluate.account.persistence.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-config.application/spring-context-application.xml")
public class UserServiceTestIT {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private UserDTO userDTO;

    @After
    public void tearDown() throws Exception {
        if (userDTO != null) {
            if (userRepository.exists(userDTO.getId())) {
                userService.remove(userDTO.getId());
            }
        }
    }

    @Test
    public void testIsUnique() throws Exception {
        assertThat(userService.isUnique("abc@def.ghi"), is(true));
    }

    @Test
    public void testCreate() throws Exception {
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
    public void testLogin() throws Exception {
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
    public void testUpdate() throws Exception {
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
    public void testGetUserDetails() throws Exception {
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
    public void testRemove() throws Exception {
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
    public void testUpdatePasswordHappyFlow() throws Exception {
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
    public void testUpdatePasswordOldPasswordDoesNotMatch() throws Exception {
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
    public void testUpdatePasswordNewPasswordDoesNotMatchPasswordConfirmation() throws Exception {
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
    public void testUpdatePasswordCurrentUserNotLoggedIn() throws Exception {
        String oldPassword = "1234567";
        String newPassword = "9999999";

        // Update a user
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withNewPassword(newPassword).withNewPasswordConfirmation(newPassword + "2").withOldPassword(oldPassword).build();
        userService.updatePassword(updatePasswordDTO, 99999999);
    }

    @Test
    public void sendVerificationEmailToken() throws Exception {
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
    public void requestResetPasswordFirstTokenOverriden() throws Exception {
        // Create a user
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        // First time
        userService.requestResetPassword(userDTO.getEmail());
        UserEmailToken firstEmailToken = userRepository.findOne(userDTO.getId()).getResetEmailToken();

        // Second time
        userService.requestResetPassword(userDTO.getEmail());
        UserEmailToken secondEmailToken = userRepository.findOne(userDTO.getId()).getResetEmailToken();

        assertThat(secondEmailToken.getToken(), not(equalTo(firstEmailToken.getToken())));
    }

    @Test
    public void testRequestResetPasswordHappyFlow() throws Exception {
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
    public void testRequestResetPasswordInExistingUser() throws Exception {
        userService.requestResetPassword("abcdefg@xx.com");
    }

    @Test
    public void testValidateResetPasswordTokenHappyFlow() throws Exception {
        // Create a user
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        // Reset password
        userService.requestResetPassword("xx@xx.xx");
        User user = userRepository.findOne(userDTO.getId());

        // validate token
        userService.validateResetPasswordToken("xx@xx.xx", user.getResetEmailToken().getToken());
    }

    @Test(expected = UserException.class)
    public void testValidateResetPasswordTokenEmailDoesNotExist() throws Exception {
        userService.validateResetPasswordToken("xx@xx.xx", null);
    }

    @Test(expected = UserException.class)
    public void testValidateResetPasswordTokenInvalidTokenSentBack() throws Exception {
        // Create a user
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        // Reset password
        userService.requestResetPassword("xx@xx.xx");
        User user = userRepository.findOne(userDTO.getId());

        // validate token
        userService.validateResetPasswordToken("xx@xx.xx", user.getResetEmailToken().getToken() + "x");
    }

    @Test(expected = UserException.class)
    public void testValidateResetPasswordTokenInvalidFirstTimeAndSecondTimeValidOneIsInvalidatedToo() throws Exception {
        // Create a user
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        // Reset password
        userService.requestResetPassword("xx@xx.xx");
        User user = userRepository.findOne(userDTO.getId());

        // validate token
        try {
            userService.validateResetPasswordToken("xx@xx.xx", user.getResetEmailToken().getToken() + "x");
        } catch (UserException ex) {
            // do nothing
        }

        userService.validateResetPasswordToken("xx@xx.xx", user.getResetEmailToken().getToken());
    }

    @Test
    public void testResetPasswordHappyFlow() throws Exception {
        // Create a user
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        // Reset password
        userService.requestResetPassword("xx@xx.xx");
        User user = userRepository.findOne(userDTO.getId());

        // reset password
        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTOBuilder().withPassword("2345678").withPasswordConfirmation("2345678").build();
        userService.resetPassword(resetPasswordDTO, "xx@xx.xx", user.getResetEmailToken().getToken());

        // Try to login
        LoginDTO loginDTO = new LoginDTOBuilder().withEmail("xx@xx.xx").withPassword("2345678").build();
        userService.login(loginDTO);

        User userWithUpdatedPassword = userRepository.findOne(userDTO.getId());
        assertThat(userWithUpdatedPassword.getResetEmailToken(), is(nullValue()));
    }

    @Test(expected = UserException.class)
    public void testResetPasswordInvalidTokenSentBack() throws Exception {
        // Create a user
        userDTO = new UserDTOBuilder().withEmail("xx@xx.xx").withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdUserDTO = userService.create(userDTO);
        userDTO.setId(createdUserDTO.getId());

        // Reset password
        userService.requestResetPassword("xx@xx.xx");
        User user = userRepository.findOne(userDTO.getId());

        // reset password
        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTOBuilder().withPassword("2345678").withPasswordConfirmation("2345678").build();
        userService.resetPassword(resetPasswordDTO, "xx@xx.xx", user.getResetEmailToken().getToken() + "x");
    }
}*/
