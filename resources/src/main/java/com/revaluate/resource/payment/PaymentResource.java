package com.revaluate.resource.payment;

import com.revaluate.domain.payment.PaymentDetailsDTO;
import com.revaluate.domain.payment.PaymentStatusDTO;
import com.revaluate.domain.payment.insights.PaymentInsightsDTO;
import com.revaluate.payment.exception.PaymentStatusException;
import com.revaluate.payment.service.PaymentStatusService;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
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
    private static final String FETCH_TOKEN = "fetchToken";
    private static final String CREATE_PAYMENT_STATUS = "createPaymentStatus";
    private static final String FETCH_PAYMENT_INSIGHTS = "fetchPaymentInsights";

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentStatusService paymentStatusService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(FETCH_TOKEN)
    public Response fetchToken() throws PaymentStatusException, PaymentException {
        PaymentStatusDTO paymentStatus = paymentStatusService.findOneByUserId(getCurrentUserId());

        return Responses.respond(Response.Status.OK, paymentService.fetchToken(paymentStatus.getCustomerId()));
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(CREATE_PAYMENT_STATUS)
    public Response create(@NotNull @Valid PaymentDetailsDTO paymentDetailsDTO) throws PaymentStatusException {
        PaymentStatusDTO paymentStatus = paymentStatusService.createPaymentStatus(paymentDetailsDTO, getCurrentUserId());
        PaymentInsightsDTO paymentInsightsDTO = paymentStatusService.fetchPaymentInsightsFor(paymentStatus.getCustomerId());

        return Responses.respond(Response.Status.OK, paymentInsightsDTO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(FETCH_PAYMENT_INSIGHTS)
    public Response fetchPaymentInsights() throws PaymentStatusException {
        PaymentStatusDTO paymentStatus = paymentStatusService.findOneByUserId(getCurrentUserId());
        PaymentInsightsDTO paymentInsightsDTO = paymentStatusService.fetchPaymentInsightsFor(paymentStatus.getCustomerId());

        return Responses.respond(Response.Status.OK, paymentInsightsDTO);
    }
}