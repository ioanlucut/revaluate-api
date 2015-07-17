package com.revaluate.mapper;

import com.revaluate.insights.exception.InsightsException;
import com.revaluate.resource.utils.Responses;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class InsightExceptionExceptionMapper implements ExceptionMapper<InsightsException> {

    public Response toResponse(InsightsException ex) {

        return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
    }
}