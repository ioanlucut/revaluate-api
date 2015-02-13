package com.revaluate.account.exception;

import com.revaluate.core.Responses;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class UserNotFoundException extends WebApplicationException {

    public UserNotFoundException() {
        super(Responses.respond(Response.Status.BAD_REQUEST, Response.Status.BAD_REQUEST.getReasonPhrase()));
    }

    public UserNotFoundException(String message) {
        super(Responses.respond(Response.Status.BAD_REQUEST, message));
    }
}