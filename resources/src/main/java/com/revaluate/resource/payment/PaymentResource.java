package com.revaluate.resource.payment;

import com.revaluate.domain.payment.PaymentDetailsDTO;
import com.revaluate.domain.payment.PaymentStatusDTO;
import com.revaluate.payment.exception.PaymentStatusException;
import com.revaluate.payment.service.PaymentStatusService;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
    private static final String FETCH_TOKEN = "fetchToken";
    private static final String CREATE_PAYMENT_STATUS = "createPaymentStatus";

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentStatusService paymentStatusService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(FETCH_TOKEN)
    public Response fetchToken() throws PaymentStatusException {
        PaymentStatusDTO paymentStatus = paymentStatusService.findOneByUserId(getCurrentUserId());

        try {
            return Responses.respond(Response.Status.OK, paymentService.fetchToken(paymentStatus.getCustomerId()));
        } catch (PaymentException ex) {

            throw new PaymentStatusException(ex);
        }
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(CREATE_PAYMENT_STATUS)
    public Response create(@Valid PaymentDetailsDTO paymentDetailsDTO) throws PaymentStatusException {
        PaymentStatusDTO paymentStatus = paymentStatusService.createPaymentStatus(paymentDetailsDTO, getCurrentUserId());

        return Responses.respond(Response.Status.OK, "Payment status successful created!");
    }
}