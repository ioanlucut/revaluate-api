package com.revaluate.resource.insight;

import com.revaluate.domain.insights.InsightDTO;
import com.revaluate.domain.insights.InsightsMonthsPerYearsDTO;
import com.revaluate.domain.insights.overview.InsightsOverviewDTO;
import com.revaluate.insights.service.InsightMonthsPerYearService;
import com.revaluate.insights.service.InsightOverviewService;
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
    private static final String INSIGHTS_MONTHS_PER_YEARS = "insights_months_per_years";
    private static final String INSIGHTS_MONTHLY_RETRIEVE_FROM_TO = "retrieve_from_to";
    private static final String INSIGHTS_OVERVIEW_RETRIEVE_FROM_TO = "insights_overview_retrieve_from_to";

    //-----------------------------------------------------------------
    // Path params
    //-----------------------------------------------------------------
    public static final String FROM = "from";
    public static final String TO = "to";

    @Autowired
    private InsightService insightService;

    @Autowired
    private InsightMonthsPerYearService insightMonthsPerYearService;

    @Autowired
    private InsightOverviewService insightOverviewService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(INSIGHTS_MONTHLY_RETRIEVE_FROM_TO)
    public Response getMonthlyInsightsFromTo(@QueryParam(FROM) LocalDateTime from, @QueryParam(TO) LocalDateTime to) {
        InsightDTO insightDTO = insightService.fetchInsightAfterBeforePeriod(getCurrentUserId(), from, to);

        return Responses.respond(Response.Status.OK, insightDTO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(INSIGHTS_OVERVIEW_RETRIEVE_FROM_TO)
    public Response getOverviewInsightsFromTo(@QueryParam(FROM) LocalDateTime from, @QueryParam(TO) LocalDateTime to) {
        InsightsOverviewDTO insightsOverview = insightOverviewService.getInsightsOverviewBetween(getCurrentUserId(), from, to);

        return Responses.respond(Response.Status.OK, insightsOverview);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(INSIGHTS_MONTHS_PER_YEARS)
    public Response getInsightsMonthsPerYearsDTO() {
        InsightsMonthsPerYearsDTO insightsMonthsPerYearsDTO = insightMonthsPerYearService.getExistingDaysPerYearsWithExpensesDefined(getCurrentUserId());

        return Responses.respond(Response.Status.OK, insightsMonthsPerYearsDTO);
    }

}