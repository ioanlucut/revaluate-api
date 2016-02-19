package com.revaluate.currency.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.persistence.User;
import com.revaluate.currency.exception.CurrencyException;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.currency.CurrencyDTO;
import com.revaluate.domain.currency.CurrencyDTOBuilder;
import org.joda.money.CurrencyUnit;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CurrencyServiceImplTestIT extends AbstractIntegrationTests {

    @Test
    public void isUniqueCurrencyCode_validCurrencyCode_ok() throws Exception {
        assertThat(currencyService.isUnique(CurrencyUnit.USD.getCode())).isTrue();
    }

    @Test
    public void isUniqueCurrencyCode_existingCurrencyCode_isFalse() throws Exception {
        //-----------------------------------------------------------------
        // Create currency
        //-----------------------------------------------------------------
        CurrencyDTO currencyDTO = new CurrencyDTOBuilder().withCurrencyCode(CurrencyUnit.USD.getCurrencyCode()).withDisplayName("").withNumericCode(0).withSymbol("$").build();
        currencyService.create(currencyDTO);

        assertThat(currencyService.isUnique(currencyDTO.getCurrencyCode())).isFalse();
    }

    @Test
    public void create_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create currency
        //-----------------------------------------------------------------
        CurrencyDTO currencyDTO = new CurrencyDTOBuilder().withCurrencyCode(CurrencyUnit.USD.getCurrencyCode()).withDisplayName("").withNumericCode(0).withSymbol("$").build();
        CurrencyDTO createdCurrencyDTO = currencyService.create(currencyDTO);

        assertThat(createdCurrencyDTO).isNotNull();
        assertThat(currencyDTO.getCurrencyCode()).isEqualTo(createdCurrencyDTO.getCurrencyCode());

        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO(TEST_EMAIL, createdCurrencyDTO);

        //-----------------------------------------------------------------
        // Check user with currency added
        //-----------------------------------------------------------------
        User user = userRepository.findOne(createdUserDTO.getId());
        assertThat(user).isNotNull();
        assertThat(user.getCurrency()).isNotNull();
        assertThat(user.getCurrency().getId()).isNotEqualTo(0);
        assertThat(user.getCurrency().getCurrencyCode()).isEqualTo(createdCurrencyDTO.getCurrencyCode());
    }

    //-----------------------------------------------------------------
    // Find all currencies work
    //-----------------------------------------------------------------
    @Test
    public void findAll_existing_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create two currencies
        //-----------------------------------------------------------------
        CurrencyDTO currencyToCreateDTO = new CurrencyDTOBuilder().withCurrencyCode(CurrencyUnit.EUR.getCurrencyCode()).withDisplayName("").withNumericCode(0).withSymbol("E").build();
        currencyService.create(currencyToCreateDTO);
        currencyToCreateDTO = new CurrencyDTOBuilder().withCurrencyCode(CurrencyUnit.USD.getCurrencyCode()).withDisplayName("").withNumericCode(0).withSymbol("$").build();
        currencyService.create(currencyToCreateDTO);

        //-----------------------------------------------------------------
        // Find created currencies + asserts
        //-----------------------------------------------------------------
        List<CurrencyDTO> allCategoriesFor = currencyService.findAllCurrencies();
        assertThat(allCategoriesFor).isNotNull();
        assertThat(allCategoriesFor.size()).isEqualTo(2);
    }

    @Test
    public void remove_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create currency
        //-----------------------------------------------------------------
        CurrencyDTO currencyDTO = new CurrencyDTOBuilder().withCurrencyCode(CurrencyUnit.EUR.getCurrencyCode()).withDisplayName("").withNumericCode(0).withSymbol("E").build();
        CurrencyDTO createdCurrencyDTO = currencyService.create(currencyDTO);

        assertThat(createdCurrencyDTO).isNotNull();
        assertThat(currencyDTO.getCurrencyCode()).isEqualTo(createdCurrencyDTO.getCurrencyCode());

        //-----------------------------------------------------------------
        // Remove the currency
        //-----------------------------------------------------------------
        currencyService.remove(createdCurrencyDTO.getCurrencyCode());

        //-----------------------------------------------------------------
        // Find created currencies + asserts
        //-----------------------------------------------------------------
        List<CurrencyDTO> allCategoriesFor = currencyService.findAllCurrencies();
        assertThat(allCategoriesFor).isNotNull();
        assertThat(allCategoriesFor.size()).isEqualTo(0);
    }

    @Test(expected = CurrencyException.class)
    public void remove_currencyInvalid_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Remove invalid currency id
        //-----------------------------------------------------------------
        currencyService.remove("xxxxxx");
    }
}
