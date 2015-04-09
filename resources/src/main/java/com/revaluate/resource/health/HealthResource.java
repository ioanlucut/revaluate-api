package com.revaluate.resource.health;

import com.revaluate.account.exception.UserException;
import com.revaluate.core.annotations.Public;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(HealthResource.HEALTH)
@Component
public class HealthResource extends Resource {

    public static final String HEALTH = "/";

    @GET
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkHealth() throws UserException {

        return Responses.respond(Response.Status.OK, "I'm alive, thanks God.");
    }
}