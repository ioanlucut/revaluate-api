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
    private static final String FETCH_CUSTOMER_TOKEN = "fetchCustomerToken";
    private static final String CREATE_PAYMENT_STATUS = "createCustomerWithPaymentMethod";
    private static final String FETCH_PAYMENT_INSIGHTS = "fetchPaymentInsights";
    private static final String SUBSCRIBE_TO_STANDARD_PLAN = "subscribeToStandardPlan";

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentStatusService paymentStatusService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(FETCH_CUSTOMER_TOKEN)
    public String fetchCustomerToken() throws PaymentStatusException, PaymentException {
        PaymentStatusDTO paymentStatus = paymentStatusService.findPaymentStatus(getCurrentUserId());

        return paymentService.fetchToken(paymentStatus.getCustomerId());
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(FETCH_TOKEN)
    public String fetchToken() throws PaymentStatusException, PaymentException {

        return paymentService.fetchToken();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(CREATE_PAYMENT_STATUS)
    public Response createPaymentDetails(@NotNull @Valid PaymentDetailsDTO paymentDetailsDTO) throws PaymentStatusException {
        PaymentStatusDTO paymentStatus = paymentStatusService.createPaymentStatus(paymentDetailsDTO, getCurrentUserId());
        PaymentInsightsDTO paymentInsightsDTO = paymentStatusService.fetchPaymentInsights(paymentStatus.getCustomerId());

        return Responses.respond(Response.Status.OK, paymentInsightsDTO);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(SUBSCRIBE_TO_STANDARD_PLAN)
    public Response subscribeToStandardPlan(@NotNull @Valid PaymentDetailsDTO paymentDetailsDTO) throws PaymentStatusException {
        PaymentInsightsDTO paymentInsightsDTO = paymentStatusService.subscribeToStandardPlan(getCurrentUserId());

        return Responses.respond(Response.Status.OK, paymentInsightsDTO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(FETCH_PAYMENT_INSIGHTS)
    public Response fetchPaymentInsights() throws PaymentStatusException {
        PaymentStatusDTO paymentStatus = paymentStatusService.findPaymentStatus(getCurrentUserId());
        PaymentInsightsDTO paymentInsightsDTO = paymentStatusService.fetchPaymentInsights(paymentStatus.getCustomerId());

        return Responses.respond(Response.Status.OK, paymentInsightsDTO);
    }
}