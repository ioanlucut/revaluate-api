package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.domain.UserDTO;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;

public class UserServiceImplTest_remove_IT extends AbstractIntegrationTests {

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
}
