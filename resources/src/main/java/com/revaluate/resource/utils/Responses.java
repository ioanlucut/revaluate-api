package com.revaluate.resource.utils;

import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.Response;

public class Responses {

    public final static int CACHE_DURATION_IN_SECOND = 60 * 60 * 24 * 7; // 7 days
    public final static long CACHE_DURATION_IN_MS = CACHE_DURATION_IN_SECOND * 1000;

    public static Response respond(Response.Status status, String answer) {
        if (StringUtils.isNotBlank(answer)) {

            return respond(status, new Answer(answer));
        }

        return Response.status(status).build();
    }

    public static Response respond(Response.Status status, Object entity) {

        return Response.status(status).entity(entity).build();
    }

    public static Response responseWithBrowserCaching(Response.Status status, Object entity) {
        long now = System.currentTimeMillis();

        return Response
                .status(status)
                .header("Cache-Control", "max-age=" + CACHE_DURATION_IN_SECOND)
                .header("Cache-Control", "must-revalidate")
                .header("Last-Modified", now)
                .header("Expires", now + CACHE_DURATION_IN_MS)
                .entity(entity).build();
    }
}