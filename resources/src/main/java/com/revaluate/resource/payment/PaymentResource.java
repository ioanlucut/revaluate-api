package com.revaluate.resource.payment;

import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

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
    private static final String FETCH_TOKEN = "fetchToken/{customerId}";
    private static final String PERFORM_PAYMENT = "performPayment/{customerId}/{paymentNonce}";

    //-----------------------------------------------------------------
    // Path params
    //-----------------------------------------------------------------
    private static final String CUSTOMER_ID = "customerId";
    private static final String PAYMENT_NONCE = "paymentNonce";

    @Autowired
    private PaymentService paymentService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(FETCH_TOKEN)
    public Response fetchToken(@PathParam(CUSTOMER_ID) @NotEmpty String customerId) throws PaymentException {
        String token = paymentService.fetchToken(customerId);

        return Responses.respond(Response.Status.OK, token);
    }

    /*@POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(PERFORM_PAYMENT)
    public Response performPayment(@PathParam(CUSTOMER_ID) @NotEmpty String customerId, @PathParam(PAYMENT_NONCE) @NotEmpty String paymentNonce) throws PaymentException {
        Result<Transaction> pay = paymentService.pay(BigDecimal.ONE, customerId, paymentNonce);
        if (!pay.isSuccess()) {

            throw new PaymentException("Error while trying to perform payment.");
        }

        return Responses.respond(Response.Status.OK, pay.getMessage());
    }*/

}