package com.revaluate.account.resource;

import com.revaluate.core.ConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

public class Resource {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Context
    protected UriInfo uriInfo;

    @Context
    protected HttpHeaders httpHeaders;

    @Autowired
    private ConfigProperties configProperties;
}