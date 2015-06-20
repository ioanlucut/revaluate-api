package com.revaluate.paymentjob;

import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.domain.account.UserSubscriptionStatus;
import com.revaluate.payment.exception.PaymentStatusException;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserSubscriptionJobService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserSubscriptionJobService.class);
    public static final int AUTO_SUBSCRIBE_USERS_DELAY = 60000;

    @Autowired
    private UserAutoSubscriberService userAutoSubscriberService;

    @Autowired
    private UserRepository userRepository;

    @Scheduled(fixedDelay = AUTO_SUBSCRIBE_USERS_DELAY)
    public void activateSubscriptionForUserWithPaymentMethod() {
        LOGGER.info(String.format("Start batch job %s :", this.getClass().getSimpleName()));

        List<User> potentialUsersToBeAutoSubscribed = userRepository.findAllByUserSubscriptionStatusAndEndTrialDateBefore(UserSubscriptionStatus.TRIAL_EXPIRED, LocalDateTime.now());
        LOGGER.info(String.format("Fetched potential users: %s :", potentialUsersToBeAutoSubscribed));

        //-----------------------------------------------------------------
        // Try to auto subscribe users
        //-----------------------------------------------------------------
        potentialUsersToBeAutoSubscribed
                .stream()
                .forEach(user -> {
                    try {
                        userAutoSubscriberService.autoValidate(user);
                    } catch (PaymentStatusException ex) {

                        LOGGER.error(ex.getMessage(), ex);
                    }
                });

        LOGGER.info(String.format("End batch job %s :", this.getClass().getSimpleName()));
    }
}
