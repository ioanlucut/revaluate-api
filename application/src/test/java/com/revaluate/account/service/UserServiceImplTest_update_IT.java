package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.exception.UserException;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.account.UserDTOBuilder;
import com.revaluate.domain.currency.CurrencyDTO;
import org.joda.money.CurrencyUnit;
import org.junit.Test;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class UserServiceImplTest_update_IT extends AbstractIntegrationTests {

    @Test
    public void update_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO userDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Compute the currency to update
        //-----------------------------------------------------------------
        CurrencyDTO currency = new CurrencyDTO();
        currency.setCurrencyCode(CurrencyUnit.GBP.getCurrencyCode());
        CurrencyDTO currencyDTOToUpdate = currencyService.create(currency);

        // Update a user
        UserDTO userDTOToUpdate = new UserDTOBuilder().withId(9999).withEmail("xx@xx.xx2").withFirstName("fn2").withLastName("ln2").withPassword("12345672").withCurrency(currencyDTOToUpdate).build();
        UserDTO updatedUserDTO = userService.update(userDTOToUpdate, userDTO.getId());

        assertThat(updatedUserDTO, is(notNullValue()));
        assertThat(updatedUserDTO.getFirstName(), equalTo(userDTOToUpdate.getFirstName()));
        assertThat(updatedUserDTO.getLastName(), equalTo(userDTOToUpdate.getLastName()));
        assertThat(updatedUserDTO.isInitiated(), is(false));

        //-----------------------------------------------------------------
        // Given email is ignored
        //-----------------------------------------------------------------
        assertThat(updatedUserDTO.getEmail(), not(equalTo(userDTOToUpdate.getEmail())));

        //-----------------------------------------------------------------
        // Given ID is ignored
        //-----------------------------------------------------------------
        assertThat(updatedUserDTO.getId(), not(equalTo(0)));
        assertThat(updatedUserDTO.getId(), equalTo(userDTO.getId()));

        //-----------------------------------------------------------------
        // Currency is updated
        //-----------------------------------------------------------------
        assertThat(updatedUserDTO.getCurrency().getCurrencyCode(), not(equalTo(userDTO.getCurrency().getCurrencyCode())));
        assertThat(updatedUserDTO.getCurrency().getCurrencyCode(), equalTo(currency.getCurrencyCode()));

        //-----------------------------------------------------------------
        // New password if provided is ignored
        //-----------------------------------------------------------------
        assertThat(updatedUserDTO.getPassword(), is(nullValue()));
    }

    @Test
    public void update_withValidDetailsWithoutPasswordAndEmail_ok() throws Exception {
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

        //-----------------------------------------------------------------
        // Update a user without id, email and password - works
        //-----------------------------------------------------------------
        UserDTO userDTOToUpdate = new UserDTOBuilder().withFirstName("fn2").withLastName("ln2").withCurrency(currencyDTOToUpdate).build();
        userService.update(userDTOToUpdate, createdUserDTO.getId());
    }

    @Test
    public void update_invalidDetails_handledCorrectly() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO userDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Expect constraint violation if updates with an invalid user DTO
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        UserDTO userDTOToUpdate = new UserDTOBuilder().build();
        userService.update(userDTOToUpdate, userDTO.getId());

        //-----------------------------------------------------------------
        // Expect constraint violation if updates only with first name
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        userDTOToUpdate = new UserDTOBuilder().withFirstName("fn2").build();
        userService.update(userDTOToUpdate, userDTO.getId());

        //-----------------------------------------------------------------
        // Expect constraint violation if updates only with last name
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        userDTOToUpdate = new UserDTOBuilder().withLastName("fn2").build();
        userService.update(userDTOToUpdate, userDTO.getId());

        //-----------------------------------------------------------------
        // Expect constraint violation if User DTO is null
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        userService.update(null, userDTO.getId());

        //-----------------------------------------------------------------
        // Expect constraint violation if updates with inexisting currency
        //-----------------------------------------------------------------
        exception.expect(UserException.class);
        CurrencyDTO currency = new CurrencyDTO();
        currency.setCurrencyCode("INEXISTING");
        userDTOToUpdate = new UserDTOBuilder().withFirstName("fn2").withLastName("fn2").withCurrency(currency).build();
        userService.update(userDTOToUpdate, userDTO.getId());
    }
}
