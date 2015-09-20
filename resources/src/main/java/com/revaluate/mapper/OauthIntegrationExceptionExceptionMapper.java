package com.revaluate.mapper;

import com.revaluate.integrations.exception.OauthIntegrationException;
import com.revaluate.resource.utils.Responses;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class OauthIntegrationExceptionExceptionMapper implements ExceptionMapper<OauthIntegrationException> {

    public Response toResponse(OauthIntegrationException ex) {

        return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
    }
}
