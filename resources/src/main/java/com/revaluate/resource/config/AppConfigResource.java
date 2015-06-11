package com.revaluate.resource.config;

import com.revaluate.account.exception.UserException;
import com.revaluate.config.AppConfig;
import com.revaluate.core.annotations.Public;
import com.revaluate.domain.settings.AppConfigDTO;
import com.revaluate.domain.settings.AppConfigDTOBuilder;
import com.revaluate.domain.settings.KeyValueDTO;
import com.revaluate.domain.settings.KeyValueDTOBuilder;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Path(AppConfigResource.APP_CONFIG)
@Component
public class AppConfigResource extends Resource {

    public static final String APP_CONFIG = "appconfig";
    private static final String FETCH_CONFIG = "fetchConfig";

    @GET
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(FETCH_CONFIG)
    public Response isUnique() throws UserException {

        List<KeyValueDTO> keyValueDTOs = Arrays.asList(new KeyValueDTOBuilder().withKey("TRIAL_DAYS").withValue(String.valueOf(AppConfig.TRIAL_DATE)).build());

        AppConfigDTO appConfigDTO = new AppConfigDTOBuilder()
                .withVersion(AppConfig.VERSION)
                .withKeyValueDTOList(keyValueDTOs)
                .build();

        return Responses.respond(Response.Status.OK, appConfigDTO);
    }

}