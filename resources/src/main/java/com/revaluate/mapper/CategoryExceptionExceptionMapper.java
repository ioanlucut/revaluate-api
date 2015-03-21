package com.revaluate.mapper;

import com.revaluate.category.exception.CategoryException;
import com.revaluate.resource.utils.Responses;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class CategoryExceptionExceptionMapper implements ExceptionMapper<CategoryException> {

    public Response toResponse(CategoryException ex) {

        return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
    }
}
