package com.revaluate.resource.expense;

import com.revaluate.core.annotations.PaymentRequired;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpensesQueryResponseDTO;
import com.revaluate.expense.exception.ExpenseException;
import com.revaluate.expense.service.ExpenseService;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path(ExpenseResource.EXPENSES)
@Component
public class ExpenseResource extends Resource {

    //-----------------------------------------------------------------
    // Path
    //-----------------------------------------------------------------
    public static final String EXPENSES = "expenses";

    //-----------------------------------------------------------------
    // Sub paths
    //-----------------------------------------------------------------
    private static final String REMOVE_EXPENSE = "{expenseId}";
    private static final String RETRIEVE_EXPENSES = "retrieve";
    private static final String RETRIEVE_EXPENSES_GROUPED = "retrieve_grouped";
    private static final String RETRIEVE_EXPENSES_FROM_TO = "retrieve_from_to";
    private static final String RETRIEVE_EXPENSES_OF_CATEGORY_FROM_TO = "retrieve_from_to_of_category/{categoryId}";
    private static final String BULK_DELETE = "bulkDelete";

    //-----------------------------------------------------------------
    // Path params
    //-----------------------------------------------------------------
    private static final String EXPENSE_ID = "expenseId";
    private static final String CATEGORY_ID = "categoryId";
    public static final String FROM = "from";
    public static final String TO = "to";
    public static final String PAGE = "page";
    public static final String SIZE = "size";

    @Autowired
    private ExpenseService expenseService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @PaymentRequired
    public Response create(@Valid ExpenseDTO expenseDTO) throws ExpenseException {
        ExpenseDTO createdExpenseDTO = expenseService.create(expenseDTO, getCurrentUserId());

        return Responses.respond(Response.Status.OK, createdExpenseDTO);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @PaymentRequired
    public Response update(@Valid ExpenseDTO expenseDTO) throws ExpenseException {
        ExpenseDTO createdExpenseDTO = expenseService.update(expenseDTO, getCurrentUserId());

        return Responses.respond(Response.Status.OK, createdExpenseDTO);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path(REMOVE_EXPENSE)
    @PaymentRequired
    public Response remove(@PathParam(EXPENSE_ID) @NotNull int expenseId) throws ExpenseException {
        expenseService.remove(expenseId, getCurrentUserId());

        return Responses.respond(Response.Status.OK, "Expense successfully removed");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(RETRIEVE_EXPENSES)
    public Response retrieveAll() {
        List<ExpenseDTO> allExpensesFor = expenseService.findAllExpensesFor(getCurrentUserId(), Optional.empty());

        return Responses.respond(Response.Status.OK, allExpensesFor);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(RETRIEVE_EXPENSES_GROUPED)
    public Response retrieveAllGrouped(@QueryParam(PAGE) int page, @QueryParam(SIZE) int size) {
        ExpensesQueryResponseDTO expensesAfterBeforeAndGroupBySpentDate = expenseService.findExpensesGroupBySpentDate(getCurrentUserId(), new PageRequest(page, size));

        return Responses.respond(Response.Status.OK, expensesAfterBeforeAndGroupBySpentDate);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(RETRIEVE_EXPENSES_FROM_TO)
    public Response retrieveAllFromTo(@QueryParam(FROM) LocalDateTime from, @QueryParam(TO) LocalDateTime to) {
        List<ExpenseDTO> allExpensesFor = expenseService.findAllExpensesAfterBefore(getCurrentUserId(), from, to);

        return Responses.respond(Response.Status.OK, allExpensesFor);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(RETRIEVE_EXPENSES_OF_CATEGORY_FROM_TO)
    public Response retrieveAllOfCategoryFromTo(@PathParam(CATEGORY_ID) @NotNull int categoryId, @QueryParam(FROM) LocalDateTime from, @QueryParam(TO) LocalDateTime to) {
        List<ExpenseDTO> allExpensesOfThisCategory = expenseService.findAllExpensesWithCategoryIdAfterBefore(getCurrentUserId(), categoryId, from, to);

        return Responses.respond(Response.Status.OK, allExpensesOfThisCategory);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(BULK_DELETE)
    @PaymentRequired
    public Response bulkDelete(@NotNull @Valid List<ExpenseDTO> expenseDTOs) throws ExpenseException {
        expenseService.bulkDelete(expenseDTOs, getCurrentUserId());

        return Responses.respond(Response.Status.OK, "Bulk delete action complete");
    }

}