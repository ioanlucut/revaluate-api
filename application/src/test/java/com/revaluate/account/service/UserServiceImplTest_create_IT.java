package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.domain.UserDTO;
import com.revaluate.account.persistence.EmailType;
import com.revaluate.account.persistence.User;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class UserServiceImplTest_create_IT extends AbstractIntegrationTests {

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
}
