package com.revaluate.resource.payment;

import com.fasterxml.jackson.annotation.JsonView;
import com.revaluate.account.exception.UserException;
import com.revaluate.account.service.UserService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.payment.*;
import com.revaluate.domain.payment.insights.PaymentInsightsDTO;
import com.revaluate.payment.exception.PaymentStatusException;
import com.revaluate.payment.service.PaymentStatusService;
import com.revaluate.resource.utils.Resource;
import com.revaluate.resource.utils.Responses;
import com.revaluate.views.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

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
    private static final String CREATE_PAYMENT_STATUS_SUBSCRIBE_TO_STANDARD_PLAN = "createCustomerWithPaymentMethodSubscribeToStandardPlan";
    private static final String FETCH_PAYMENT_INSIGHTS = "fetchPaymentInsights";
    private static final String FETCH_PAYMENT_STATUS = "fetchPaymentStatus";
    private static final String IS_PAYMENT_STATUS_DEFINED = "isPaymentStatusDefined";
    private static final String UPDATE_CUSTOMER = "updateCustomer";
    private static final String UPDATE_PAYMENT_METHOD = "updatePaymentMethod";
    private static final String REMOVE_PAYMENT_METHOD = "removePaymentMethod";

    //-----------------------------------------------------------------
    // Json keys
    //-----------------------------------------------------------------
    public static final String PAYMENT_STATUS_DEFINED = "paymentStatusDefined";

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentStatusService paymentStatusService;

    @Autowired
    private UserService userService;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(FETCH_CUSTOMER_TOKEN)
    public Response fetchCustomerToken() throws PaymentStatusException, PaymentException {
        PaymentStatusDTO paymentStatus = paymentStatusService.findPaymentStatus(getCurrentUserId());
        PaymentTokenDTO paymentTokenDTO = new PaymentTokenDTOBuilder().withClientToken(paymentService.fetchToken(paymentStatus.getCustomerId())).build();

        return Responses.respond(Response.Status.OK, paymentTokenDTO);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(FETCH_TOKEN)
    public Response fetchToken() throws PaymentStatusException, PaymentException {
        PaymentTokenDTO paymentTokenDTO = new PaymentTokenDTOBuilder().withClientToken(paymentService.fetchToken()).build();

        return Responses.respond(Response.Status.OK, paymentTokenDTO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(IS_PAYMENT_STATUS_DEFINED)
    public Response isPaymentStatusDefined() throws PaymentStatusException {
        boolean paymentStatusDefined = paymentStatusService.isPaymentStatusDefined(getCurrentUserId());

        Map<String, Boolean> response = new HashMap<>();
        response.put(PAYMENT_STATUS_DEFINED, paymentStatusDefined);
        return Responses.respond(Response.Status.OK, response);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(CREATE_PAYMENT_STATUS_SUBSCRIBE_TO_STANDARD_PLAN)
    public Response createPaymentDetailsAndSubscribeToStandardPlanIfUserEligible(@NotNull @Valid PaymentDetailsDTO paymentDetailsDTO) throws PaymentStatusException {
        PaymentInsightsDTO paymentInsightsDTO = paymentStatusService.createPaymentStatusAndTryToSubscribeToStandardPlan(paymentDetailsDTO, getCurrentUserId());

        return Responses.respond(Response.Status.OK, paymentInsightsDTO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(FETCH_PAYMENT_STATUS)
    public Response fetchPaymentStatus() throws PaymentStatusException {
        PaymentStatusDTO paymentStatus = paymentStatusService.findPaymentStatus(getCurrentUserId());

        return Responses.respond(Response.Status.OK, paymentStatus);
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

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(UPDATE_CUSTOMER)
    public Response updateCustomer(@NotNull @Valid PaymentCustomerDetailsDTO paymentCustomerDetailsDTO) throws PaymentStatusException {
        PaymentStatusDTO updatedPaymentStatus = paymentStatusService.updateCustomer(paymentCustomerDetailsDTO, getCurrentUserId());
        PaymentInsightsDTO paymentInsightsDTO = paymentStatusService.fetchPaymentInsights(updatedPaymentStatus.getCustomerId());

        return Responses.respond(Response.Status.OK, paymentInsightsDTO);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(UPDATE_PAYMENT_METHOD)
    public Response updatePaymentMethod(@NotNull @Valid PaymentNonceDetailsDTO paymentNonceDetailsDTO) throws PaymentStatusException {
        PaymentStatusDTO updatedPaymentStatus = paymentStatusService.updatePaymentMethod(paymentNonceDetailsDTO, getCurrentUserId());
        PaymentInsightsDTO paymentInsightsDTO = paymentStatusService.fetchPaymentInsights(updatedPaymentStatus.getCustomerId());

        return Responses.respond(Response.Status.OK, paymentInsightsDTO);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path(REMOVE_PAYMENT_METHOD)
    @JsonView({Views.DetailsView.class})
    public Response removePaymentMethod() throws PaymentStatusException, UserException {
        paymentStatusService.removePaymentMethod(getCurrentUserId());
        UserDTO userDetailsDTO = userService.getUserDetails(getCurrentUserId());

        return Responses.respond(Response.Status.OK, userDetailsDTO);
    }
}