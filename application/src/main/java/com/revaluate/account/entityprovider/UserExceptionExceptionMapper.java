package com.revaluate.account.entityprovider;

import com.revaluate.account.exception.UserException;
import com.revaluate.core.resource.Responses;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserExceptionExceptionMapper implements ExceptionMapper<UserException> {

    public Response toResponse(UserException ex) {

        return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
    }
}