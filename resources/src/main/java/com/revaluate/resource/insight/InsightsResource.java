package com.revaluate.resource.insight;

import com.revaluate.domain.insights.daily.InsightsDailyDTO;
import com.revaluate.domain.insights.monthly.InsightsMonthlyDTO;
import com.revaluate.domain.insights.overview.InsightsOverviewDTO;
import com.revaluate.domain.insights.progress.ProgressInsightsDTO;
import com.revaluate.domain.insights.statistics.MonthsPerYearsDTO;
import com.revaluate.insights.service.*;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import com.revaluate.statistics.MonthsPerYearStatisticsService;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(InsightsResource.INSIGHTS)
@Component
public class InsightsResource extends Resource {

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
    private static final String INSIGHTS_DAILY_RETRIEVE_FROM_TO = "insights_daily_retrieve_from_to";
    private static final String INSIGHTS_PROGRESS_RETRIEVE_FROM_TO = "insights_progress_retrieve_from_to";

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
    private ProgressInsightsService progressInsightsService;

    @Autowired
    private OverviewInsightsService overviewInsightsService;

    @Autowired
    private DailyInsightsService dailyInsightsService;

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
    @Path(INSIGHTS_DAILY_RETRIEVE_FROM_TO)
    public Response getDailyInsightsFromTo(@QueryParam(FROM) LocalDateTime from, @QueryParam(TO) LocalDateTime to) {
        InsightsDailyDTO insightsDailyDTO = dailyInsightsService.fetchDailyInsightsAfterBeforePeriod(getCurrentUserId(), from, to);

        return Responses.respond(Response.Status.OK, insightsDailyDTO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(INSIGHTS_PROGRESS_RETRIEVE_FROM_TO)
    public Response fetchProgressInsightsBetween(@QueryParam(FROM) LocalDateTime from, @QueryParam(TO) LocalDateTime to) {
        ProgressInsightsDTO progressInsights = progressInsightsService.fetchProgressInsightsBetween(getCurrentUserId(), from, to);

        return Responses.respond(Response.Status.OK, progressInsights);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(INSIGHTS_MONTHS_PER_YEARS)
    public Response getInsightsMonthsPerYearsDTO() {
        MonthsPerYearsDTO monthsPerYearsDTO = monthsPerYearStatisticsService.getExistingDaysPerYearsWithExpensesDefined(getCurrentUserId());

        return Responses.respond(Response.Status.OK, monthsPerYearsDTO);
    }

}