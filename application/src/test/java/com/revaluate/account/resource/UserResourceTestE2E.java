package com.revaluate.account.resource;

import com.nimbusds.jose.JOSEException;
import com.revaluate.account.domain.UpdatePasswordDTO;
import com.revaluate.account.domain.UpdatePasswordDTOBuilder;
import com.revaluate.account.domain.UserDTO;
import com.revaluate.account.domain.UserDTOBuilder;
import com.revaluate.core.resource.Answer;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

public class UserResourceTestE2E extends AbstractResourceTest {

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
        UserDTO userDTO = new UserDTOBuilder().withEmail("a@b." + RandomStringUtils.randomAlphanumeric(5)).withFirstName("fn").withLastName("ln").withPassword("1234567").build();

        WebTarget target = target("/account/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(userDTO, MediaType.APPLICATION_JSON_TYPE));
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.OK.getStatusCode()));

        UserDTO createdUserDTO = response.readEntity(UserDTO.class);
        MatcherAssert.assertThat(createdUserDTO.getId(), Matchers.notNullValue());

        // check if is unique email
        target = target("/account/isUniqueEmail");
        response = target.queryParam("email", userDTO.getEmail()).request().get();
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.BAD_REQUEST.getStatusCode()));

        Answer answer = response.readEntity(Answer.class);
        MatcherAssert.assertThat(answer, Matchers.is(Matchers.notNullValue()));

        // remove user
        removeUser(createdUserDTO.getId());
    }

    @Test
    public void createUserWithInvalidEmail() {
        UserDTO userDTO = new UserDTOBuilder().withEmail("xxx").build();

        WebTarget target = target("/account/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(userDTO, MediaType.APPLICATION_JSON_TYPE));
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.BAD_REQUEST.getStatusCode()));

        Set<String> violationsMessageTemplates = getValidationMessageTemplates(response);
        assertEquals(2, violationsMessageTemplates.size());
    }

    @Test
    public void createUserWithInvalidPassword() {
        UserDTO userDTO = new UserDTOBuilder().withEmail("a@b.c").withFirstName("aaaaaaa").withLastName("asdadsadsa").build();

        WebTarget target = target("/account/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(userDTO, MediaType.APPLICATION_JSON_TYPE));
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.BAD_REQUEST.getStatusCode()));

        Set<String> violationsMessageTemplates = getValidationMessageTemplates(response);
        assertEquals(1, violationsMessageTemplates.size());
    }

    @Test
    public void createUserWithInvalidFirstName() {
        UserDTO userDTO = new UserDTOBuilder().withEmail("a@b.c").withLastName("asdadsadsa").withPassword("aaaaaaaaaaa").build();

        WebTarget target = target("/account/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(userDTO, MediaType.APPLICATION_JSON_TYPE));
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.BAD_REQUEST.getStatusCode()));

        Set<String> violationsMessageTemplates = getValidationMessageTemplates(response);
        assertEquals(1, violationsMessageTemplates.size());
    }

    @Test
    public void createUserWithInvalidLastName() {
        UserDTO userDTO = new UserDTOBuilder().withEmail("a@b.c").withFirstName("asdadsadsa").withPassword("aaaaaaaaaaa").build();

        WebTarget target = target("/account/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(userDTO, MediaType.APPLICATION_JSON_TYPE));
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.BAD_REQUEST.getStatusCode()));

        Set<String> violationsMessageTemplates = getValidationMessageTemplates(response);
        assertEquals(1, violationsMessageTemplates.size());
    }

    @Test
    public void createUserWithValidDetails() {
        UserDTO userDTO = new UserDTOBuilder().withEmail("a@b." + RandomStringUtils.randomAlphanumeric(5)).withFirstName("fn").withLastName("ln").withPassword("1234567").build();

        WebTarget target = target("/account/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(userDTO, MediaType.APPLICATION_JSON_TYPE));
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.OK.getStatusCode()));

        UserDTO createdUserDTO = response.readEntity(UserDTO.class);
        MatcherAssert.assertThat(createdUserDTO.getId(), Matchers.notNullValue());

        removeUser(createdUserDTO.getId());
    }

    @Test
    public void removeNonAuthenticatedUser() {
        Response response = target("/account/remove").request().delete();
        assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), response.getStatus());
    }

    @Test
    public void removeNonExistingUserWithValidToken() {
        int id = 999999;
        try {
            String tokenForUserWithId = jwtService.createTokenForUserWithId(id);
            Response delete = target("/account/remove").request().header("Authorization", "Bearer " + tokenForUserWithId).delete();
            assertEquals(Response.Status.UNAUTHORIZED.getStatusCode(), delete.getStatus());
        } catch (JOSEException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void removeUser(int id) {
        try {
            String tokenForUserWithId = jwtService.createTokenForUserWithId(id);
            Response delete = target("/account/remove").request().header("Authorization", "Bearer " + tokenForUserWithId).delete();
            assertEquals(Response.Status.OK.getStatusCode(), delete.getStatus());
        } catch (JOSEException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updatePasswordWithMissingTwoJsonProperties() throws ParseException, JOSEException {
        // First, create a valid user - and account
        UserDTO userDTO = new UserDTOBuilder().withEmail("a@b." + RandomStringUtils.randomAlphanumeric(5)).withFirstName("fn").withLastName("ln").withPassword("1234567").build();

        WebTarget target = target("/account/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(userDTO, MediaType.APPLICATION_JSON_TYPE));
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.OK.getStatusCode()));

        UserDTO createdUserDTO = response.readEntity(UserDTO.class);
        MatcherAssert.assertThat(createdUserDTO.getId(), Matchers.notNullValue());
        String tokenForUserWithId = jwtService.createTokenForUserWithId(createdUserDTO.getId());
        //-----------------------------------------------------------------
        //-----------------------------------------------------------------


        // Real test - check the update password
        response = target("/account/updatePassword").request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", "Bearer " + tokenForUserWithId).post(Entity.entity(new UpdatePasswordDTOBuilder().withNewPassword("x").build(), MediaType.APPLICATION_JSON_TYPE));
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.BAD_REQUEST.getStatusCode()));

        Set<String> violationsMessageTemplates = getValidationMessageTemplates(response);
        assertEquals(2, violationsMessageTemplates.size());
        //-----------------------------------------------------------------
        //-----------------------------------------------------------------

        // Lastly, remove the user
        removeUser(createdUserDTO.getId());
    }

    @Test
    public void updatePasswordWithUnProperOldPassword() throws ParseException, JOSEException {
        // First, create a valid user - and account
        UserDTO userDTO = new UserDTOBuilder().withEmail("a@b." + RandomStringUtils.randomAlphanumeric(5)).withFirstName("fn").withLastName("ln").withPassword("1234567").build();

        WebTarget target = target("/account/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(userDTO, MediaType.APPLICATION_JSON_TYPE));
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.OK.getStatusCode()));

        UserDTO createdUserDTO = response.readEntity(UserDTO.class);
        MatcherAssert.assertThat(createdUserDTO.getId(), Matchers.notNullValue());
        String tokenForUserWithId = jwtService.createTokenForUserWithId(createdUserDTO.getId());
        //-----------------------------------------------------------------
        //-----------------------------------------------------------------

        // Real test - check the update password
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withOldPassword("1234567X").withNewPassword("999999999").withNewPasswordConfirmation("999999999").build();
        response = target("/account/updatePassword")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Bearer " + tokenForUserWithId)
                .post(Entity.entity(updatePasswordDTO, MediaType.APPLICATION_JSON_TYPE));
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.BAD_REQUEST.getStatusCode()));

        Answer answer = response.readEntity(Answer.class);
        MatcherAssert.assertThat(answer, Matchers.is(Matchers.notNullValue()));
        //-----------------------------------------------------------------
        //-----------------------------------------------------------------

        // Lastly, remove the user
        removeUser(createdUserDTO.getId());
    }

    @Test
    public void updatePasswordWithProperOldPasswordButNewPasswordNotMatchingConfirmationPassword() throws ParseException, JOSEException {
        // First, create a valid user - and account
        UserDTO userDTO = new UserDTOBuilder().withEmail("a@b." + RandomStringUtils.randomAlphanumeric(5)).withFirstName("fn").withLastName("ln").withPassword("1234567").build();

        WebTarget target = target("/account/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(userDTO, MediaType.APPLICATION_JSON_TYPE));
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.OK.getStatusCode()));

        UserDTO createdUserDTO = response.readEntity(UserDTO.class);
        MatcherAssert.assertThat(createdUserDTO.getId(), Matchers.notNullValue());
        String tokenForUserWithId = jwtService.createTokenForUserWithId(createdUserDTO.getId());
        //-----------------------------------------------------------------
        //-----------------------------------------------------------------

        // Real test - check the update password
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withOldPassword("1234567").withNewPassword("999999999").withNewPasswordConfirmation("999999999X").build();
        response = target("/account/updatePassword")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Bearer " + tokenForUserWithId)
                .post(Entity.entity(updatePasswordDTO, MediaType.APPLICATION_JSON_TYPE));
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.BAD_REQUEST.getStatusCode()));

        Answer answer = response.readEntity(Answer.class);
        MatcherAssert.assertThat(answer, Matchers.is(Matchers.notNullValue()));
        //-----------------------------------------------------------------
        //-----------------------------------------------------------------

        // Lastly, remove the user
        removeUser(createdUserDTO.getId());
    }

    @Test
    public void updatePasswordWorks() throws ParseException, JOSEException {
        // First, create a valid user - and account
        UserDTO userDTO = new UserDTOBuilder().withEmail("a@b." + RandomStringUtils.randomAlphanumeric(5)).withFirstName("fn").withLastName("ln").withPassword("1234567").build();

        WebTarget target = target("/account/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(userDTO, MediaType.APPLICATION_JSON_TYPE));
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.OK.getStatusCode()));

        UserDTO createdUserDTO = response.readEntity(UserDTO.class);
        MatcherAssert.assertThat(createdUserDTO.getId(), Matchers.notNullValue());
        String tokenForUserWithId = jwtService.createTokenForUserWithId(createdUserDTO.getId());
        //-----------------------------------------------------------------
        //-----------------------------------------------------------------

        // Real test - check the update password
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withOldPassword("1234567").withNewPassword("999999999").withNewPasswordConfirmation("999999999").build();
        response = target("/account/updatePassword")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Bearer " + tokenForUserWithId)
                .post(Entity.entity(updatePasswordDTO, MediaType.APPLICATION_JSON_TYPE));
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.OK.getStatusCode()));

        Answer answer = response.readEntity(Answer.class);
        MatcherAssert.assertThat(answer, Matchers.is(Matchers.notNullValue()));
        //-----------------------------------------------------------------
        //-----------------------------------------------------------------

        // Lastly, remove the user
        removeUser(createdUserDTO.getId());
    }

    @Test
    public void requestSignUpRegistrationWorks() throws ParseException, JOSEException {
        Response response = target("/account/signUp")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity("a@b.c", MediaType.APPLICATION_JSON_TYPE));
        MatcherAssert.assertThat(response.getStatus(), Matchers.is(Response.Status.OK.getStatusCode()));

        Answer answer = response.readEntity(Answer.class);
        MatcherAssert.assertThat(answer, Matchers.is(Matchers.notNullValue()));
    }
}