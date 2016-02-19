package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.User;
import com.revaluate.domain.account.LoginDTO;
import com.revaluate.domain.account.LoginDTOBuilder;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.account.UserSubscriptionStatus;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void login_initialTrialStatusSet_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        assertThat(createdUserDTO.getUserSubscriptionStatus()).isEqualTo(UserSubscriptionStatus.TRIAL);

        //-----------------------------------------------------------------
        // Try to login
        //-----------------------------------------------------------------
        LoginDTO loginDTO = new LoginDTOBuilder().withEmail(createdUserDTO.getEmail()).withPassword(TEST_PASSWORD).build();
        userService.login(loginDTO);

        //-----------------------------------------------------------------
        // Check payment status
        //-----------------------------------------------------------------
        Optional<User> oneByEmail = userRepository.findOneByEmailIgnoreCase(createdUserDTO.getEmail());
        assertThat(oneByEmail.isPresent()).isTrue();
        assertThat(oneByEmail.get().getUserSubscriptionStatus()).isEqualTo(UserSubscriptionStatus.TRIAL);
    }

    /*@Test
    public void login_trialStatusSetAsExpired_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        assertThat(createdUserDTO.getUserSubscriptionStatus()).isEqualTo(UserSubscriptionStatus.TRIAL)));

        //-----------------------------------------------------------------
        // Try to login
        //-----------------------------------------------------------------
        LoginDTO loginDTO = new LoginDTOBuilder().withEmail(createdUserDTO.getEmail()).withPassword(TEST_PASSWORD).build();
        userService.login(loginDTO);

        //-----------------------------------------------------------------
        // Check payment status
        //-----------------------------------------------------------------
        Optional<User> oneByEmail = userRepository.findOneByEmailIgnoreCase(createdUserDTO.getEmail());
        assertThat(oneByEmail.isPresent()).isTrue();
        User user = oneByEmail.get();
        assertThat(user.getUserSubscriptionStatus()).isEqualTo(UserSubscriptionStatus.TRIAL)));

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
        oneByEmail = userRepository.findOneByEmailIgnoreCase(createdUserDTO.getEmail());
        assertThat(oneByEmail.isPresent()).isTrue();
        assertThat(oneByEmail.get().getUserSubscriptionStatus()).isEqualTo(UserSubscriptionStatus.TRIAL_EXPIRED)));
    }
*/
}
