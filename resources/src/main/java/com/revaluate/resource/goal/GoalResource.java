package com.revaluate.resource.goal;

import com.revaluate.category.exception.CategoryException;
import com.revaluate.core.annotations.PaymentRequired;
import com.revaluate.domain.goal.GoalDTO;
import com.revaluate.goals.exception.GoalException;
import com.revaluate.goals.service.GoalService;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path(GoalResource.GOALS)
@Component
public class GoalResource extends Resource {

    //-----------------------------------------------------------------
    // Path
    //-----------------------------------------------------------------
    public static final String GOALS = "goals";

    //-----------------------------------------------------------------
    // Sub paths
    //-----------------------------------------------------------------
    private static final String REMOVE_GOAL = "{goalId}";
    private static final String RETRIEVE_GOALS = "retrieve";
    private static final String RETRIEVE_GOALS_FROM_TO = "retrieve_from_to";
    private static final String BULK_DELETE = "bulkDelete";
    private static final String IS_UNIQUE_GOAL_CATEGORY = "isUnique/{categoryId}";

    //-----------------------------------------------------------------
    // Path params
    //-----------------------------------------------------------------
    private static final String GOAL_ID = "goalId";
    public static final String FROM = "from";
    public static final String TO = "to";
    private static final String CATEGORY_ID = "categoryId";

    //-----------------------------------------------------------------
    // Json keys
    //-----------------------------------------------------------------
    public static final String IS_UNIQUE_GOAL_CATEGORY_JSON_KEY = "isUniqueGoalCategory";


    @Autowired
    private GoalService goalService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(IS_UNIQUE_GOAL_CATEGORY)
    public Response isUniqueGoalCategory(@PathParam(CATEGORY_ID) @NotNull int categoryId, @QueryParam(FROM) @NotNull LocalDateTime from, @QueryParam(TO) @NotNull LocalDateTime to) throws CategoryException {
        Map<String, Boolean> response = new HashMap<>();
        response.put(IS_UNIQUE_GOAL_CATEGORY_JSON_KEY, goalService.isUniqueGoalWithCategoryBetween(getCurrentUserId(), categoryId, from, to));

        return Responses.respond(Response.Status.OK, response);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @PaymentRequired
    public Response create(@Valid GoalDTO goalDTO) throws GoalException {
        GoalDTO createdGoalDTO = goalService.create(goalDTO, getCurrentUserId());

        return Responses.respond(Response.Status.OK, createdGoalDTO);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @PaymentRequired
    public Response update(@Valid GoalDTO goalDTO) throws GoalException {
        GoalDTO createdGoalDTO = goalService.update(goalDTO, getCurrentUserId());

        return Responses.respond(Response.Status.OK, createdGoalDTO);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path(REMOVE_GOAL)
    @PaymentRequired
    public Response remove(@PathParam(GOAL_ID) @NotNull int goalId) throws GoalException {
        goalService.remove(goalId, getCurrentUserId());

        return Responses.respond(Response.Status.OK, "Goal successfully removed");
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(RETRIEVE_GOALS)
    public Response retrieveAll() {
        List<GoalDTO> allGoalsFor = goalService.findAllGoalsFor(getCurrentUserId());

        return Responses.respond(Response.Status.OK, allGoalsFor);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(RETRIEVE_GOALS_FROM_TO)
    public Response retrieveAllFromTo(@QueryParam(FROM) @NotNull LocalDateTime from, @QueryParam(TO) @NotNull LocalDateTime to) {
        List<GoalDTO> allGoalsFor = goalService.findAllGoalsAfterBefore(getCurrentUserId(), from, to);

        return Responses.respond(Response.Status.OK, allGoalsFor);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(BULK_DELETE)
    @PaymentRequired
    public Response bulkDelete(@NotNull @Valid List<GoalDTO> goalDTOs) throws GoalException {
        goalService.bulkDelete(goalDTOs, getCurrentUserId());

        return Responses.respond(Response.Status.OK, "Bulk delete action complete");
    }

}