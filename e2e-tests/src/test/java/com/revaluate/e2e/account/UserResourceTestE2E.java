package com.revaluate.e2e.account;

import com.nimbusds.jose.JOSEException;
import com.revaluate.core.resource.Answer;
import com.revaluate.core.status.ExtraStatus;
import com.revaluate.domain.account.UpdatePasswordDTO;
import com.revaluate.domain.account.UpdatePasswordDTOBuilder;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.account.UserDTOBuilder;
import com.revaluate.e2e.AbstractResourceTestEndToEnd;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserResourceTestE2E extends AbstractResourceTestEndToEnd {

    //-----------------------------------------------------------------
    // Is email unique tests
    //-----------------------------------------------------------------

    @Test
    public void isUniqueEmail_emptyEmail_badResponseReceived() {
        WebTarget target = target("/account/isUniqueEmail");
        Response response = target.request().get();
        assertThat(response.getStatus(), is(ExtraStatus.UNPROCESSABLE_ENTITY.getStatusCode()));
    }

    @Test
    public void isUniqueEmail_invalidEmail_badResponseReceived() {
        WebTarget target = target("/account/isUniqueEmail");
        Response response = target.queryParam("email", "invalidEmail").request().get();
        assertThat(response.getStatus(), is(ExtraStatus.UNPROCESSABLE_ENTITY.getStatusCode()));
    }

    @Test
    public void isUniqueEmail_validEmail_okReceived() {
        WebTarget target = target("/account/isUniqueEmail");
        Response response = target.queryParam("email", "abcd@efgh.acsd").request().get();
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void isUniqueEmail_validEmailButNotUnique_badResponseReceived() {
        UserDTO createdUserDTO = createUserGetCreatedUserDTO();

        // check if is unique email
        WebTarget target = target("/account/isUniqueEmail");
        Response response = target.queryParam("email", createdUserDTO.getEmail()).request().get();
        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));

        Answer answer = response.readEntity(Answer.class);
        assertThat(answer, is(notNullValue()));

        // remove user
        removeUser(createdUserDTO.getId());
    }

    //-----------------------------------------------------------------
    // Create user tests
    //-----------------------------------------------------------------

    @Test
    public void create_invalidEmail_badResponseReceived() {
        UserDTO userDTO = new UserDTOBuilder().withEmail("xxx").build();

        WebTarget target = target("/account/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(userDTO, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(ExtraStatus.UNPROCESSABLE_ENTITY.getStatusCode()));

//        List<String> violationsMessageTemplates = getValidationMessageTemplates(response);
//        assertThat(violationsMessageTemplates.size(), is(2));
    }

    @Test
    public void create_invalidPassword_badResponseReceived() {
        UserDTO userDTO = new UserDTOBuilder().withEmail("a@b.c").withFirstName("aaaaaaa").withLastName("asdadsadsa").build();

        WebTarget target = target("/account/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(userDTO, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(ExtraStatus.UNPROCESSABLE_ENTITY.getStatusCode()));

//        List<String> violationsMessageTemplates = getValidationMessageTemplates(response);
//        assertThat(violationsMessageTemplates.size(), is(1));
    }

    @Test
    public void create_invalidFirstName_badResponseReceived() {
        UserDTO userDTO = new UserDTOBuilder().withEmail("a@b.c").withLastName("asdadsadsa").withPassword("aaaaaaaaaaa").build();

        WebTarget target = target("/account/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(userDTO, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(ExtraStatus.UNPROCESSABLE_ENTITY.getStatusCode()));

//        List<String> violationsMessageTemplates = getValidationMessageTemplates(response);
//        assertThat(violationsMessageTemplates.size(), is(1));
    }

    @Test
    public void create_invalidLastName_badResponseReceived() {
        UserDTO userDTO = new UserDTOBuilder().withEmail("a@b.c").withFirstName("asdadsadsa").withPassword("aaaaaaaaaaa").build();

        WebTarget target = target("/account/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(userDTO, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(ExtraStatus.UNPROCESSABLE_ENTITY.getStatusCode()));

//        List<String> violationsMessageTemplates = getValidationMessageTemplates(response);
//        assertThat(violationsMessageTemplates.size(), is(1));
    }

    @Test
    public void create_validDetails_okResponseReceived() {
        UserDTO createdUserDTO = createUserGetCreatedUserDTO();
        assertThat(createdUserDTO.getId(), notNullValue());

        removeUser(createdUserDTO.getId());
    }

    //-----------------------------------------------------------------
    // Remove user tests
    //-----------------------------------------------------------------

    @Test
    public void remove_nonAuthenticatedUser_unauthorizedResponseReceived() {
        Response response = target("/account/remove").request().delete();
        assertThat(Response.Status.UNAUTHORIZED.getStatusCode(), is(equalTo(response.getStatus())));
    }

    //-----------------------------------------------------------------
    // Update password tests
    //-----------------------------------------------------------------

    @Test
    public void updatePassword_withMissingTwoJsonProperties_badResponseReceived() throws ParseException, JOSEException {
        // First, create a valid user - and account
        UserDTO createdUserDTO = createUserGetCreatedUserDTO();
        assertThat(createdUserDTO.getId(), notNullValue());
        String tokenForUserWithId = jwtService.createTokenForUserWithId(createdUserDTO.getId());
        //-----------------------------------------------------------------
        //-----------------------------------------------------------------

        // Real test - check the update password
        Response response = target("/account/updatePassword").request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", "Bearer " + tokenForUserWithId).post(Entity.entity(new UpdatePasswordDTOBuilder().withNewPassword("x").build(), MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(ExtraStatus.UNPROCESSABLE_ENTITY.getStatusCode()));

//        List<String> violationsMessageTemplates = getValidationMessageTemplates(response);
//        assertThat(violationsMessageTemplates.size(), is(2));
        //-----------------------------------------------------------------
        //-----------------------------------------------------------------

        // Lastly, remove the user
        removeUser(createdUserDTO.getId());
    }

    @Test
    public void updatePassword_withUnProperOldPassword_badResponseReceived() throws ParseException, JOSEException {
        // First, create a valid user - and account
        UserDTO createdUserDTO = createUserGetCreatedUserDTO();
        assertThat(createdUserDTO.getId(), notNullValue());
        String tokenForUserWithId = jwtService.createTokenForUserWithId(createdUserDTO.getId());
        //-----------------------------------------------------------------
        //-----------------------------------------------------------------

        // Real test - check the update password
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withOldPassword("1234567X").withNewPassword("999999999").withNewPasswordConfirmation("999999999").build();
        Response response = target("/account/updatePassword")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Bearer " + tokenForUserWithId)
                .post(Entity.entity(updatePasswordDTO, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));

        Answer answer = response.readEntity(Answer.class);
        assertThat(answer, is(notNullValue()));
        //-----------------------------------------------------------------
        //-----------------------------------------------------------------

        // Lastly, remove the user
        removeUser(createdUserDTO.getId());
    }

    @Test
    public void updatePassword_withProperOldPasswordButNewPasswordNotMatchingConfirmationPassword_badResponseReceived() throws ParseException, JOSEException {
        // First, create a valid user - and account
        UserDTO createdUserDTO = createUserGetCreatedUserDTO();
        assertThat(createdUserDTO.getId(), notNullValue());
        String tokenForUserWithId = jwtService.createTokenForUserWithId(createdUserDTO.getId());
        //-----------------------------------------------------------------
        //-----------------------------------------------------------------

        // Real test - check the update password
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withOldPassword("1234567").withNewPassword("999999999").withNewPasswordConfirmation("999999999X").build();
        Response response = target("/account/updatePassword")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Bearer " + tokenForUserWithId)
                .post(Entity.entity(updatePasswordDTO, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));

        Answer answer = response.readEntity(Answer.class);
        assertThat(answer, is(notNullValue()));
        //-----------------------------------------------------------------
        //-----------------------------------------------------------------

        // Lastly, remove the user
        removeUser(createdUserDTO.getId());
    }

    @Test
    public void updatePassword_withProperDetails_okResponseReceived() throws ParseException, JOSEException {
        UserDTO createdUserDTO = createUserGetCreatedUserDTO();

        assertThat(createdUserDTO.getId(), notNullValue());
        String tokenForUserWithId = jwtService.createTokenForUserWithId(createdUserDTO.getId());
        //-----------------------------------------------------------------
        //-----------------------------------------------------------------

        // Real test - check the update password
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTOBuilder().withOldPassword("1234567").withNewPassword("999999999").withNewPasswordConfirmation("999999999").build();
        Response response = target("/account/updatePassword")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Bearer " + tokenForUserWithId)
                .post(Entity.entity(updatePasswordDTO, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));

        Answer answer = response.readEntity(Answer.class);
        assertThat(answer, is(notNullValue()));
        //-----------------------------------------------------------------
        //-----------------------------------------------------------------

        // Lastly, remove the user
        removeUser(createdUserDTO.getId());
    }

    //-----------------------------------------------------------------
    // Request reset password
    //-----------------------------------------------------------------
    @Test
    public void requestResetPassword_withProperDetails_okResponseReceived() throws ParseException, JOSEException {
        UserDTO createdUserDTO = createUserGetCreatedUserDTO();

        assertThat(createdUserDTO.getId(), notNullValue());
        //-----------------------------------------------------------------
        //-----------------------------------------------------------------

        // Real test - check the reset password
        Response response = target("/account/requestResetPassword/" + createdUserDTO.getEmail())
                .request().post(Entity.entity("", MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));

        Answer answer = response.readEntity(Answer.class);
        assertThat(answer, is(notNullValue()));
        //-----------------------------------------------------------------
        //-----------------------------------------------------------------

        // Lastly, remove the user
        removeUser(createdUserDTO.getId());
    }

    @Test
    public void requestResetPassword_withNonExistingEmail_okResponseReceived() throws ParseException, JOSEException {
        Response response = target("/account/requestResetPassword/" + "xxxx@xxxx.xxxx")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.entity("", MediaType.APPLICATION_JSON_TYPE));

        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));

        Answer answer = response.readEntity(Answer.class);
        assertThat(answer, is(notNullValue()));
    }
}