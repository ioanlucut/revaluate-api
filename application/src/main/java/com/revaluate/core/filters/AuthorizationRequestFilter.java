package com.revaluate.core.filters;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class AuthorizationRequestFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (!resourceInfo.getResourceMethod().isAnnotationPresent(PublicMethod.class)) {
            String authorization = requestContext.getHeaderString("Authorization");

            if (authorization == null) {
                requestContext.abortWith(Response
                        .status(Response.Status.UNAUTHORIZED)
                        .entity("User is not authorized")
                        .build());
            }
        }
    }
}

