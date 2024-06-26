package com.revaluate.mapper;

import com.revaluate.account.exception.UserException;
import com.revaluate.resource.utils.Responses;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class UserExceptionExceptionMapper implements ExceptionMapper<UserException> {

    public Response toResponse(UserException ex) {

        return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
    }
}