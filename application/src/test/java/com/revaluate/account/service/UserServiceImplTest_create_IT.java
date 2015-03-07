package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.domain.UserDTO;
import com.revaluate.account.domain.UserDTOBuilder;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.EmailType;
import com.revaluate.account.persistence.User;
import com.revaluate.currency.domain.CurrencyDTO;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
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

    @Test
    public void create_sameEmailTwice_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        createUserDTO();

        //-----------------------------------------------------------------
        // Try to create again the user with same email
        //-----------------------------------------------------------------
        exception.expect(UserException.class);
        createUserDTO();
    }

    @Test
    public void create_invalidDetails_handledCorrectly() throws Exception {
        //-----------------------------------------------------------------
        // Expect constraint violation if creates with an invalid user DTO
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        UserDTO toCreate = new UserDTOBuilder().build();
        userService.create(toCreate);

        //-----------------------------------------------------------------
        // Expect constraint violation if updates only with first name
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        toCreate = new UserDTOBuilder().withFirstName("fn2").build();
        userService.create(toCreate);

        //-----------------------------------------------------------------
        // Expect constraint violation if updates only with last name
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        toCreate = new UserDTOBuilder().withLastName("fn2").build();
        userService.create(toCreate);

        //-----------------------------------------------------------------
        // Expect constraint violation if User DTO is null
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        userService.create(null);

        //-----------------------------------------------------------------
        // Expect constraint violation if creates with in existing currency
        //-----------------------------------------------------------------
        exception.expect(UserException.class);
        CurrencyDTO currency = new CurrencyDTO();
        currency.setCurrencyCode("INEXISTING");
        toCreate = new UserDTOBuilder().withFirstName("fn2").withLastName("fn2").withCurrency(currency).build();
        userService.create(toCreate);
    }
}
