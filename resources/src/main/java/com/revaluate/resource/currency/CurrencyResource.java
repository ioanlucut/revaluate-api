package com.revaluate.resource.currency;

import com.revaluate.core.annotations.Public;
import com.revaluate.currency.exception.CurrencyException;
import com.revaluate.currency.service.CurrencyService;
import com.revaluate.domain.currency.CurrencyDTO;
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

@Path(CurrencyResource.CURRENCY)
@Component
public class CurrencyResource extends Resource {

    public static final String CURRENCY = "currency";
    private static final String LIST_ALL_CURRENCIES = "list";

    @Autowired
    private CurrencyService currencyService;

    @GET
    @Public
    @Produces(MediaType.APPLICATION_JSON)
    @Path(LIST_ALL_CURRENCIES)
    public Response isUnique() throws CurrencyException {
        List<CurrencyDTO> allCurrencies = currencyService.findAllCurrencies();

        return Responses.respond(Response.Status.OK, allCurrencies);
    }
}