package com.revaluate.account.service;

import com.revaluate.account.persistence.User;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface UserSubscriptionService {

    boolean isUserTrialPeriodExpired(@NotNull @Valid User user);
}