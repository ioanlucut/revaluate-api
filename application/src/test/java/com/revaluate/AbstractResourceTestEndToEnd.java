package com.revaluate;

import com.google.common.collect.ImmutableList;
import com.nimbusds.jose.JOSEException;
import com.revaluate.account.domain.UserDTO;
import com.revaluate.account.domain.UserDTOBuilder;
import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.core.jwt.JwtService;
import com.revaluate.currency.domain.CurrencyDTO;
import com.revaluate.currency.domain.CurrencyDTOBuilder;
import io.dropwizard.jersey.validation.ValidationErrorMessage;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import io.github.fallwizard.configuration.FallwizardConfiguration;
import org.apache.commons.lang3.RandomStringUtils;
import org.joda.money.CurrencyUnit;
import org.junit.ClassRule;
import org.mockito.Mockito;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

// [UnitOfWork_StateUnderTest_ExpectedBehavior]
// public void Sum_NegativeNumberAs1stParam_ExceptionThrown()
public class AbstractResourceTestEndToEnd {

    protected static final String JWT_ISSUER_TEST = "http://revaluate.io";
    protected static final String JWT_SHARED_TEST = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    protected static final String BEARER_TEST = "Bearer";
    protected JwtService jwtService = getMockedJwtService();

    static {
        System.setProperty(ConfigProperties.SPRING_PROFILE_ACTIVE, "IT");
    }

    @ClassRule
    public static final DropwizardAppRule<FallwizardConfiguration> RULE =
            new DropwizardAppRule<FallwizardConfiguration>(RevaluateApplication.class, ResourceHelpers.resourceFilePath("config.yaml"));

    protected Client client = ClientBuilder.newClient();

    protected WebTarget target(String path) {
        return client.target(String.format("http://localhost:%d", RULE.getLocalPort())).path(path);
    }

    protected List<ValidationErrorMessage> getValidationErrorMessageList(final Response response) {
        return response.readEntity(new GenericType<List<ValidationErrorMessage>>() {
        });
    }

    protected List<String> getValidationMessageTemplates(final Response response) {
        return getValidationMessageTemplates(getValidationErrorMessageList(response));
    }

    protected List<String> getValidationMessageTemplates(final List<ValidationErrorMessage> errors) {
        List<String> allErrors = new ArrayList<>();
        for (ValidationErrorMessage validationErrorMessage : errors) {
            ImmutableList<String> strings = validationErrorMessage.getErrors();
            allErrors.addAll(strings);
        }

        return allErrors;
    }

    protected JwtService getMockedJwtService() {
        ConfigProperties configProperties = Mockito.mock(ConfigProperties.class);
        Mockito.when(configProperties.getIssuer()).thenReturn(JWT_ISSUER_TEST);
        Mockito.when(configProperties.getShared()).thenReturn(JWT_SHARED_TEST);
        Mockito.when(configProperties.getBearerHeaderKey()).thenReturn(BEARER_TEST);
        JwtService jwtService = Mockito.spy(new JwtService());
        jwtService.setConfigProperties(configProperties);
        return jwtService;
    }

    //-----------------------------------------------------------------
    // Common methods used
    //-----------------------------------------------------------------

    protected UserDTO createUserGetCreatedUserDTO() {
        // First, create a valid user - and account
        CurrencyDTO currencyDTO = new CurrencyDTOBuilder().withCurrencyCode(CurrencyUnit.EUR.getCurrencyCode()).build();
        UserDTO userDTO = new UserDTOBuilder().withEmail("a@b." + RandomStringUtils.randomAlphanumeric(5)).withFirstName("fn").withLastName("ln").withPassword("1234567").withCurrency(currencyDTO).build();

        WebTarget target = target("/account/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(userDTO, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));

        return response.readEntity(UserDTO.class);
    }

    protected void removeUser(int id) {
        try {
            String tokenForUserWithId = jwtService.createTokenForUserWithId(id);
            Response response = target("/account/remove").request().header("Authorization", "Bearer " + tokenForUserWithId).delete();
            assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
        } catch (JOSEException | ParseException e) {
            e.printStackTrace();
        }
    }
}