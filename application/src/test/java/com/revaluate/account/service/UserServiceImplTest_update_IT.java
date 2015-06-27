package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserPartialUpdateEnum;
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
        CurrencyDTO currency = new CurrencyDTOBuilder().withCurrencyCode(CurrencyUnit.GBP.getCurrencyCode()).withLocale("").withDisplayName("").withNumericCode(0).withSymbol("E").build();
        CurrencyDTO currencyDTOToUpdate = currencyService.create(currency);

        // Update a user
        UserDTO userDTOToUpdate = new UserDTOBuilder().withId(9999).withEmail("xx@xx.xx2").withFirstName("fn2").withLastName("ln2").withPassword("12345672").withCurrency(currencyDTOToUpdate).build();
        UserDTO updatedUserDTO = userService.update(userDTOToUpdate, userDTO.getId(), UserPartialUpdateEnum.ACCOUNT_DETAILS);

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
    public void update_onlyWithCurrency_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Compute the currency to update
        //-----------------------------------------------------------------
        CurrencyDTO currency = new CurrencyDTOBuilder().withCurrencyCode(CurrencyUnit.GBP.getCurrencyCode()).withLocale("").withDisplayName("").withNumericCode(0).withSymbol("E").build();
        CurrencyDTO currencyDTOToUpdate = currencyService.create(currency);

        //-----------------------------------------------------------------
        // Update a user only with currency - works
        //-----------------------------------------------------------------
        UserDTO userDTOToUpdate = new UserDTOBuilder().withCurrency(currencyDTOToUpdate).build();
        userService.update(userDTOToUpdate, createdUserDTO.getId(), UserPartialUpdateEnum.ACCOUNT_DETAILS);
    }

    @Test
    public void update_onlyFirstNameAndLastNameAreUpdated_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user and prepare it
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Prepared user
        //-----------------------------------------------------------------
        User user = userRepository.findOne(createdUserDTO.getId());
        user.setEmail("a@b.c");
        user.setLastName("last");
        user.setFirstName("first");
        user.setEmailConfirmed(true);
        user.setInitiated(true);
        user.setEnabled(true);
        User originalUser = userRepository.save(user);

        //-----------------------------------------------------------------
        // Compute the currency to update
        //-----------------------------------------------------------------
        CurrencyDTO currency = new CurrencyDTOBuilder().withCurrencyCode(CurrencyUnit.GBP.getCurrencyCode()).withLocale("").withDisplayName("").withNumericCode(0).withSymbol("E").build();
        UserDTO userDTOToUpdate = new UserDTOBuilder().withCurrency(currency).withEmail("c@d.e").withFirstName("first2").withLastName("last2").withEmailConfirmed(false).withInitiated(false).build();
        User mappedUser = userRepository.findOne(createdUserDTO.getId());
        dozerBeanMapper.map(userDTOToUpdate, mappedUser, UserPartialUpdateEnum.ACCOUNT_DETAILS.getMapId());

        //-----------------------------------------------------------------
        // Those are not updated
        //-----------------------------------------------------------------
        assertThat(mappedUser.getEmail(), is(equalTo(originalUser.getEmail())));
        assertThat(mappedUser.getCurrency().getCurrencyCode(), is(equalTo(originalUser.getCurrency().getCurrencyCode())));
        assertThat(mappedUser.isEmailConfirmed(), is(equalTo(originalUser.isEmailConfirmed())));
        assertThat(mappedUser.isInitiated(), is(equalTo(originalUser.isInitiated())));

        //-----------------------------------------------------------------
        // Only those are updated
        //-----------------------------------------------------------------
        assertThat(mappedUser.getFirstName(), is(equalTo(userDTOToUpdate.getFirstName())));
        assertThat(mappedUser.getLastName(), is(equalTo(userDTOToUpdate.getLastName())));
    }

    @Test
    public void update_onlyWithInitiatedTrue_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Update a user only with currency - works
        //-----------------------------------------------------------------
        UserDTO userDTOToUpdate = new UserDTOBuilder().withInitiated(Boolean.TRUE).build();
        userService.update(userDTOToUpdate, createdUserDTO.getId(), UserPartialUpdateEnum.ACCOUNT_DETAILS);
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
        userService.update(userDTOToUpdate, userDTO.getId(), UserPartialUpdateEnum.ACCOUNT_DETAILS);

        //-----------------------------------------------------------------
        // Expect constraint violation if updates only with first name
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        userDTOToUpdate = new UserDTOBuilder().withFirstName("fn2").build();
        userService.update(userDTOToUpdate, userDTO.getId(), UserPartialUpdateEnum.ACCOUNT_DETAILS);

        //-----------------------------------------------------------------
        // Expect constraint violation if updates only with last name
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        userDTOToUpdate = new UserDTOBuilder().withLastName("fn2").build();
        userService.update(userDTOToUpdate, userDTO.getId(), UserPartialUpdateEnum.ACCOUNT_DETAILS);

        //-----------------------------------------------------------------
        // Expect constraint violation if User DTO is null
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        userService.update(null, userDTO.getId(), UserPartialUpdateEnum.ACCOUNT_DETAILS);

        //-----------------------------------------------------------------
        // Expect constraint violation if updates with inexisting currency
        //-----------------------------------------------------------------
        exception.expect(UserException.class);
        CurrencyDTO currency = new CurrencyDTO();
        currency.setCurrencyCode("INEXISTING");
        userDTOToUpdate = new UserDTOBuilder().withFirstName("fn2").withLastName("fn2").withCurrency(currency).build();
        userService.update(userDTOToUpdate, userDTO.getId(), UserPartialUpdateEnum.ACCOUNT_DETAILS);
    }

    @Test
    public void update_partialUpdateAccountDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();
        UserDTO userDTOToUpdate;

        User originalUser = userRepository.findOne(createdUserDTO.getId());
        originalUser.setEmail("a@b.c");
        originalUser.setLastName("last");
        originalUser.setFirstName("first");
        originalUser.setEmailConfirmed(false);
        originalUser.setInitiated(false);
        originalUser.setEnabled(false);
        originalUser = userRepository.save(originalUser);

        User originalUserToBeFilled = userRepository.findOne(createdUserDTO.getId());
        userDTOToUpdate = new UserDTOBuilder().withEmail("c@d.e").withFirstName("first2").withLastName("last2").withEmailConfirmed(true).withInitiated(true).build();
        dozerBeanMapper.map(userDTOToUpdate, originalUserToBeFilled, UserPartialUpdateEnum.ACCOUNT_DETAILS.getMapId());

        assertThat(originalUserToBeFilled.isInitiated(), is(equalTo(originalUser.isInitiated())));
        assertThat(originalUserToBeFilled.isEmailConfirmed(), is(equalTo(originalUser.isEmailConfirmed())));

        assertThat(originalUserToBeFilled.getLastName(), is(equalTo(userDTOToUpdate.getLastName())));
        assertThat(originalUserToBeFilled.getFirstName(), is(equalTo(userDTOToUpdate.getFirstName())));
    }

    @Test
    public void update_partialUpdateInitiatedStatus_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();
        UserDTO userDTOToUpdate;

        User originalUser = userRepository.findOne(createdUserDTO.getId());
        originalUser.setEmail("a@b.c");
        originalUser.setLastName("last");
        originalUser.setFirstName("first");
        originalUser.setEmailConfirmed(false);
        originalUser.setInitiated(false);
        originalUser.setEnabled(false);
        originalUser = userRepository.save(originalUser);

        User originalUserToBeFilled = userRepository.findOne(createdUserDTO.getId());
        userDTOToUpdate = new UserDTOBuilder().withEmail("c@d.e").withFirstName("first2").withLastName("last2").withEmailConfirmed(true).withInitiated(true).build();
        dozerBeanMapper.map(userDTOToUpdate, originalUserToBeFilled, UserPartialUpdateEnum.INITIATED_STATUS.getMapId());

        assertThat(originalUserToBeFilled.isInitiated(), is(equalTo(userDTOToUpdate.isInitiated())));
        assertThat(originalUserToBeFilled.isEmailConfirmed(), is(equalTo(originalUser.isEmailConfirmed())));

        assertThat(originalUserToBeFilled.getLastName(), is(equalTo(originalUser.getLastName())));
        assertThat(originalUserToBeFilled.getFirstName(), is(equalTo(originalUser.getFirstName())));
    }
}
