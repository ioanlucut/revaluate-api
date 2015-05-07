package com.revaluate.resource.color;

import com.revaluate.color.exception.ColorException;
import com.revaluate.color.service.ColorService;
import com.revaluate.core.annotations.Public;
import com.revaluate.domain.color.ColorDTO;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path(ColorResource.COLOR)
@Component
public class ColorResource extends Resource {

    public static final String COLOR = "color";
    private static final String LIST_ALL_COLORS = "list";

    @Autowired
    private ColorService colorService;

    @GET
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Path(LIST_ALL_COLORS)
    public Response isUnique() throws ColorException {
        List<ColorDTO> allColors = colorService.findAllColors();

        return Responses.respond(Response.Status.OK, allColors);
    }
}