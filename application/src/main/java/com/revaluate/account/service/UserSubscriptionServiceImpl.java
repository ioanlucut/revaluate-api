package com.revaluate.account.service;

import com.revaluate.account.persistence.User;
import com.revaluate.domain.account.UserSubscriptionStatus;
import org.joda.time.LocalDateTime;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class UserSubscriptionServiceImpl implements UserSubscriptionService {

    public static final int TRIAL_DAYS = 15;

    @Override
    public boolean isUserTrialPeriodExpired(User foundUser) {
        if (foundUser.getUserSubscriptionStatus() == UserSubscriptionStatus.TRIAL) {
            LocalDateTime createdDate = foundUser.getCreatedDate();
            LocalDateTime endTrialDate = createdDate.plusDays(TRIAL_DAYS);

            return LocalDateTime.now().isAfter(endTrialDate);
        }
        return Boolean.FALSE;
    }
}