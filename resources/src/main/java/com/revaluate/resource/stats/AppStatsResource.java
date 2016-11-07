package com.revaluate.resource.stats;

import com.revaluate.account.exception.UserException;
import com.revaluate.core.annotations.Public;
import com.revaluate.expense.persistence.ExpenseRepository;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path(AppStatsResource.APP_STATS)
@Component
public class AppStatsResource extends Resource {

    static final String APP_STATS = "appstats";
    private static final String FETCH_STATS = "fetch";
    private static final String FETCH_STATS_INSTANT = "fetchInstant";

    private static final Map<String, Object> APP_STATS_MAP = new HashMap<>();

    @Autowired
    private ExpenseRepository expenseRepository;

    @PostConstruct
    public void fillAppConfiguration() {
        APP_STATS_MAP.put("EXPENSES_COUNTS", expenseRepository.count());
    }

    @GET
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(FETCH_STATS)
    public Response fetchStatsWithBrowserCaching() throws UserException {

        return Responses.responseWithBrowserCachingForAppStats(Response.Status.OK, APP_STATS_MAP);
    }

    @GET
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(FETCH_STATS_INSTANT)
    public Response fetchStatsWithNoBrowserCaching() throws UserException {
        fillAppConfiguration();

        return Responses.respond(Response.Status.OK, APP_STATS_MAP);
    }

}