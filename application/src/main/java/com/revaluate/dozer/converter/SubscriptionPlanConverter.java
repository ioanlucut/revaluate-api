package com.revaluate.dozer.converter;

import com.revaluate.domain.subscription_plan.SubscriptionPlanDTO;
import com.revaluate.domain.subscription_plan.SubscriptionPlanDTOBuilder;
import com.revaluate.subscription_plan.persistence.SubscriptionPlan;
import com.revaluate.subscription_plan.persistence.SubscriptionPlanRepository;
import org.dozer.DozerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SubscriptionPlanConverter extends DozerConverter<SubscriptionPlanDTO, SubscriptionPlan> {

    @Autowired
    private SubscriptionPlanRepository subscriptionPlanRepository;

    public SubscriptionPlanConverter() {
        super(SubscriptionPlanDTO.class, SubscriptionPlan.class);
    }

    public SubscriptionPlan convertTo(SubscriptionPlanDTO source, SubscriptionPlan destination) {
        Optional<SubscriptionPlan> bySubscriptionPlan = subscriptionPlanRepository.findOneById(source.getId());

        return bySubscriptionPlan.orElseThrow(() -> new IllegalArgumentException("The given subscription plan does not exists"));
    }

    @Override
    public SubscriptionPlanDTO convertFrom(SubscriptionPlan source, SubscriptionPlanDTO destination) {

        return new SubscriptionPlanDTOBuilder()
                .withId(source.getId())
                .withDescription(source.getDescription())
                .withValue(source.getValue().doubleValue())
                .withSubscriptionType(source.getSubscriptionType())
                .build();
    }

}