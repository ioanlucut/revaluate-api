package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.domain.*;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.EmailToken;
import com.revaluate.account.persistence.EmailType;
import com.revaluate.account.persistence.User;
import com.revaluate.currency.domain.CurrencyDTO;
import org.joda.money.CurrencyUnit;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        assertThat(createdUserDTO, is(notNullValue()));
        assertThat(userDTO.getEmail(), equalTo(createdUserDTO.getEmail()));
        assertThat(userDTO.getFirstName(), equalTo(createdUserDTO.getFirstName()));
        assertThat(userDTO.getLastName(), equalTo(createdUserDTO.getLastName()));
        assertThat(userDTO.getPassword(), not(equalTo(createdUserDTO.getPassword())));
        assertThat(createdUserDTO.getId(), not(equalTo(0)));
        assertThat(createdUserDTO.getPassword(), is(nullValue()));

        //-----------------------------------------------------------------
        // Assert that email token is added
        //-----------------------------------------------------------------
        Optional<User> oneByEmail = userRepository.findOneByEmail(userDTO.getEmail());
        assertThat(oneByEmail.isPresent(), is(true));
        boolean anyMatch = oneByEmail.get().getEmailTokens().stream().anyMatch(e -> e.getEmailType() == EmailType.CREATED_ACCOUNT);
        assertThat(anyMatch, is(true));
    }

    @Test
    public void login_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        LoginDTO loginDTO = new LoginDTOBuilder().withEmail(FAKE_EMAIL).withPassword(OLD_PASSWORD).build();

        UserDTO loggedUserDTO = userService.login(loginDTO);
        assertThat(loggedUserDTO, is(notNullValue()));
        assertThat(createdUserDTO.getEmail(), equalTo(loggedUserDTO.getEmail()));
        assertThat(createdUserDTO.getFirstName(), equalTo(loggedUserDTO.getFirstName()));
        assertThat(createdUserDTO.getLastName(), equalTo(loggedUserDTO.getLastName()));
        assertThat(createdUserDTO.getPassword(), is(nullValue()));
        assertThat(loggedUserDTO.getId(), not(equalTo(0)));
        assertThat(loggedUserDTO.getPassword(), is(nullValue()));
    }

    @Test
    public void getDetails_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        UserDTO userDetailsDTO = userService.getUserDetails(createdUserDTO.getId());

        assertThat(userDetailsDTO, is(notNullValue()));
        // email excluded
        assertThat(userDetailsDTO.getEmail(), equalTo(createdUserDTO.getEmail()));
        assertThat(userDetailsDTO.getFirstName(), equalTo(createdUserDTO.getFirstName()));
        assertThat(userDetailsDTO.getLastName(), equalTo(createdUserDTO.getLastName()));
        assertThat(userDetailsDTO.getId(), not(equalTo(0)));
        assertThat(userDetailsDTO.getId(), equalTo(createdUserDTO.getId()));
        assertThat(userDetailsDTO.getPassword(), is(nullValue()));
    }

    @Test
    public void remove_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        // Remove
        userService.remove(createdUserDTO.getId());

        // Assertions
        boolean exists = userRepository.exists(createdUserDTO.getId());
        assertThat(exists, not(true));
    }

    @Test
    public void update_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Compute the currency to update
        //-----------------------------------------------------------------
        CurrencyDTO currency = new CurrencyDTO();
        currency.setCurrencyCode(CurrencyUnit.GBP.getCurrencyCode());
        CurrencyDTO currencyDTOToUpdate = currencyService.create(currency);

        // Update a user
        UserDTO userDTOToUpdate = new UserDTOBuilder().withEmail("xx@xx.xx2").withFirstName("fn2").withLastName("ln2").withPassword("12345672").withCurrency(currencyDTOToUpdate).build();
        UserDTO updatedUserDTO = userService.update(userDTOToUpdate, createdUserDTO.getId());

        assertThat(updatedUserDTO, is(notNullValue()));
        // email excluded
        assertThat(updatedUserDTO.getEmail(), not(equalTo(userDTOToUpdate.getEmail())));
        assertThat(updatedUserDTO.getFirstName(), equalTo(userDTOToUpdate.getFirstName()));
        assertThat(updatedUserDTO.getLastName(), equalTo(userDTOToUpdate.getLastName()));
        assertThat(updatedUserDTO.getId(), not(equalTo(0)));
        assertThat(updatedUserDTO.getId(), equalTo(createdUserDTO.getId()));
        assertThat(updatedUserDTO.getCurrency().getCurrencyCode(), not(equalTo(createdUserDTO.getCurrency().getCurrencyCode())));
        assertThat(updatedUserDTO.getCurrency().getCurrencyCode(), equalTo(currency.getCurrencyCode()));
        assertThat(updatedUserDTO.getPassword(), not(equalTo(userDTOToUpdate.getPassword())));
    }

    @Test
    public void updateAndLogin_validDetails_ok() throws Exception {
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
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withNewPassword(NEW_PASSWORD).withNewPasswordConfirmation(NEW_PASSWORD).withOldPassword(OLD_PASSWORD + "2").build();
        userService.updatePassword(updatePasswordDTO, createdUserDTO.getId());
    }

    @Test(expected = UserException.class)
    public void updatePassword_newPasswordDoesNotMatchPasswordConfirmation_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        // Update a user
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withNewPassword(NEW_PASSWORD).withNewPasswordConfirmation(NEW_PASSWORD + "2").withOldPassword(OLD_PASSWORD).build();
        userService.updatePassword(updatePasswordDTO, createdUserDTO.getId());
    }

    @Test(expected = UserException.class)
    public void updatePassword_currentUserNotLoggedIn_exceptionThrown() throws Exception {

        // Update a user
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withNewPassword(NEW_PASSWORD).withNewPasswordConfirmation(NEW_PASSWORD + "2").withOldPassword(OLD_PASSWORD).build();
        userService.updatePassword(updatePasswordDTO, 99999999);
    }

    @Test
    public void sendVerificationEmailToken_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        userService.requestResetPassword(createdUserDTO.getEmail());

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
        // Create first user
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
    public void requestResetPassword_detailsValid_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        // Reset password
        userService.requestResetPassword(FAKE_EMAIL);

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

    @Test
    public void validateResetPasswordToken_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        // Reset password
        userService.requestResetPassword(FAKE_EMAIL);
        User user = userRepository.findOne(createdUserDTO.getId());

        // validate token
        userService.validateResetPasswordToken(FAKE_EMAIL, user.getEmailTokens().get(0).getToken());
    }

    @Test(expected = UserException.class)
    public void validateResetPasswordToken_EmailDoesNotExist_exceptionThrown() throws Exception {
        userService.validateResetPasswordToken(FAKE_EMAIL, null);
    }

    @Test(expected = UserException.class)
    public void validateResetPasswordToken_invalidToken_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        // Reset password
        userService.requestResetPassword(FAKE_EMAIL);
        User user = userRepository.findOne(createdUserDTO.getId());

        // validate token
        userService.validateResetPasswordToken(FAKE_EMAIL, user.getEmailTokens().get(0).getToken() + "x");
    }

    @Test(expected = UserException.class)
    public void validateResetPasswordToken_invalidFirstTimeAndSecondTimeValidOneIsInvalidatedToo_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        // Reset password
        userService.requestResetPassword(FAKE_EMAIL);
        User user = userRepository.findOne(createdUserDTO.getId());

        // validate token
        List<EmailToken> emailTokens = getTokenOfType(user, EmailType.RESET_PASSWORD);
        assertThat(emailTokens.size(), is(1));
        String realToken = emailTokens.get(0).getToken();
        try {
            userService.validateResetPasswordToken(FAKE_EMAIL, realToken + "x");
        } catch (UserException ex) {
            // do nothing
        }
        userService.validateResetPasswordToken(FAKE_EMAIL, realToken);
    }

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
