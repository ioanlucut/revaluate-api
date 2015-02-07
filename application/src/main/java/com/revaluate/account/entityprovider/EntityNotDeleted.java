package com.revaluate.account.entityprovider;

import org.springframework.dao.EmptyResultDataAccessException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotDeleted implements ExceptionMapper<EmptyResultDataAccessException> {

    public Response toResponse(EmptyResultDataAccessException ex) {

        return Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
    }
}
