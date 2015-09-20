package com.revaluate.resource.slack;

import com.revaluate.core.annotations.Public;
import com.revaluate.domain.app_integration.AppIntegrationType;
import com.revaluate.domain.slack.SlackDTO;
import com.revaluate.domain.slack.SlackDTOBuilder;
import com.revaluate.app_integration.persistence.AppIntegrationSlack;
import com.revaluate.app_integration.persistence.AppIntegrationSlackRepository;
import com.revaluate.resource.utils.Resource;
import com.revaluate.slack.SlackException;
import com.revaluate.slack_command.SlackCommandService;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(SlackCommandResource.SLACK)
@Component
public class SlackCommandResource extends Resource {

    //-----------------------------------------------------------------
    // Path
    //-----------------------------------------------------------------
    public static final String SLACK = "slack";

    //-----------------------------------------------------------------
    // Query params
    //-----------------------------------------------------------------
    public static final String USER_NAME = "user_name";
    public static final String COMMAND = "command";
    public static final String USER_ID = "user_id";
    public static final String CHANNEL_NAME = "channel_name";
    public static final String CHANNEL_ID = "channel_id";
    public static final String TEAM_DOMAIN = "team_domain";
    public static final String TEAM_ID = "team_id";
    public static final String TOKEN = "token";
    public static final String TEXT = "text";

    @Autowired
    private SlackCommandService slackService;

    @Autowired
    private AppIntegrationSlackRepository oauthIntegrationSlackRepository;

    @GET
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@NotEmpty
                           @QueryParam(TOKEN)
                           String token,

                           @NotEmpty
                           @QueryParam(TEAM_ID)
                           String teamId,

                           @NotEmpty
                           @QueryParam(TEAM_DOMAIN)
                           String teamDomain,

                           @NotEmpty
                           @QueryParam(CHANNEL_ID)
                           String channelId,

                           @NotEmpty
                           @QueryParam(CHANNEL_NAME)
                           String channelName,

                           @NotEmpty
                           @QueryParam(USER_ID)
                           String userId,

                           @NotEmpty
                           @QueryParam(USER_NAME)
                           String userName,

                           @NotEmpty
                           @QueryParam(COMMAND)
                           String command,

                           @NotEmpty
                           @QueryParam(TEXT)
                           String text) throws SlackException {
        SlackDTO request = new SlackDTOBuilder()
                .withToken(token)
                .withTeamId(teamId)
                .withTeamDomain(teamDomain)
                .withChannelId(channelId)
                .withChannelName(channelName)
                .withUserId(userId)
                .withUserName(userName)
                .withCommand(command)
                .withText(text)
                .build();

        AppIntegrationSlack oneBySlackUserId = oauthIntegrationSlackRepository
                .findOneByAppIntegrationTypeAndSlackUserIdAndSlackTeamId(AppIntegrationType.SLACK, request.getUserId(), request.getTeamId())
                .orElseThrow(() -> new SlackException("Sorry, do we know you?.."));

        Integer matchingUserId = oneBySlackUserId.getUser().getId();
        String answer = slackService.answer(request, matchingUserId);

        return Response.status(Response.Status.OK).entity(answer).build();
    }

}