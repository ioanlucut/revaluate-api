package com.revaluate.account.resource;

import com.nimbusds.jose.JOSEException;
import com.revaluate.account.domain.UserDomain;
import com.revaluate.account.domain.UserDomainBuilder;
import com.revaluate.core.Answer;
import com.revaluate.core.ApplicationConfig;
import com.revaluate.core.ConfigProperties;
import com.revaluate.core.jwt.JwtService;
import org.apache.commons.lang3.RandomStringUtils;
import org.glassfish.jersey.server.validation.ValidationError;
import org.glassfish.jersey.test.JerseyTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
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

import static junit.framework.Assert.assertEquals;

public class UserRepositoryTestE2E extends JerseyTest {

    private List<ValidationError> getValidationErrorList(final Response response) {
        return response.readEntity(new GenericType<List<ValidationError>>() {
        });
    }

    private Set<String> getValidationMessageTemplates(final Response response) {
        return getValidationMessageTemplates(getValidationErrorList(response));
    }

    private Set<String> getValidationMessageTemplates(final List<ValidationError> errors) {
        return errors.stream().map(ValidationError::getMessageTemplate).collect(Collectors.toSet());
    }

    @Override
    protected Application configure() {
        return new ApplicationConfig();
    }

    @Test
    public void emptyEmail() {
        WebTarget target = target("/account/isUniqueEmail");
        Response response = target.request().get();
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.BAD_REQUEST.getStatusCode()));
    }

    @Test
    public void invalidEmail() {
        WebTarget target = target("/account/isUniqueEmail");
        Response response = target.queryParam("email", "invalidEmail").request().get();
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.BAD_REQUEST.getStatusCode()));
    }

    @Test
    public void validEmail() {
        WebTarget target = target("/account/isUniqueEmail");
        Response response = target.queryParam("email", "abcd@efgh.acsd").request().get();
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void validEmailButNotUnique() {
        WebTarget target = target("/account/isUniqueEmail");
        Response response = target.queryParam("email", "a@b.c").request().get();
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.BAD_REQUEST.getStatusCode()));

        Answer answer = response.readEntity(Answer.class);
        MatcherAssert.assertThat(answer, Matchers.is(Matchers.notNullValue()));
    }

    @Test
    public void createUserWithInvalidEmail() {
        UserDomain userDomain = new UserDomainBuilder().withEmail("xxx").build();

        WebTarget target = target("/account/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(userDomain, MediaType.APPLICATION_JSON_TYPE));
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.BAD_REQUEST.getStatusCode()));

        Set<String> violationsMessageTemplates = getValidationMessageTemplates(response);
        assertEquals(2, violationsMessageTemplates.size());
    }

    @Test
    public void createUserWithInvalidPassword() {
        UserDomain userDomain = new UserDomainBuilder().withEmail("a@b.c").withFirstName("aaaaaaa").withLastName("asdadsadsa").build();

        WebTarget target = target("/account/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(userDomain, MediaType.APPLICATION_JSON_TYPE));
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.BAD_REQUEST.getStatusCode()));

        Set<String> violationsMessageTemplates = getValidationMessageTemplates(response);
        assertEquals(1, violationsMessageTemplates.size());
    }

    @Test
    public void createUserWithInvalidFirstName() {
        UserDomain userDomain = new UserDomainBuilder().withEmail("a@b.c").withLastName("asdadsadsa").withPassword("aaaaaaaaaaa").build();

        WebTarget target = target("/account/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(userDomain, MediaType.APPLICATION_JSON_TYPE));
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.BAD_REQUEST.getStatusCode()));

        Set<String> violationsMessageTemplates = getValidationMessageTemplates(response);
        assertEquals(1, violationsMessageTemplates.size());
    }

    @Test
    public void createUserWithInvalidLastName() {
        UserDomain userDomain = new UserDomainBuilder().withEmail("a@b.c").withFirstName("asdadsadsa").withPassword("aaaaaaaaaaa").build();

        WebTarget target = target("/account/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(userDomain, MediaType.APPLICATION_JSON_TYPE));
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.BAD_REQUEST.getStatusCode()));

        Set<String> violationsMessageTemplates = getValidationMessageTemplates(response);
        assertEquals(1, violationsMessageTemplates.size());
    }

    @Test
    public void createUserWithValidDetails() {
        UserDomain userDomain = new UserDomainBuilder().withEmail("a@b." + RandomStringUtils.randomAlphanumeric(5)).withFirstName("fn").withLastName("ln").withPassword("1234567").build();

        WebTarget target = target("/account/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(userDomain, MediaType.APPLICATION_JSON_TYPE));
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.OK.getStatusCode()));

        UserDomain createdUserDomain = response.readEntity(UserDomain.class);
        MatcherAssert.assertThat(createdUserDomain.getId(), Matchers.notNullValue());

        removeUser(createdUserDomain.getId());
    }

    @Test
    public void removeNonExistingUser() {
        Response response = target("/account/remove/9999999").request().delete();
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test
    public void removeNonExistingUserWithValidToken() {
        int id = 999999;
        ConfigProperties configProperties = Mockito.mock(ConfigProperties.class);
        Mockito.when(configProperties.getIssuer()).thenReturn("http://revaluate.io");
        Mockito.when(configProperties.getShared()).thenReturn("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        JwtService jwtService = Mockito.spy(new JwtService());
        jwtService.setConfigProperties(configProperties);
        try {
            String tokenForUserWithId = jwtService.createTokenForUserWithId(id);
            Response delete = target("/account/remove/" + id).request().header("Authorization", "Bearer " + tokenForUserWithId).delete();
            assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), delete.getStatus());
        } catch (JOSEException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void removeUser(int id) {
        ConfigProperties configProperties = Mockito.mock(ConfigProperties.class);
        Mockito.when(configProperties.getIssuer()).thenReturn("http://revaluate.io");
        Mockito.when(configProperties.getShared()).thenReturn("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
        JwtService jwtService = Mockito.spy(new JwtService());
        jwtService.setConfigProperties(configProperties);

        try {
            String tokenForUserWithId = jwtService.createTokenForUserWithId(id);
            Response delete = target("/account/remove/" + id).request().header("Authorization", "Bearer " + tokenForUserWithId).delete();
            assertEquals(Response.Status.OK.getStatusCode(), delete.getStatus());
        } catch (JOSEException | ParseException e) {
            e.printStackTrace();
        }
    }
}