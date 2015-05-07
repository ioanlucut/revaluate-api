package com.revaluate.mapper;

import com.revaluate.currency.exception.CurrencyException;
import com.revaluate.resource.utils.Responses;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class CurrencyExceptionExceptionMapper implements ExceptionMapper<CurrencyException> {

    public Response toResponse(CurrencyException ex) {

        return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
    }
}
