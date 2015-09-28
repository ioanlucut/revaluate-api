package com.revaluate.resource.oauth;

import com.revaluate.domain.oauth.AppIntegrationDTO;
import com.revaluate.oauth.exception.AppIntegrationException;
import com.revaluate.oauth.service.AppIntegrationService;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
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
    // Sub paths
    //-----------------------------------------------------------------
    private static final String REMOVE_APP_INTEGRATION = "{appId}";

    //-----------------------------------------------------------------
    // Query params
    //-----------------------------------------------------------------
    public static final String CODE = "code";
    public static final String REDIRECT_URI = "redirect_uri";

    //-----------------------------------------------------------------
    // Path params
    //-----------------------------------------------------------------
    private static final String APP_INTEGRATION_ID = "appId";

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

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path(REMOVE_APP_INTEGRATION)
    public Response remove(@PathParam(APP_INTEGRATION_ID) @NotNull int appIntegrationId) throws AppIntegrationException {
        oauthIntegrationService.removeIntegration(appIntegrationId, getCurrentUserId());

        return Responses.respond(Response.Status.OK, "App integration successfully removed");
    }

}