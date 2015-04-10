package com.revaluate.currency.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.account.persistence.User;
import com.revaluate.domain.currency.CurrencyDTO;
import com.revaluate.domain.currency.CurrencyDTOBuilder;
import com.revaluate.currency.exception.CurrencyException;
import org.joda.money.CurrencyUnit;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class CurrencyServiceImplTestIT extends AbstractIntegrationTests {

    @Test
    public void isUniqueCurrencyCode_validCurrencyCode_ok() throws Exception {
        assertThat(currencyService.isUnique(CurrencyUnit.USD.getCode()), is(true));
    }

    @Test
    public void isUniqueCurrencyCode_existingCurrencyCode_isFalse() throws Exception {
        //-----------------------------------------------------------------
        // Create currency
        //-----------------------------------------------------------------
        CurrencyDTO currencyDTO = new CurrencyDTOBuilder().withCurrencyCode(CurrencyUnit.USD.getCurrencyCode()).withDisplayName("").withNumericCode(0).build();
        currencyService.create(currencyDTO);

        assertThat(currencyService.isUnique(currencyDTO.getCurrencyCode()), is(false));
    }

    @Test
    public void create_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create currency
        //-----------------------------------------------------------------
        CurrencyDTO currencyDTO = new CurrencyDTOBuilder().withCurrencyCode(CurrencyUnit.USD.getCurrencyCode()).withDisplayName("").withNumericCode(0).build();
        CurrencyDTO createdCurrencyDTO = currencyService.create(currencyDTO);

        assertThat(createdCurrencyDTO, is(notNullValue()));
        assertThat(currencyDTO.getCurrencyCode(), equalTo(createdCurrencyDTO.getCurrencyCode()));

        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO(TEST_EMAIL, createdCurrencyDTO);

        //-----------------------------------------------------------------
        // Check user with currency added
        //-----------------------------------------------------------------
        User user = userRepository.findOne(createdUserDTO.getId());
        assertThat(user, is(notNullValue()));
        assertThat(user.getCurrency(), is(notNullValue()));
        assertThat(user.getCurrency().getId(), not(equalTo(0)));
        assertThat(user.getCurrency().getCurrencyCode(), is(equalTo(createdCurrencyDTO.getCurrencyCode())));
    }

    //-----------------------------------------------------------------
    // Find all currencies work
    //-----------------------------------------------------------------
    @Test
    public void findAll_existing_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create two currencies
        //-----------------------------------------------------------------
        CurrencyDTO currencyToCreateDTO = new CurrencyDTOBuilder().withCurrencyCode(CurrencyUnit.EUR.getCurrencyCode()).withDisplayName("").withNumericCode(0).build();
        currencyService.create(currencyToCreateDTO);
        currencyToCreateDTO = new CurrencyDTOBuilder().withCurrencyCode(CurrencyUnit.USD.getCurrencyCode()).withDisplayName("").withNumericCode(0).build();
        currencyService.create(currencyToCreateDTO);

        //-----------------------------------------------------------------
        // Find created currencies + asserts
        //-----------------------------------------------------------------
        List<CurrencyDTO> allCategoriesFor = currencyService.findAllCurrencies();
        assertThat(allCategoriesFor, is(notNullValue()));
        assertThat(allCategoriesFor.size(), is(equalTo(2)));
    }

    @Test
    public void remove_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create currency
        //-----------------------------------------------------------------
        CurrencyDTO currencyDTO = new CurrencyDTOBuilder().withCurrencyCode(CurrencyUnit.EUR.getCurrencyCode()).withDisplayName("").withNumericCode(0).build();
        CurrencyDTO createdCurrencyDTO = currencyService.create(currencyDTO);

        assertThat(createdCurrencyDTO, is(notNullValue()));
        assertThat(currencyDTO.getCurrencyCode(), equalTo(createdCurrencyDTO.getCurrencyCode()));

        //-----------------------------------------------------------------
        // Remove the currency
        //-----------------------------------------------------------------
        currencyService.remove(createdCurrencyDTO.getCurrencyCode());

        //-----------------------------------------------------------------
        // Find created currencies + asserts
        //-----------------------------------------------------------------
        List<CurrencyDTO> allCategoriesFor = currencyService.findAllCurrencies();
        assertThat(allCategoriesFor, is(notNullValue()));
        assertThat(allCategoriesFor.size(), is(equalTo(0)));
    }

    @Test(expected = CurrencyException.class)
    public void remove_currencyInvalid_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Remove invalid currency id
        //-----------------------------------------------------------------
        currencyService.remove("xxxxxx");
    }
}
