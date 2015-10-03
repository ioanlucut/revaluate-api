package com.revaluate.resource.oauth;

import com.revaluate.core.annotations.Public;
import com.revaluate.domain.oauth.AppIntegrationDTO;
import com.revaluate.domain.oauth.AppSlackIntegrationDTO;
import com.revaluate.oauth.exception.AppIntegrationException;
import com.revaluate.oauth.service.AppIntegrationGranterService;
import com.revaluate.oauth.service.AppIntegrationService;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
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
    public static final String STATE = "state";
    public static final String USER_ID = "userId";
    public static final String TEAM_ID = "teamId";
    public static final String TEAM_NAME = "teamName";
    public static final String ACCESS_TOKEN = "accessToken";
    public static final String SCOPE = "scope";

    //-----------------------------------------------------------------
    // Path params
    //-----------------------------------------------------------------
    private static final String APP_INTEGRATION_ID = "appId";

    //-----------------------------------------------------------------
    // Sub path
    //-----------------------------------------------------------------
    public static final String OAUTH_GRANT_PROXY = "/grant";

    @Autowired
    private AppIntegrationService appIntegrationService;

    @Autowired
    private AppIntegrationGranterService appIntegrationGranterService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(OAUTH_GRANT_PROXY)
    @Public
    public Response grantAccess(@NotEmpty @QueryParam(CODE) String code, @NotEmpty @QueryParam(STATE) String state) throws AppIntegrationException {

        return Response.temporaryRedirect(appIntegrationGranterService.grantOauthIntegration(code, state)).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(@Valid AppSlackIntegrationDTO appSlackIntegrationDTO) throws AppIntegrationException {
        AppIntegrationDTO oauthIntegrationSlack = appIntegrationService.createOauthIntegrationSlack(appSlackIntegrationDTO, getCurrentUserId());

        return Responses.respond(Response.Status.OK, oauthIntegrationSlack);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public Response retrieve() throws AppIntegrationException {
        List<AppIntegrationDTO> allIntegrations = appIntegrationService.findAllIntegrations(getCurrentUserId());

        return Responses.respond(Response.Status.OK, allIntegrations);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path(REMOVE_APP_INTEGRATION)
    public Response remove(@PathParam(APP_INTEGRATION_ID) @NotNull int appIntegrationId) throws AppIntegrationException {
        appIntegrationService.removeIntegration(appIntegrationId, getCurrentUserId());

        return Responses.respond(Response.Status.OK, "App integration successfully removed");
    }

}