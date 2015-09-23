package com.revaluate.resource.oauth;

import com.revaluate.domain.oauth.AppIntegrationDTO;
import com.revaluate.oauth.exception.AppIntegrationException;
import com.revaluate.oauth.service.AppIntegrationService;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path(AppIntegrationResource.OAUTH)
@Component
public class AppIntegrationResource extends Resource {

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
    private AppIntegrationService oauthIntegrationService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(@NotEmpty @QueryParam(CODE) String code, @NotEmpty @QueryParam(REDIRECT_URI) String redirectUri) throws AppIntegrationException {
        AppIntegrationDTO oauthIntegrationSlack = oauthIntegrationService.createOauthIntegrationSlack(code, redirectUri, getCurrentUserId());

        return Responses.respond(Response.Status.OK, oauthIntegrationSlack);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public Response retrieve() throws AppIntegrationException {
        List<AppIntegrationDTO> allIntegrations = oauthIntegrationService.findAllIntegrations(getCurrentUserId());

        return Responses.respond(Response.Status.OK, allIntegrations);
    }

}