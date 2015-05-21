package com.revaluate.resource.utils;

import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.core.jwt.JwtService;
import com.revaluate.settings.filter.AuthorizationRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

public class Resource {

    @Context
    protected UriInfo uriInfo;

    @Context
    protected HttpHeaders httpHeaders;

    @Autowired
    protected ConfigProperties configProperties;

    @Autowired
    protected JwtService jwtService;

    @Context
    private HttpServletRequest httpServletRequest;

    protected int getCurrentUserId() {

        return (int) httpServletRequest.getAttribute(AuthorizationRequestFilter.USER_ID);
    }
}