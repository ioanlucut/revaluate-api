package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.User;
import com.revaluate.domain.account.LoginDTO;
import com.revaluate.domain.account.LoginDTOBuilder;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.account.UserSubscriptionStatus;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

public class UserServiceImplTest_login_IT extends AbstractIntegrationTests {

    @Test
    public void login_happyFlow_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Try to login
        //-----------------------------------------------------------------
        LoginDTO loginDTO = new LoginDTOBuilder().withEmail(createdUserDTO.getEmail()).withPassword(TEST_PASSWORD).build();
        userService.login(loginDTO);
    }

    @Test
    public void login_invalidCredentials_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Try to login
        //-----------------------------------------------------------------
        LoginDTO loginDTO = new LoginDTOBuilder().withEmail(createdUserDTO.getEmail()).withPassword(TEST_PASSWORD_WRONG).build();

        exception.expect(UserException.class);
        userService.login(loginDTO);
    }

    @Test
    public void login_trialCheckIgnoredWhileInTrial_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        assertThat(createdUserDTO.getUserSubscriptionStatus(), is(equalTo(UserSubscriptionStatus.TRIAL)));

        //-----------------------------------------------------------------
        // Try to login
        //-----------------------------------------------------------------
        LoginDTO loginDTO = new LoginDTOBuilder().withEmail(createdUserDTO.getEmail()).withPassword(TEST_PASSWORD).build();
        userService.login(loginDTO);

        //-----------------------------------------------------------------
        // Check payment status
        //-----------------------------------------------------------------
        Optional<User> oneByEmail = userRepository.findOneByEmail(createdUserDTO.getEmail());
        assertThat(oneByEmail.isPresent(), is(true));
        assertThat(oneByEmail.get().getUserSubscriptionStatus(), is(equalTo(UserSubscriptionStatus.TRIAL)));
    }

    @Test
    public void login_trialStatusSetAsExpired_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        assertThat(createdUserDTO.getUserSubscriptionStatus(), is(equalTo(UserSubscriptionStatus.TRIAL)));

        //-----------------------------------------------------------------
        // Try to login
        //-----------------------------------------------------------------
        LoginDTO loginDTO = new LoginDTOBuilder().withEmail(createdUserDTO.getEmail()).withPassword(TEST_PASSWORD).build();
        userService.login(loginDTO);

        //-----------------------------------------------------------------
        // Check payment status
        //-----------------------------------------------------------------
        Optional<User> oneByEmail = userRepository.findOneByEmail(createdUserDTO.getEmail());
        assertThat(oneByEmail.isPresent(), is(true));
        User user = oneByEmail.get();
        assertThat(user.getUserSubscriptionStatus(), is(equalTo(UserSubscriptionStatus.TRIAL)));

        //-----------------------------------------------------------------
        // Now change the user created date
        //-----------------------------------------------------------------
        user.setCreatedDate(LocalDateTime.now().minusDays(15));
        userRepository.save(user);

        //-----------------------------------------------------------------
        // Try to login again
        //-----------------------------------------------------------------
        loginDTO = new LoginDTOBuilder().withEmail(createdUserDTO.getEmail()).withPassword(TEST_PASSWORD).build();
        userService.login(loginDTO);

        //-----------------------------------------------------------------
        // Check payment status
        //-----------------------------------------------------------------
        oneByEmail = userRepository.findOneByEmail(createdUserDTO.getEmail());
        assertThat(oneByEmail.isPresent(), is(true));
        assertThat(oneByEmail.get().getUserSubscriptionStatus(), is(equalTo(UserSubscriptionStatus.TRIAL_EXPIRED)));
    }

}
