package com.revaluate.plan.service;

import com.revaluate.domain.subscription.SubscriptionPlanDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface SubscriptionPlanService {

    @NotNull
    List<SubscriptionPlanDTO> findAllSubscriptionPlans();
}