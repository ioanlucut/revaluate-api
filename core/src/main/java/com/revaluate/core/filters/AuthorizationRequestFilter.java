package com.revaluate.core.filters;

import com.revaluate.core.annotations.Public;
import com.revaluate.core.jwt.JwtException;
import com.revaluate.core.jwt.JwtService;
import com.revaluate.core.resource.Responses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Component
public class AuthorizationRequestFilter implements ContainerRequestFilter {

    public static final String BEARER_HEADER = "Bearer";
    public static final String OPTIONS = "OPTIONS";

    @Context
    private ResourceInfo resourceInfo;

    @Autowired
    private JwtService jwtService;

    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (isOptionsHttpMethod(requestContext) || isPublicMethod()) {
            return;
        }

        if (isTokenMissingOrInvalid(requestContext)) {
            abort(requestContext);
        }
    }

    private boolean isTokenMissingOrInvalid(ContainerRequestContext requestContext) {
        String authorization = requestContext.getHeaderString("Authorization");

        return StringUtils.isBlank(authorization) || isInvalidToken(authorization);
    }

    private boolean isOptionsHttpMethod(ContainerRequestContext requestContext) {
        return OPTIONS.equals(requestContext.getMethod());
    }

    public boolean isPublicMethod() {
        return resourceInfo.getResourceMethod().isAnnotationPresent(Public.class);
    }

    private boolean isInvalidToken(String authorization) {
        if (!authorization.contains(BEARER_HEADER)) {
            return false;
        }

        String jwtToken = authorization.replaceAll(BEARER_HEADER, "").trim();
        if (StringUtils.isBlank(jwtToken)) {
            return true;
        }

        try {
            //-----------------------------------------------------------------
            // If token can be parsed, then it is ok.
            //-----------------------------------------------------------------
            jwtService.parseToken(jwtToken);
        } catch (JwtException | EntityNotFoundException e) {
            return true;
        }

        return false;
    }

    public void abort(ContainerRequestContext requestContext) {
        requestContext.abortWith(Responses.respond(Response.Status.UNAUTHORIZED, "User is not authorized"));
    }
}

