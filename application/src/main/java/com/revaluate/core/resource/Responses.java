package com.revaluate.core.resource;

import javax.ws.rs.core.Response;

public class Responses {

    public static Response respond(Response.Status status, String answer) {

        return Response.status(status).entity(new Answer(answer)).build();
    }

    public static Response respond(Response.Status status, Object entity) {

        return Response.status(status).entity(entity).build();
    }
}
