package com.revaluate.mapper;

import com.revaluate.payment.exception.PaymentStatusException;
import com.revaluate.resource.utils.Responses;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class PaymentStatusExceptionExceptionMapper implements ExceptionMapper<PaymentStatusException> {

    public Response toResponse(PaymentStatusException ex) {

        return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
    }
}
