package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.User;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.account.UserSubscriptionStatus;
import com.revaluate.domain.account.UserType;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceImplTest_loginViaOauth_IT extends AbstractIntegrationTests {

    @Test
    public void loginViaOauth_happyFlow_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO(Boolean.TRUE, UserType.OAUTH_FACEBOOK);

        //-----------------------------------------------------------------
        // Try to login
        //-----------------------------------------------------------------
        userService.loginViaOauth(createdUserDTO.getEmail(), UserType.OAUTH_FACEBOOK);
    }

    @Test
    public void loginViaOauth_happyFlowButAnotherProvider_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO(Boolean.TRUE, UserType.OAUTH_FACEBOOK);

        //-----------------------------------------------------------------
        // Try to login
        //-----------------------------------------------------------------
        userService.loginViaOauth(createdUserDTO.getEmail(), UserType.OAUTH_GOOGLE);
    }

    @Test
    public void loginViaOauth_withSignUpUserButEmailNotConfirmed_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Try to login
        //-----------------------------------------------------------------
        exception.expect(UserException.class);
        userService.loginViaOauth(createdUserDTO.getEmail(), UserType.OAUTH_GOOGLE);
    }

    @Test
    public void loginViaOauth_withSignUpUserButEmailConfirmed_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();
        User user = userRepository.findOneById(createdUserDTO.getId()).get();
        user.setEmailConfirmed(Boolean.TRUE);
        userRepository.save(user);

        //-----------------------------------------------------------------
        // Try to login
        //-----------------------------------------------------------------
        userService.loginViaOauth(createdUserDTO.getEmail(), UserType.OAUTH_GOOGLE);
    }

    @Test
    public void loginViaOauth_invalidCredentials_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO(Boolean.TRUE, UserType.OAUTH_FACEBOOK);

        //-----------------------------------------------------------------
        // Try to login
        //-----------------------------------------------------------------
        exception.expect(UserException.class);
        userService.loginViaOauth(createdUserDTO.getEmail() + "x", UserType.OAUTH_FACEBOOK);
    }

    @Test
    public void loginViaOauth_initialTrialStatusSet_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO(Boolean.TRUE, UserType.OAUTH_FACEBOOK);

        assertThat(createdUserDTO.getUserSubscriptionStatus()).isEqualTo(UserSubscriptionStatus.TRIAL);

        //-----------------------------------------------------------------
        // Try to login
        //-----------------------------------------------------------------
        userService.loginViaOauth(createdUserDTO.getEmail(), UserType.OAUTH_FACEBOOK);

        //-----------------------------------------------------------------
        // Check payment status
        //-----------------------------------------------------------------
        Optional<User> oneByEmail = userRepository.findOneByEmailIgnoreCase(createdUserDTO.getEmail());
        assertThat(oneByEmail.isPresent()).isTrue();
        assertThat(oneByEmail.get().getUserSubscriptionStatus()).isEqualTo(UserSubscriptionStatus.TRIAL);
    }

}
