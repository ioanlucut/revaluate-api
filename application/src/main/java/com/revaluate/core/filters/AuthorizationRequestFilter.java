package com.revaluate.core.filters;

import com.revaluate.account.repository.UserRepository;
import com.revaluate.core.Responses;
import com.revaluate.core.jwt.JwtException;
import com.revaluate.core.jwt.JwtService;
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
    @Context
    private ResourceInfo resourceInfo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (!isPublicMethod()) {
            String authorization = requestContext.getHeaderString("Authorization");

            if (StringUtils.isBlank(authorization) || isInvalidToken(authorization)) {
                abort(requestContext);
            }
        }
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
            int userId = jwtService.parseToken(jwtToken);
            boolean userExists = userRepository.exists(userId);
            if (!userExists) {
                return true;
            }
        } catch (JwtException | EntityNotFoundException e) {
            return true;
        }

        return false;
    }

    public void abort(ContainerRequestContext requestContext) {
        requestContext.abortWith(Responses.respond(Response.Status.UNAUTHORIZED, "User is not authorized"));
    }
}

