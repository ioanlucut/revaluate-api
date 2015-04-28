package com.revaluate.mapper;

import com.revaluate.insights.exception.InsightException;
import com.revaluate.resource.utils.Responses;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class InsightExceptionExceptionMapper implements ExceptionMapper<InsightException> {

    public Response toResponse(InsightException ex) {

        return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
    }
}