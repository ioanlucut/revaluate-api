package com.revaluate.expense.resource;

import com.revaluate.core.resource.Resource;
import com.revaluate.core.resource.Responses;
import com.revaluate.expense.domain.ExpenseDTO;
import com.revaluate.expense.exception.ExpenseException;
import com.revaluate.expense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(ExpenseResource.CATEGORIES)
@Component
public class ExpenseResource extends Resource {

    //-----------------------------------------------------------------
    // Path
    //-----------------------------------------------------------------
    public static final String CATEGORIES = "expenses";

    //-----------------------------------------------------------------
    // Sub paths
    //-----------------------------------------------------------------
    private static final String CREATE_EXPENSE = "create";
    private static final String UPDATE_EXPENSE = "update";
    private static final String REMOVE_EXPENSE = "remove/{expenseId}";

    //-----------------------------------------------------------------
    // Path params
    //-----------------------------------------------------------------
    private static final String EXPENSE_ID = "expenseId";

    @Autowired
    private ExpenseService expenseService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(CREATE_EXPENSE)
    public Response create(@Valid ExpenseDTO expenseDTO) throws ExpenseException {
        ExpenseDTO createdExpenseDTO = expenseService.create(expenseDTO, getCurrentUserId());

        return Responses.respond(Response.Status.OK, createdExpenseDTO);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(UPDATE_EXPENSE)
    public Response update(@Valid ExpenseDTO expenseDTO) throws ExpenseException {
        ExpenseDTO createdExpenseDTO = expenseService.update(expenseDTO, getCurrentUserId());

        return Responses.respond(Response.Status.OK, createdExpenseDTO);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path(REMOVE_EXPENSE)
    public Response remove(@PathParam(EXPENSE_ID) @NotNull int expenseId) throws ExpenseException {
        expenseService.remove(expenseId, getCurrentUserId());

        return Responses.respond(Response.Status.OK, "Expense successfully removed");
    }
}