package com.revaluate.core.entityprovider;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ThrowableExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable error) {
        Response response;
        if (error instanceof WebApplicationException) {
            WebApplicationException webEx = (WebApplicationException) error;
            response = webEx.getResponse();
        } else {
            response = Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Internal error").type(MediaType.APPLICATION_JSON_TYPE).build();
        }
        return response;
    }
}


