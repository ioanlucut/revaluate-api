package com.revaluate.account.entityprovider;

import com.revaluate.core.Responses;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundMapper implements ExceptionMapper<EntityNotFoundException> {

    public Response toResponse(javax.persistence.EntityNotFoundException ex) {

        return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
    }
}
