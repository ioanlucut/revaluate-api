package com.revaluate.account.resource;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

public class UserRepositoryTestEndToEnd extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(UserResource.class);
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
}