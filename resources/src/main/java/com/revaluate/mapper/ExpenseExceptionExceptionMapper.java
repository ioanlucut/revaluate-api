package com.revaluate.mapper;

import com.revaluate.expense.exception.ExpenseException;
import com.revaluate.resource.utils.Responses;
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
