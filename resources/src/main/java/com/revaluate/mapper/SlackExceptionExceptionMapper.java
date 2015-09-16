package com.revaluate.mapper;

import com.revaluate.resource.utils.Responses;
import com.revaluate.slack.SlackException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class SlackExceptionExceptionMapper implements ExceptionMapper<SlackException> {

    public Response toResponse(SlackException ex) {

        return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
    }
}
