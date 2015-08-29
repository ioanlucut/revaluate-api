package com.revaluate.resource.statistics;

import com.revaluate.domain.insights.statistics.MonthsPerYearsDTO;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import com.revaluate.statistics.MonthsPerYearStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(StatisticsResource.STATISTICS)
@Component
public class StatisticsResource extends Resource {

    //-----------------------------------------------------------------
    // Path
    //-----------------------------------------------------------------
    public static final String STATISTICS = "statistics";

    //-----------------------------------------------------------------
    // Sub paths
    //-----------------------------------------------------------------
    private static final String EXPENSES_MONTHS_PER_YEARS_STATISTICS = "expenses_months_per_years";
    private static final String GOALS_MONTHS_PER_YEARS_STATISTICS = "goals_months_per_years";

    @Autowired
    private MonthsPerYearStatisticsService monthsPerYearStatisticsService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(EXPENSES_MONTHS_PER_YEARS_STATISTICS)
    public Response getExpensesMonthsPerYearsStatisticsDTO() {
        MonthsPerYearsDTO monthsPerYearsDTO = monthsPerYearStatisticsService.getExistingDaysPerYearsWithExpensesDefined(getCurrentUserId());

        return Responses.respond(Response.Status.OK, monthsPerYearsDTO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(GOALS_MONTHS_PER_YEARS_STATISTICS)
    public Response getGoalsMonthsPerYearsStatisticsDTO() {
        MonthsPerYearsDTO monthsPerYearsDTO = monthsPerYearStatisticsService.getExistingDaysPerYearsWithGoalsDefined(getCurrentUserId());

        return Responses.respond(Response.Status.OK, monthsPerYearsDTO);
    }

}