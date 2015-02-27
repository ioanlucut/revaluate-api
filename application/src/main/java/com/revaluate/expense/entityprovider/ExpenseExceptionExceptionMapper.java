package com.revaluate.expense.entityprovider;

import com.revaluate.core.resource.Responses;
import com.revaluate.expense.exception.ExpenseException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class ExpenseExceptionExceptionMapper implements ExceptionMapper<ExpenseException> {

    public Response toResponse(ExpenseException ex) {

        return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
    }
}
