package com.revaluate.account.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UserNotFoundException extends WebApplicationException {

    public UserNotFoundException() {
        super(Response.Status.NOT_FOUND.getReasonPhrase());
    }

    public UserNotFoundException(String message) {
        super(Response.status(Response.Status.NOT_FOUND).entity(message).type(MediaType.APPLICATION_JSON).build());
    }
}