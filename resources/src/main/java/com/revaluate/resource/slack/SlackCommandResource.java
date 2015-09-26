package com.revaluate.resource.slack;

import com.revaluate.core.annotations.Public;
import com.revaluate.domain.oauth.AppIntegrationType;
import com.revaluate.domain.slack.SlackDTO;
import com.revaluate.domain.slack.SlackDTOBuilder;
import com.revaluate.intercom.IntercomTracker;
import com.revaluate.oauth.persistence.AppIntegrationSlack;
import com.revaluate.oauth.persistence.AppIntegrationSlackRepository;
import com.revaluate.resource.utils.Resource;
import com.revaluate.slack.SlackException;
import com.revaluate.slack_command.SlackCommandService;
import com.revaluate.slack_command.SlackCommandServiceImpl;
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

    @Autowired
    private IntercomTracker intercomTracker;

    @GET
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@NotEmpty(message = "The slack command is not proper.")
                           @QueryParam(TOKEN)
                           String token,

                           @NotEmpty(message = "The slack command is not proper.")
                           @QueryParam(TEAM_ID)
                           String teamId,

                           @NotEmpty(message = "The slack command is not proper.")
                           @QueryParam(TEAM_DOMAIN)
                           String teamDomain,

                           @NotEmpty(message = "The slack command is not proper.")
                           @QueryParam(CHANNEL_ID)
                           String channelId,

                           @NotEmpty(message = "The slack command is not proper.")
                           @QueryParam(CHANNEL_NAME)
                           String channelName,

                           @NotEmpty(message = "The slack command is not proper.")
                           @QueryParam(USER_ID)
                           String userId,

                           @NotEmpty(message = "The slack command is not proper.")
                           @QueryParam(USER_NAME)
                           String userName,

                           @NotEmpty(message = "The slack command is not proper.")
                           @QueryParam(COMMAND)
                           String command,

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
                .orElseThrow(() -> new SlackException(SlackCommandServiceImpl.INVALID_USER));

        Integer matchingUserId = oneBySlackUserId.getUser().getId();

        try {
            String answer = slackService.answer(request, matchingUserId);
            return Response.status(Response.Status.OK).entity(answer).build();
        } finally {
            //-----------------------------------------------------------------
            // Track event async
            //-----------------------------------------------------------------
            intercomTracker.trackEventAsync(IntercomTracker.EventType.SLACK_COMMAND, String.valueOf(matchingUserId));
        }
    }

}