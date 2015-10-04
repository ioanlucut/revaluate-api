package com.revaluate.mapper;

import com.revaluate.intercom.IntercomTracker;
import com.revaluate.slack.SlackException;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Should always respond with 200.
 */
@Component
@Provider
public class SlackExceptionExceptionMapper implements ExceptionMapper<SlackException> {

    public Response toResponse(SlackException ex) {

        return Response.status(Response.Status.OK).entity(ex.getMessage()).build();
    }
}
