package com.revaluate.account.entityprovider;

import com.revaluate.core.Responses;
import org.glassfish.jersey.message.internal.MessageBodyProviderNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MessageBodyProviderNotFoundMapper implements ExceptionMapper<MessageBodyProviderNotFoundException> {

    public Response toResponse(MessageBodyProviderNotFoundException ex) {

        return Responses.respond(Response.Status.BAD_REQUEST, ex.getMessage());
    }
}
