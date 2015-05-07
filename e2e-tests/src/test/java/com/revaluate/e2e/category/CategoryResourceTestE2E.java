package com.revaluate.e2e.category;

import com.nimbusds.jose.JOSEException;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.e2e.AbstractResourceTestEndToEnd;
import com.revaluate.resource.utils.Answer;
import com.revaluate.resource.utils.ExtraStatus;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class CategoryResourceTestE2E extends AbstractResourceTestEndToEnd {

    //-----------------------------------------------------------------
    // Is category unique tests
    //-----------------------------------------------------------------

    @Test
    public void isUniqueCategory_emptyEmail_badResponseReceived() throws ParseException, JOSEException {
        UserDTO createdUserDTO = createUserGetCreatedUserDTO();

        String tokenForUserWithId = jwtService.createTokenForUserWithId(createdUserDTO.getId());
        WebTarget target = target("/categories/isUniqueCategory");
        Response response = target.request().header("Authorization", "Bearer " + tokenForUserWithId).get();

        assertThat(response.getStatus(), is(ExtraStatus.UNPROCESSABLE_ENTITY.getStatusCode()));
    }

    @Test
    public void isUniqueCategory_tooShortName_badResponseReceived() throws ParseException, JOSEException {
        UserDTO createdUserDTO = createUserGetCreatedUserDTO();
        String tokenForUserWithId = jwtService.createTokenForUserWithId(createdUserDTO.getId());

        WebTarget target = target("/categories/isUniqueCategory");
        Response response = target.queryParam("name", "1").request().header("Authorization", "Bearer " + tokenForUserWithId).get();
        assertThat(response.getStatus(), is(ExtraStatus.UNPROCESSABLE_ENTITY.getStatusCode()));
    }

    @Test
    public void isUniqueCategory_validName_okReceived() throws ParseException, JOSEException {
        UserDTO createdUserDTO = createUserGetCreatedUserDTO();
        String tokenForUserWithId = jwtService.createTokenForUserWithId(createdUserDTO.getId());

        WebTarget target = target("/categories/isUniqueCategory");
        Response response = target.queryParam("name", "abcd").request().header("Authorization", "Bearer " + tokenForUserWithId).get();
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));
    }

    @Test
    public void isUniqueCategory_validNameButNotUnique_badResponseReceived() throws ParseException, JOSEException {
        UserDTO createdUserDTO = createUserGetCreatedUserDTO();
        String tokenForUserWithId = jwtService.createTokenForUserWithId(createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create first category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withName(RandomStringUtils.randomAlphanumeric(5)).withColor(FIRST_VALID_COLOR).build();
        WebTarget target = target("/categories");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", "Bearer " + tokenForUserWithId).post(Entity.entity(categoryDTO, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));

        CategoryDTO createdCategoryDTO = response.readEntity(CategoryDTO.class);
        assertThat(createdCategoryDTO.getId(), notNullValue());

        //-----------------------------------------------------------------
        // Check if the same name is ok
        //-----------------------------------------------------------------
        target = target("/categories/isUniqueCategory");
        response = target.queryParam("name", categoryDTO.getName()).request().header("Authorization", "Bearer " + tokenForUserWithId).get();
        assertThat(response.getStatus(), is(Response.Status.BAD_REQUEST.getStatusCode()));

        Answer answer = response.readEntity(Answer.class);
        assertThat(answer, is(notNullValue()));

        // remove user
        removeUser(createdUserDTO.getId());
    }

    //-----------------------------------------------------------------
    // Create categories tests
    //-----------------------------------------------------------------

    @Test
    public void create_withInvalidCategoryName_badResponseReceived() throws ParseException, JOSEException {
        UserDTO createdUserDTO = createUserGetCreatedUserDTO();
        String tokenForUserWithId = jwtService.createTokenForUserWithId(createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create invalid category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withName(RandomStringUtils.randomAlphanumeric(1)).withColor(FIRST_VALID_COLOR).build();
        WebTarget target = target("/categories");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", "Bearer " + tokenForUserWithId).post(Entity.entity(categoryDTO, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(ExtraStatus.UNPROCESSABLE_ENTITY.getStatusCode()));
//        List<String> violationsMessageTemplates = getValidationMessageTemplates(response);
//        assertThat(violationsMessageTemplates.size(), is(1));
    }

    @Test
    public void create_withInvalidCategoryColor_badResponseReceived() throws ParseException, JOSEException {
        UserDTO createdUserDTO = createUserGetCreatedUserDTO();
        String tokenForUserWithId = jwtService.createTokenForUserWithId(createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create invalid category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withName(RandomStringUtils.randomAlphanumeric(4)).withColor(INVALID_COLOR).build();
        WebTarget target = target("/categories");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", "Bearer " + tokenForUserWithId).post(Entity.entity(categoryDTO, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(ExtraStatus.UNPROCESSABLE_ENTITY.getStatusCode()));
//        List<String> violationsMessageTemplates = getValidationMessageTemplates(response);
//        assertThat(violationsMessageTemplates.size(), is(1));
    }

    @Test
    public void create_withInvalidCategory_badResponseReceived() throws ParseException, JOSEException {
        UserDTO createdUserDTO = createUserGetCreatedUserDTO();
        String tokenForUserWithId = jwtService.createTokenForUserWithId(createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create invalid category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().build();
        WebTarget target = target("/categories");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", "Bearer " + tokenForUserWithId).post(Entity.entity(categoryDTO, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(ExtraStatus.UNPROCESSABLE_ENTITY.getStatusCode()));
//        List<String> violationsMessageTemplates = getValidationMessageTemplates(response);
//        assertThat(violationsMessageTemplates.size(), is(1));
    }

    @Test
    public void create_validDetails_okResponseReceived() throws ParseException, JOSEException {
        UserDTO createdUserDTO = createUserGetCreatedUserDTO();
        String tokenForUserWithId = jwtService.createTokenForUserWithId(createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create valid category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("name").build();
        WebTarget target = target("/categories");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", "Bearer " + tokenForUserWithId).post(Entity.entity(categoryDTO, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus(), is(Response.Status.OK.getStatusCode()));

        removeUser(createdUserDTO.getId());
    }
}
