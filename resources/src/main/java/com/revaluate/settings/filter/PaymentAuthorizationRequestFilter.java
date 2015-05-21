package com.revaluate.settings.filter;

import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.account.service.UserSubscriptionService;
import com.revaluate.core.annotations.PaymentRequired;
import com.revaluate.domain.account.UserSubscriptionStatus;
import com.revaluate.resource.utils.Responses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Component
@Priority(Priorities.USER)
public class PaymentAuthorizationRequestFilter implements ContainerRequestFilter {

    public static final String OPTIONS = "OPTIONS";

    @Context
    private ResourceInfo resourceInfo;

    private UserRepository userRepository;

    private UserSubscriptionService userSubscriptionService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setUserSubscriptionService(UserSubscriptionService userSubscriptionService) {
        this.userSubscriptionService = userSubscriptionService;
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (isOptionsHttpMethod(requestContext) || !isPaymentRequired()) {

            return;
        }

        User foundUser = userRepository.findOne((Integer) requestContext.getProperty(AuthorizationRequestFilter.USER_ID));
        if (foundUser == null || foundUser.getUserSubscriptionStatus() == UserSubscriptionStatus.ACTIVE) {

            return;
        }

        boolean isUserTrialPeriodExpired = userSubscriptionService.isUserTrialPeriodExpired(foundUser);

        //-----------------------------------------------------------------
        // If user trial period is expired, set status as trial expired
        //-----------------------------------------------------------------
        if (isUserTrialPeriodExpired) {
            foundUser.setUserSubscriptionStatus(UserSubscriptionStatus.TRIAL_EXPIRED);
            userRepository.save(foundUser);
        }

        //-----------------------------------------------------------------
        // If user is in TRIAL EXPIRED, abort
        //-----------------------------------------------------------------
        if (foundUser.getUserSubscriptionStatus() == UserSubscriptionStatus.TRIAL_EXPIRED) {
            abort(requestContext);
        }
    }

    private boolean isOptionsHttpMethod(ContainerRequestContext requestContext) {
        return OPTIONS.equals(requestContext.getMethod());
    }

    public boolean isPaymentRequired() {
        return resourceInfo.getResourceMethod().isAnnotationPresent(PaymentRequired.class);
    }

    public void abort(ContainerRequestContext requestContext) {
        requestContext.abortWith(Responses.respond(Response.Status.PAYMENT_REQUIRED, "Payment required."));
    }
}

