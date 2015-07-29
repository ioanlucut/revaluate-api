package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.User;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.account.UserSubscriptionStatus;
import com.revaluate.domain.account.UserType;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

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
    public void loginViaOauth_happyFlowButAnotherProvider_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO(Boolean.TRUE, UserType.OAUTH_FACEBOOK);

        //-----------------------------------------------------------------
        // Try to login
        //-----------------------------------------------------------------
        exception.expect(UserException.class);
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

        assertThat(createdUserDTO.getUserSubscriptionStatus(), is(equalTo(UserSubscriptionStatus.TRIAL)));

        //-----------------------------------------------------------------
        // Try to login
        //-----------------------------------------------------------------
        userService.loginViaOauth(createdUserDTO.getEmail(), UserType.OAUTH_FACEBOOK);

        //-----------------------------------------------------------------
        // Check payment status
        //-----------------------------------------------------------------
        Optional<User> oneByEmail = userRepository.findOneByEmailIgnoreCase(createdUserDTO.getEmail());
        assertThat(oneByEmail.isPresent(), is(true));
        assertThat(oneByEmail.get().getUserSubscriptionStatus(), is(equalTo(UserSubscriptionStatus.TRIAL)));
    }

}
