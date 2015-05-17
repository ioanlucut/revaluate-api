package com.revaluate.subscription_plan.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.domain.subscription_plan.SubscriptionType;
import com.revaluate.subscription_plan.persistence.SubscriptionPlan;
import com.revaluate.subscription_plan.persistence.SubscriptionPlanRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class SubscriptionPlanServiceImplTestIT extends AbstractIntegrationTests {

    @Autowired
    private SubscriptionPlanRepository subscriptionPlanRepository;

    @Test
    public void findAll_validDetails_ok() throws Exception {
        List<SubscriptionPlan> allExistingSubscriptionPlans = subscriptionPlanRepository.findAll();

        //-----------------------------------------------------------------
        // Assertions
        //-----------------------------------------------------------------
        assertThat(allExistingSubscriptionPlans, is(notNullValue()));
        assertThat(allExistingSubscriptionPlans.size(), equalTo(1));
        assertThat(allExistingSubscriptionPlans.get(0).getId(), is(equalTo(1)));
        assertThat(allExistingSubscriptionPlans.get(0).getDescription(), is(equalTo("Basic subscription plan")));
        assertThat(allExistingSubscriptionPlans.get(0).getSubscriptionType(), is(equalTo(SubscriptionType.STANDARD)));
        assertThat(allExistingSubscriptionPlans.get(0).getValue().doubleValue(), is(equalTo(5.0)));
    }
}