package com.revaluate.mapper;

import com.revaluate.color.exception.ColorException;
import com.revaluate.resource.utils.Responses;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Component
@Provider
public class ColorExceptionExceptionMapper implements ExceptionMapper<ColorException> {

    public Response toResponse(ColorException ex) {

        return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
    }
}
