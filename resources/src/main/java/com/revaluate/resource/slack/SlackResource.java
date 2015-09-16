package com.revaluate.resource.slack;

import com.revaluate.core.annotations.Public;
import com.revaluate.domain.slack.SlackAnswerDTO;
import com.revaluate.domain.slack.SlackDTO;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import com.revaluate.slack.SlackException;
import com.revaluate.slack.SlackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(SlackResource.SLACK)
@Component
public class SlackResource extends Resource {

    //-----------------------------------------------------------------
    // Path
    //-----------------------------------------------------------------
    public static final String SLACK = "slack";

    @Autowired
    private SlackService slackService;

    @POST
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
    public Response create(@Valid SlackDTO slackDTO) throws SlackException {
        SlackAnswerDTO answer = slackService.answer(slackDTO);

        return Responses.respond(Response.Status.OK, answer);
    }

}