package com.revaluate.core.resource;

import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.Response;

public class Responses {

    public static Response respond(Response.Status status, String answer) {
        if (StringUtils.isNotBlank(answer)) {
            return Response.status(status).entity(new Answer(answer)).build();
        }

        return Response.status(status).build();
    }

    public static Response respond(Response.Status status, Object entity) {

        return Response.status(status).entity(entity).build();
    }
}
