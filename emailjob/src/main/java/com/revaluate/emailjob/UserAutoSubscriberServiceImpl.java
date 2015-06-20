package com.revaluate.emailjob;

import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.domain.account.UserSubscriptionStatus;
import com.revaluate.payment.exception.PaymentStatusException;
import com.revaluate.payment.service.PaymentStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UserAutoSubscriberServiceImpl implements UserAutoSubscriberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserAutoSubscriberServiceImpl.class);

    @Autowired
    private PaymentStatusService paymentStatusService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void autoValidate(User user) throws PaymentStatusException {
        if (paymentStatusService.isPaymentStatusDefined(user.getId())) {
            LOGGER.info(String.format("Try to auto-subscribe user %s to standard plan", user));

            paymentStatusService.subscribeToStandardPlan(user.getId());

            //-----------------------------------------------------------------
            // Set user as having the subscription status active
            //-----------------------------------------------------------------
            user.setUserSubscriptionStatus(UserSubscriptionStatus.ACTIVE);
            userRepository.save(user);
        }
    }
}