package com.revaluate.resource.config;

import com.revaluate.account.exception.UserException;
import com.revaluate.color.service.ColorService;
import com.revaluate.config.AppConfig;
import com.revaluate.core.annotations.Public;
import com.revaluate.currency.CurrenciesLocaleGenerator;
import com.revaluate.currency.service.CurrencyService;
import com.revaluate.domain.account.UserType;
import com.revaluate.domain.color.ColorDTO;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Path(AppConfigResource.APP_CONFIG)
@Component
public class AppConfigResource extends Resource {

    public static final String APP_CONFIG = "appconfig";
    private static final String FETCH_CONFIG = "fetchConfig";

    public static final Map<String, Object> APP_CONFIGURATION_MAP = new HashMap<>();

    @Autowired
    private ColorService colorService;

    @Autowired
    private CurrencyService currencyService;

    @PostConstruct
    public void fillAppConfiguration() {
        APP_CONFIGURATION_MAP.put("VERSION", AppConfig.VERSION);
        APP_CONFIGURATION_MAP.put("TRIAL_DAYS", AppConfig.TRIAL_DAYS);

        //-----------------------------------------------------------------
        // Put also all colors
        //-----------------------------------------------------------------
        List<ColorDTO> allColors = colorService.findAllColors();
        APP_CONFIGURATION_MAP.put("ALL_COLORS", allColors);

        //-----------------------------------------------------------------
        // Put also all colors
        //-----------------------------------------------------------------
        APP_CONFIGURATION_MAP.put("USER_TYPES", Arrays.stream(UserType.values()).collect(Collectors.toList()));

        //-----------------------------------------------------------------
        // Put also all predefined categories
        //-----------------------------------------------------------------
        List<String> predefinedCategories = Arrays.asList("Bills", "Food", "Clothes", "Car", "Donations", "Hobby", "Health", "Education", "Investments", "House");
        APP_CONFIGURATION_MAP.put("PREDEFINED_CATEGORIES", predefinedCategories);

        //-----------------------------------------------------------------
        // Categories boundaries
        //-----------------------------------------------------------------
        APP_CONFIGURATION_MAP.put("MIN_ALLOWED_CATEGORIES", AppConfig.MIN_ALLOWED_CATEGORIES);
        APP_CONFIGURATION_MAP.put("MAX_ALLOWED_CATEGORIES", AppConfig.MAX_ALLOWED_CATEGORIES);

        //-----------------------------------------------------------------
        // Others
        //-----------------------------------------------------------------
        APP_CONFIGURATION_MAP.put("MIN_EXPENSES_TO_ENABLE_BULK_ACTION", AppConfig.MIN_EXPENSES_TO_ENABLE_BULK_ACTION);
        APP_CONFIGURATION_MAP.put("IMPORT_MIN_CATEGORIES_TO_SELECT", AppConfig.IMPORT_MIN_CATEGORIES_TO_SELECT);
        APP_CONFIGURATION_MAP.put("SETUP_MIN_CATEGORIES_TO_SELECT", AppConfig.SETUP_MIN_CATEGORIES_TO_SELECT);

        //-----------------------------------------------------------------
        // Currencies
        //-----------------------------------------------------------------
        APP_CONFIGURATION_MAP.put("CURRENCIES", currencyService.findAllCurrencies());
        APP_CONFIGURATION_MAP.put("CURRENCIES_LOCALE_MAP", CurrenciesLocaleGenerator.generateCurrencyLocaleMap());

        //-----------------------------------------------------------------
        // Price boundaries
        //-----------------------------------------------------------------
        APP_CONFIGURATION_MAP.put("VALUE_INTEGER_SIZE", AppConfig.VALUE_INTEGER_SIZE);
        APP_CONFIGURATION_MAP.put("VALUE_FRACTION_SIZE", AppConfig.VALUE_FRACTION_SIZE);

        //-----------------------------------------------------------------
        // Goals boundaries
        //-----------------------------------------------------------------
        APP_CONFIGURATION_MAP.put("MIN_ALLOWED_GOALS", AppConfig.MIN_ALLOWED_GOALS);
        APP_CONFIGURATION_MAP.put("MAX_ALLOWED_GOALS", AppConfig.MAX_ALLOWED_GOALS);

    }

    @GET
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(FETCH_CONFIG)
    public Response isUnique() throws UserException {

        return Responses.responseWithBrowserCaching(Response.Status.OK, APP_CONFIGURATION_MAP);
    }

}