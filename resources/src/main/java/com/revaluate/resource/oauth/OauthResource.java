package com.revaluate.resource.oauth;

import com.revaluate.integrations.exception.OauthIntegrationException;
import com.revaluate.integrations.service.OauthIntegrationService;
import com.revaluate.resource.utils.Resource;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(OauthResource.OAUTH)
@Component
public class OauthResource extends Resource {

    //-----------------------------------------------------------------
    // Path
    //-----------------------------------------------------------------
    public static final String OAUTH = "oauth";

    //-----------------------------------------------------------------
    // Query params
    //-----------------------------------------------------------------
    public static final String CODE = "code";
    public static final String REDIRECT_URI = "redirect_uri";

    @Autowired
    private OauthIntegrationService oauthIntegrationService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@NotEmpty @QueryParam(CODE) String code, @NotEmpty @QueryParam(REDIRECT_URI) String redirectUri) throws OauthIntegrationException {

        oauthIntegrationService.createOauthIntegrationSlack(code, redirectUri, getCurrentUserId());

        return Response.status(Response.Status.OK).build();
    }

}