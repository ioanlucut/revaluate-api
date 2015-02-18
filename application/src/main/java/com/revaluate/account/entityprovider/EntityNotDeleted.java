package com.revaluate.account.entityprovider;

import com.revaluate.core.resource.Responses;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotDeleted implements ExceptionMapper<EmptyResultDataAccessException> {

    public Response toResponse(EmptyResultDataAccessException ex) {

        return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
    }
}
