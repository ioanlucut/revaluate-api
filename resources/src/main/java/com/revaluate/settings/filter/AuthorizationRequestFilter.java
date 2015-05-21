package com.revaluate.settings.filter;

import com.revaluate.core.annotations.Public;
import com.revaluate.core.jwt.JwtException;
import com.revaluate.core.jwt.JwtService;
import com.revaluate.resource.utils.Responses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Priority;
import javax.persistence.EntityNotFoundException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Component
@Priority(Priorities.AUTHENTICATION)
public class AuthorizationRequestFilter implements ContainerRequestFilter {

    public static final String BEARER_HEADER = "Bearer";
    public static final String OPTIONS = "OPTIONS";
    public static final String USER_ID = "userId";

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

    private boolean isOptionsHttpMethod(ContainerRequestContext requestContext) {
        return OPTIONS.equals(requestContext.getMethod());
    }

    public boolean isPublicMethod() {
        return resourceInfo.getResourceMethod().isAnnotationPresent(Public.class);
    }

    private boolean isTokenMissingOrInvalid(ContainerRequestContext requestContext) {
        String authorization = requestContext.getHeaderString("Authorization");

        //-----------------------------------------------------------------
        // No authorization header ?
        //-----------------------------------------------------------------
        if (StringUtils.isBlank(authorization)) {

            return Boolean.TRUE;
        }

        //-----------------------------------------------------------------
        // No bearer inside ?
        //-----------------------------------------------------------------
        if (!authorization.contains(BEARER_HEADER)) {

            return Boolean.TRUE;
        }

        //-----------------------------------------------------------------
        // No token?
        //-----------------------------------------------------------------
        String jwtToken = authorization.replaceAll(BEARER_HEADER, "").trim();
        if (StringUtils.isBlank(jwtToken)) {

            return Boolean.TRUE;
        }

        return setTokenGetIfSuccessful(jwtToken, requestContext);
    }

    private boolean setTokenGetIfSuccessful(String jwtToken, ContainerRequestContext requestContext) {

        try {
            //-----------------------------------------------------------------
            // If token can be parsed, then it is ok.
            //-----------------------------------------------------------------
            int userId = jwtService.parseToken(jwtToken);
            requestContext.setProperty(USER_ID, userId);
        } catch (JwtException | EntityNotFoundException ex) {

            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    public void abort(ContainerRequestContext requestContext) {
        requestContext.abortWith(Responses.respond(Response.Status.UNAUTHORIZED, "Unauthorized user."));
    }
}

