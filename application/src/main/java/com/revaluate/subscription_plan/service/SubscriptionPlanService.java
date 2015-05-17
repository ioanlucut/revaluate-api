package com.revaluate.subscription_plan.service;

import com.revaluate.domain.subscription_plan.SubscriptionPlanDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface SubscriptionPlanService {

    @NotNull
    List<SubscriptionPlanDTO> findAllSubscriptionPlans();
}