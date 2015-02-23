package com.revaluate.account.resource;

import com.nimbusds.jose.JOSEException;
import com.revaluate.account.domain.UserDTO;
import com.revaluate.account.domain.UserDTOBuilder;
import com.revaluate.core.bootstrap.ApplicationConfig;
import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.core.jwt.JwtService;
import org.apache.commons.lang3.RandomStringUtils;
import org.glassfish.jersey.server.validation.ValidationError;
import org.glassfish.jersey.test.JerseyTest;
import org.mockito.Mockito;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

// [UnitOfWork_StateUnderTest_ExpectedBehavior]
// public void Sum_NegativeNumberAs1stParam_ExceptionThrown()
public class AbstractResourceTestEndToEnd extends JerseyTest {

    protected static final String JWT_ISSUER_TEST = "http://revaluate.io";
    protected static final String JWT_SHARED_TEST = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    protected static final String BEARER_TEST = "Bearer";
    protected JwtService jwtService = getMockedJwtService();

    protected List<ValidationError> getValidationErrorList(final Response response) {
        return response.readEntity(new GenericType<List<ValidationError>>() {
        });
    }

    protected Set<String> getValidationMessageTemplates(final Response response) {
        return getValidationMessageTemplates(getValidationErrorList(response));
    }

    protected Set<String> getValidationMessageTemplates(final List<ValidationError> errors) {
        return errors.stream().map(ValidationError::getMessageTemplate).collect(Collectors.toSet());
    }

    @Override
    protected Application configure() {
        return new ApplicationConfig();
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
        UserDTO userDTO = new UserDTOBuilder().withEmail("a@b." + RandomStringUtils.randomAlphanumeric(5)).withFirstName("fn").withLastName("ln").withPassword("1234567").build();

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