package com.revaluate.account.resource;

import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.core.jwt.JwtService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

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

    protected int getCurrentUserId() {
        String authorization = httpHeaders.getHeaderString("Authorization");

        if (!authorization.contains(configProperties.getBearerHeaderKey())) {
            return -1;
        }

        String jwtToken = authorization.replaceAll(configProperties.getBearerHeaderKey(), "").trim();
        if (StringUtils.isBlank(jwtToken)) {
            return -1;
        }

        return jwtService.parseTokenSilent(jwtToken);
    }
}