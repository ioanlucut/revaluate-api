package com.revaluate.expense.resource;

import com.revaluate.core.resource.Resource;
import com.revaluate.core.resource.Responses;
import com.revaluate.expense.domain.ExpenseDTO;
import com.revaluate.expense.exception.ExpenseException;
import com.revaluate.expense.service.ExpenseService;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

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
    private static final String RETRIEVE_EXPENSES = "retrieve";
    private static final String RETRIEVE_EXPENSES_FROM_TO = "retrieve_from_to";
    private static final String REMOVE_EXPENSE = "remove/{expenseId}";

    //-----------------------------------------------------------------
    // Path params
    //-----------------------------------------------------------------
    private static final String EXPENSE_ID = "expenseId";
    public static final String FROM = "from";
    public static final String TO = "to";

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(RETRIEVE_EXPENSES)
    public Response retrieveAll() {
        List<ExpenseDTO> allExpensesFor = expenseService.findAllExpensesFor(getCurrentUserId());

        return Responses.respond(Response.Status.OK, allExpensesFor);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(RETRIEVE_EXPENSES_FROM_TO)
    public Response retrieveAllFromTo(@QueryParam(FROM) LocalDateTime from, @QueryParam(TO) LocalDateTime to) {
        List<ExpenseDTO> allExpensesFor = expenseService.findAllExpensesAfterBefore(getCurrentUserId(), from, to);

        return Responses.respond(Response.Status.OK, allExpensesFor);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path(REMOVE_EXPENSE)
    public Response remove(@PathParam(EXPENSE_ID) @NotNull int expenseId) throws ExpenseException {
        expenseService.remove(expenseId, getCurrentUserId());

        return Responses.respond(Response.Status.OK, "Expense successfully removed");
    }
}