package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.persistence.User;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.account.UserDTOBuilder;
import com.revaluate.domain.account.UserSubscriptionStatus;
import com.revaluate.domain.account.UserType;
import com.revaluate.domain.currency.CurrencyDTO;
import com.revaluate.domain.email.EmailType;
import com.revaluate.email.persistence.EmailToken;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class UserServiceImplTest_createViaOauth_IT extends AbstractIntegrationTests {

    @Test
    public void createViaOauth_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO(Boolean.TRUE, UserType.OAUTH_FACEBOOK);

        assertThat(createdUserDTO).isNotNull();
        assertThat(createdUserDTO.getEmail()).isEqualTo(createdUserDTO.getEmail());
        assertThat(createdUserDTO.getFirstName()).isEqualTo(createdUserDTO.getFirstName());
        assertThat(createdUserDTO.getLastName()).isEqualTo(createdUserDTO.getLastName());
        assertThat(createdUserDTO.getPassword()).isNull();
        assertThat(createdUserDTO.getId()).isNotEqualTo(0);
        assertThat(createdUserDTO.isInitiated()).isFalse();
        assertThat(createdUserDTO.isEmailConfirmed()).isTrue();
        assertThat(createdUserDTO.getUserType()).isEqualTo(UserType.OAUTH_FACEBOOK);
        assertThat(createdUserDTO.isConnectedViaOauth()).isTrue();

        //-----------------------------------------------------------------
        // Assert that email token is NOT added
        //-----------------------------------------------------------------
        Optional<User> oneByEmail = userRepository.findOneByEmailIgnoreCase(userDTO.getEmail());
        assertThat(oneByEmail.isPresent()).isTrue();
        Optional<EmailToken> oneByEmailTypeAndUserId = emailTokenRepository.findOneByEmailTypeAndUserId(EmailType.CREATED_ACCOUNT, oneByEmail.get().getId());
        assertThat(oneByEmailTypeAndUserId.isPresent()).isFalse();
    }

    @Test
    public void createViaOauth_validDetailsTrialStatusIsSet_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO(Boolean.TRUE, UserType.OAUTH_FACEBOOK);

        //-----------------------------------------------------------------
        // Assert that trial status is set
        //-----------------------------------------------------------------
        assertThat(createdUserDTO.getUserSubscriptionStatus()).isEqualTo(UserSubscriptionStatus.TRIAL);
    }

    @Test
    public void createViaOauth_sameEmailTwice_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        createUserDTO(Boolean.TRUE, UserType.OAUTH_FACEBOOK);

        //-----------------------------------------------------------------
        // Try to create again the user with same email
        //-----------------------------------------------------------------
        exception.expect(UserException.class);
        createUserDTO(Boolean.TRUE, UserType.OAUTH_FACEBOOK);
    }

    @Test
    public void createViaOauth_sameEmailTwiceOneWithLowerCaseOneWithUppercase_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        createUserDTO(Boolean.TRUE, "a@a.a", UserType.OAUTH_FACEBOOK);

        //-----------------------------------------------------------------
        // Try to create again the user with same email but uppercase
        //-----------------------------------------------------------------
        exception.expect(UserException.class);
        createUserDTO(Boolean.TRUE, "A@A.A", UserType.OAUTH_FACEBOOK);
    }

    @Test
    public void createViaOauth_sameEmailTwiceSecondNotTrimmed_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        createUserDTO(Boolean.TRUE, "a@a.a", UserType.OAUTH_FACEBOOK);

        //-----------------------------------------------------------------
        // Try to create again the user with same email but uppercase
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        createUserDTO(Boolean.TRUE, " a@a.a    ", UserType.OAUTH_FACEBOOK);
    }

    @Test
    public void createViaOauth_invalidDetails_handledCorrectly() throws Exception {
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
