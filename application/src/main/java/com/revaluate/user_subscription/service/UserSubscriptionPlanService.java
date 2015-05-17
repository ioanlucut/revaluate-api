package com.revaluate.user_subscription.service;

import com.revaluate.domain.user_subscription.UserSubscriptionPlanDTO;
import com.revaluate.user_subscription.exception.UserSubscriptionPlanException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface UserSubscriptionPlanService {

    @NotNull
    UserSubscriptionPlanDTO findOneByUserId(int userId) throws UserSubscriptionPlanException;

    @NotNull
    UserSubscriptionPlanDTO create(@NotNull @Valid UserSubscriptionPlanDTO userSubscriptionPlanDTO, int userId) throws UserSubscriptionPlanException;

    @NotNull
    UserSubscriptionPlanDTO update(@NotNull @Valid UserSubscriptionPlanDTO userSubscriptionPlanDTO, int userId) throws UserSubscriptionPlanException;
}