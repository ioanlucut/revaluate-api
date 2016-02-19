package com.revaluate.e2e.expense;

import com.google.gson.Gson;
import com.nimbusds.jose.JOSEException;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.e2e.AbstractResourceTestEndToEnd;
import org.hamcrest.core.Is;
import org.joda.time.LocalDateTime;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

public class ExpenseResourceTestE2E extends AbstractResourceTestEndToEnd {

    @Ignore("The spent date from entity is not really well marshalled")
    @Test
    public void create_validDetails_okResponseReceived() throws ParseException, JOSEException {
        UserDTO createdUserDTO = createUserGetCreatedUserDTO();
        String tokenForUserWithId = jwtService.createTokenForUserWithId(createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create valid expense
        //-----------------------------------------------------------------
        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        WebTarget target = target("/categories/create");
        Response response = target.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", "Bearer " + tokenForUserWithId).post(Entity.entity(categoryDTO, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus()).isEqualTo((Response.Status.OK.getStatusCode()));
        CategoryDTO createdCategoryDTO = response.readEntity(CategoryDTO.class);

        //-----------------------------------------------------------------
        // Create expense
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusYears(3)).build();
        target = target("/expenses/create");
        response = target.request(MediaType.APPLICATION_JSON_TYPE).header("Authorization", "Bearer " + tokenForUserWithId).post(Entity.entity(expenseDTO, MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus()).isEqualTo((Response.Status.OK.getStatusCode()));
        ExpenseDTO createdExpenseDTO = response.readEntity(ExpenseDTO.class);

        //-----------------------------------------------------------------
        // Assert created expense is ok
        //-----------------------------------------------------------------
        assertThat(createdExpenseDTO, Is.is(notNullValue()));
        assertThat(createdExpenseDTO.getId()).isNotEqualTo(0);
        assertThat(createdExpenseDTO.getCategory()).isEqualTo(expenseDTO.getCategory());
        assertThat(createdExpenseDTO.getDescription()).isEqualTo(expenseDTO.getDescription());
        assertThat(createdExpenseDTO.getValue()).isEqualTo(expenseDTO.getValue());
        assertThat(createdExpenseDTO.getSpentDate().getYear()).isEqualTo(expenseDTO.getSpentDate().getYear());

        removeUser(createdUserDTO.getId());
    }

    public static void main(String[] args) {
        System.out.println(new Gson().toJson(LocalDateTime.now().minusYears(3)));
    }
}
