package com.revaluate.plan.persistence;

import com.revaluate.domain.subscription.SubscriptionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Integer> {

    Optional<SubscriptionPlan> findOneBySubscriptionType(SubscriptionType subscriptionType);

    Optional<SubscriptionPlan> findOneById(int colorId);
}