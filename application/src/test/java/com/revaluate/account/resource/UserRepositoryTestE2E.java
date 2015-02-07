package com.revaluate.account.resource;

import com.revaluate.account.domain.UserDomain;
import com.revaluate.account.domain.UserDomainBuilder;
import com.revaluate.core.ApplicationConfig;
import org.glassfish.jersey.server.validation.ValidationError;
import org.glassfish.jersey.test.JerseyTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
        UserDomain userDomain = new UserDomainBuilder().withEmail("a@b.c").withFirstName("fn").withLastName("ln").withPassword("1234567").build();

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
        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    private void removeUser(long id) {
        Response delete = target("/account/remove/" + id).request().delete();
        assertEquals(Response.Status.OK.getStatusCode(), delete.getStatus());
    }
}