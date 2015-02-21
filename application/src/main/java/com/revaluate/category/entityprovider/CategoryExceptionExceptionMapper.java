package com.revaluate.category.entityprovider;

import com.revaluate.core.resource.Responses;
import com.revaluate.category.exception.CategoryException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CategoryExceptionExceptionMapper implements ExceptionMapper<CategoryException> {

    public Response toResponse(CategoryException ex) {

        return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
    }
}
