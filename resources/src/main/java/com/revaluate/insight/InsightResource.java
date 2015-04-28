package com.revaluate.insight;

import com.revaluate.domain.insights.InsightDTO;
import com.revaluate.insights.service.InsightService;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(InsightResource.INSIGHTS)
@Component
public class InsightResource extends Resource {

    //-----------------------------------------------------------------
    // Path
    //-----------------------------------------------------------------
    public static final String INSIGHTS = "insights";

    //-----------------------------------------------------------------
    // Sub paths
    //-----------------------------------------------------------------
    private static final String RETRIEVE_INSIGHTS_FROM_TO = "retrieve_from_to";

    //-----------------------------------------------------------------
    // Path params
    //-----------------------------------------------------------------
    public static final String FROM = "from";
    public static final String TO = "to";

    @Autowired
    private InsightService insightService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(RETRIEVE_INSIGHTS_FROM_TO)
    public Response retrieveAllFromTo(@QueryParam(FROM) LocalDateTime from, @QueryParam(TO) LocalDateTime to) {
        InsightDTO insightDTO = insightService.fetchInsightAfterBeforePeriod(getCurrentUserId(), from, to);

        return Responses.respond(Response.Status.OK, insightDTO);
    }

}