package com.revaluate.user_subscription.persistence;

import com.revaluate.domain.subscription_plan.SubscriptionType;
import com.revaluate.subscription_plan.persistence.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserSubscriptionPlanRepository extends JpaRepository<UserSubscriptionPlan, Integer> {

    List<UserSubscriptionPlan> findAllBySubscriptionPlan(SubscriptionPlan subscriptionPlan);

    List<UserSubscriptionPlan> findAllBySubscriptionPlanSubscriptionType(SubscriptionType subscriptionType);

    Optional<UserSubscriptionPlan> findOneByUserId(int userId);

    @Modifying
    @Transactional
    @Query("delete from UserSubscriptionPlan u where u.user.id = ?1")
    void removeByUserId(int userId);
}

