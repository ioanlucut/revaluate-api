package com.revaluate.resource.insight;

import com.revaluate.domain.insights.monthly.InsightsMonthlyDTO;
import com.revaluate.domain.insights.statistics.InsightsMonthsPerYearsDTO;
import com.revaluate.domain.insights.overview.InsightsOverviewDTO;
import com.revaluate.insights.service.MonthsPerYearStatisticsService;
import com.revaluate.insights.service.OverviewInsightsService;
import com.revaluate.insights.service.MonthlyInsightsService;
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
    private MonthlyInsightsService monthlyInsightsService;

    @Autowired
    private MonthsPerYearStatisticsService monthsPerYearStatisticsService;

    @Autowired
    private OverviewInsightsService overviewInsightsService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(INSIGHTS_MONTHLY_RETRIEVE_FROM_TO)
    public Response getMonthlyInsightsFromTo(@QueryParam(FROM) LocalDateTime from, @QueryParam(TO) LocalDateTime to) {
        InsightsMonthlyDTO insightsMonthlyDTO = monthlyInsightsService.fetchMonthlyInsightsAfterBeforePeriod(getCurrentUserId(), from, to);

        return Responses.respond(Response.Status.OK, insightsMonthlyDTO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(INSIGHTS_OVERVIEW_RETRIEVE_FROM_TO)
    public Response getOverviewInsightsFromTo(@QueryParam(FROM) LocalDateTime from, @QueryParam(TO) LocalDateTime to) {
        InsightsOverviewDTO insightsOverview = overviewInsightsService.getOverviewInsightsBetween(getCurrentUserId(), from, to);

        return Responses.respond(Response.Status.OK, insightsOverview);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(INSIGHTS_MONTHS_PER_YEARS)
    public Response getInsightsMonthsPerYearsDTO() {
        InsightsMonthsPerYearsDTO insightsMonthsPerYearsDTO = monthsPerYearStatisticsService.getExistingDaysPerYearsWithExpensesDefined(getCurrentUserId());

        return Responses.respond(Response.Status.OK, insightsMonthsPerYearsDTO);
    }

}