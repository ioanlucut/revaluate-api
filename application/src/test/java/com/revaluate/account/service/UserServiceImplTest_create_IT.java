package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.User;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.account.UserDTOBuilder;
import com.revaluate.domain.account.UserSubscriptionStatus;
import com.revaluate.domain.currency.CurrencyDTO;
import com.revaluate.domain.email.EmailType;
import com.revaluate.email.persistence.EmailToken;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceImplTest_create_IT extends AbstractIntegrationTests {

    @Test
    public void create_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        assertThat(createdUserDTO).isNotNull();
        assertThat(userDTO.getEmail()).isEqualTo(createdUserDTO.getEmail());
        assertThat(userDTO.getFirstName()).isEqualTo(createdUserDTO.getFirstName());
        assertThat(userDTO.getLastName()).isEqualTo(createdUserDTO.getLastName());
        assertThat(userDTO.getPassword()).isNotEqualTo(createdUserDTO.getPassword());
        assertThat(createdUserDTO.getId()).isNotEqualTo(0);
        assertThat(createdUserDTO.getPassword()).isNull();
        assertThat(createdUserDTO.isInitiated()).isFalse();

        //-----------------------------------------------------------------
        // Assert that email token is added
        //-----------------------------------------------------------------
        Optional<User> oneByEmail = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        assertThat(oneByEmail.isPresent()).isTrue();
        Optional<EmailToken> oneByEmailTypeAndUserId = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.CREATED_ACCOUNT, oneByEmail.get().getId());
        assertThat(oneByEmailTypeAndUserId.isPresent()).isTrue();
    }

    @Test
    public void create_validDetailsTrialStatusIsSet_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Assert that trial status is set
        //-----------------------------------------------------------------
        assertThat(createdUserDTO.getUserSubscriptionStatus()).isEqualTo(UserSubscriptionStatus.TRIAL);
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
    public void create_sameEmailTwiceOneWithLowerCaseOneWithUppercase_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        createUserDTO("a@a.a");

        //-----------------------------------------------------------------
        // Try to create again the user with same email but uppercase
        //-----------------------------------------------------------------
        exception.expect(UserException.class);
        createUserDTO("A@A.A");
    }

    @Test
    public void create_sameEmailTwiceSecondNotTrimmed_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        createUserDTO("a@a.a");

        //-----------------------------------------------------------------
        // Try to create again the user with same email but uppercase
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        createUserDTO(" a@a.a    ");
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

        //-----------------------------------------------------------------
        // Should not work if currency is not defined
        //-----------------------------------------------------------------
        exception.expect(UserException.class);
        toCreate = new UserDTOBuilder().withFirstName("fn2").withLastName("fn2").build();
        userService.create(toCreate);
    }
}
