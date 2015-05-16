package com.revaluate.resource.payment;

import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path(PaymentResource.PAYMENT)
@Component
public class PaymentResource extends Resource {

    //-----------------------------------------------------------------
    // Path
    //-----------------------------------------------------------------
    public static final String PAYMENT = "payment";

    //-----------------------------------------------------------------
    // Sub paths
    //-----------------------------------------------------------------
    private static final String FETCH_TOKEN = "{customerId}";

    //-----------------------------------------------------------------
    // Path params
    //-----------------------------------------------------------------
    private static final String CUSTOMER_ID = "customerId";

    @Autowired
    private PaymentService paymentService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(FETCH_TOKEN)
    public Response create(@PathParam(CUSTOMER_ID) @NotNull String customerId) throws PaymentException {
        String token = paymentService.fetchToken(customerId);

        return Responses.respond(Response.Status.OK, token);
    }

}