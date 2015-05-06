package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.exception.UserException;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.account.UserDTOBuilder;
import com.revaluate.domain.currency.CurrencyDTO;
import com.revaluate.domain.currency.CurrencyDTOBuilder;
import org.joda.money.CurrencyUnit;
import org.junit.Test;

import javax.validation.ConstraintViolationException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class UserServiceImplTest_updateCurrency_IT extends AbstractIntegrationTests {

    @Test
    public void updateCurrency_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO userDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Compute the currency to update
        //-----------------------------------------------------------------
        CurrencyDTO currency = new CurrencyDTOBuilder().withCurrencyCode(CurrencyUnit.GBP.getCurrencyCode()).withDisplayName("").withNumericCode(0).build();
        CurrencyDTO currencyDTOToUpdate = currencyService.create(currency);

        // Update a user
        UserDTO userDTOToUpdate = new UserDTOBuilder().withId(9999).withEmail("xx@xx.xx2").withFirstName("fn2").withLastName("ln2").withPassword("12345672").withCurrency(currencyDTOToUpdate).build();
        UserDTO updatedUserDTO = userService.updateCurrency(userDTOToUpdate, userDTO.getId());

        //-----------------------------------------------------------------
        // Simply ignored
        //-----------------------------------------------------------------
        assertThat(updatedUserDTO, is(notNullValue()));
        assertThat(updatedUserDTO.getFirstName(), not(equalTo(userDTOToUpdate.getFirstName())));
        assertThat(updatedUserDTO.getLastName(), not(equalTo(userDTOToUpdate.getLastName())));
        assertThat(updatedUserDTO.isInitiated(), is(false));
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
    public void updateCurrency_withValidDetailsWithoutPasswordAndEmail_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Compute the currency to update
        //-----------------------------------------------------------------
        CurrencyDTO currency = new CurrencyDTOBuilder().withCurrencyCode(CurrencyUnit.GBP.getCurrencyCode()).withDisplayName("").withNumericCode(0).build();
        CurrencyDTO currencyDTOToUpdate = currencyService.create(currency);

        //-----------------------------------------------------------------
        // Update a user without id, email and password - works
        //-----------------------------------------------------------------
        UserDTO userDTOToUpdate = new UserDTOBuilder().withFirstName("fn2").withLastName("ln2").withCurrency(currencyDTOToUpdate).build();
        userService.updateCurrency(userDTOToUpdate, createdUserDTO.getId());
    }

    @Test
    public void updateCurrency_onlyWithCurrency_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Compute the currency to update
        //-----------------------------------------------------------------
        CurrencyDTO currency = new CurrencyDTOBuilder().withCurrencyCode(CurrencyUnit.GBP.getCurrencyCode()).withDisplayName("").withNumericCode(0).build();
        CurrencyDTO currencyDTOToUpdate = currencyService.create(currency);

        //-----------------------------------------------------------------
        // Update a user only with currency - works
        //-----------------------------------------------------------------
        UserDTO userDTOToUpdate = new UserDTOBuilder().withCurrency(currencyDTOToUpdate).build();
        userService.updateCurrency(userDTOToUpdate, createdUserDTO.getId());
    }

    @Test
    public void updateCurrency_invalidDetails_handledCorrectly() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO userDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Expect constraint violation if updates with an invalid user DTO
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        UserDTO userDTOToUpdate = new UserDTOBuilder().build();
        userService.updateCurrency(userDTOToUpdate, userDTO.getId());

        //-----------------------------------------------------------------
        // Not expect constraint violation if updates only with first name
        //-----------------------------------------------------------------
        userDTOToUpdate = new UserDTOBuilder().withFirstName("fn2").build();
        userService.updateCurrency(userDTOToUpdate, userDTO.getId());

        //-----------------------------------------------------------------
        // Not expect constraint violation if updates only with last name
        //-----------------------------------------------------------------
        userDTOToUpdate = new UserDTOBuilder().withLastName("fn2").build();
        userService.updateCurrency(userDTOToUpdate, userDTO.getId());

        //-----------------------------------------------------------------
        // Expect constraint violation if User DTO is null
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        userService.updateCurrency(null, userDTO.getId());

        //-----------------------------------------------------------------
        // Expect constraint violation if updates with inexisting currency
        //-----------------------------------------------------------------
        exception.expect(UserException.class);
        CurrencyDTO currency = new CurrencyDTO();
        currency.setCurrencyCode("INEXISTING");
        userDTOToUpdate = new UserDTOBuilder().withFirstName("fn2").withLastName("fn2").withCurrency(currency).build();
        userService.updateCurrency(userDTOToUpdate, userDTO.getId());

        //-----------------------------------------------------------------
        // Expect exception if updates only with currency which does not exists
        //-----------------------------------------------------------------
        exception.expect(UserException.class);
        currency = new CurrencyDTO();
        currency.setCurrencyCode("USD");
        userDTOToUpdate = new UserDTOBuilder().withCurrency(currency).build();
        userService.updateCurrency(userDTOToUpdate, userDTO.getId());

        //-----------------------------------------------------------------
        // Not expect exception if updates only with currency which exists
        //-----------------------------------------------------------------
        currency = new CurrencyDTO();
        currency.setCurrencyCode("EUR");
        userDTOToUpdate = new UserDTOBuilder().withCurrency(currency).build();
        userService.updateCurrency(userDTOToUpdate, userDTO.getId());
    }
}