package com.revaluate.mapper;

import com.revaluate.oauth.exception.AppIntegrationException;
import com.revaluate.resource.utils.Responses;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class AppIntegrationExceptionExceptionMapper implements ExceptionMapper<AppIntegrationException> {

    public Response toResponse(AppIntegrationException ex) {

        return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
    }
}
