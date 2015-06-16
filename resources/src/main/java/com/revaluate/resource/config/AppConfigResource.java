package com.revaluate.resource.config;

import com.revaluate.account.exception.UserException;
import com.revaluate.color.service.ColorService;
import com.revaluate.config.AppConfig;
import com.revaluate.core.annotations.Public;
import com.revaluate.domain.color.ColorDTO;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path(AppConfigResource.APP_CONFIG)
@Component
public class AppConfigResource extends Resource {

    public static final String APP_CONFIG = "appconfig";
    private static final String FETCH_CONFIG = "fetchConfig";

    @Autowired
    private ColorService colorService;

    @GET
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(FETCH_CONFIG)
    public Response isUnique() throws UserException {
        Map<String, Object> appConfig = new HashMap<>();

        appConfig.put("VERSION", AppConfig.VERSION);
        appConfig.put("TRIAL_DAYS", AppConfig.TRIAL_DATE);

        //-----------------------------------------------------------------
        // Put also all colors
        //-----------------------------------------------------------------
        List<ColorDTO> allColors = colorService.findAllColors();
        appConfig.put("ALL_COLORS", allColors);

        //-----------------------------------------------------------------
        // Put also all predefined categories
        //-----------------------------------------------------------------
        List<String> predefinedCategories = Arrays.asList("Bills", "Food", "Clothes", "Car", "Donations", "Hobby", "Health", "Education", "Investments", "House");
        appConfig.put("PREDEFINED_CATEGORIES", predefinedCategories);


        return Responses.respond(Response.Status.OK, appConfig);
    }

}