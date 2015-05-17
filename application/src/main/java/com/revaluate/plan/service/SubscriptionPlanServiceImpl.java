package com.revaluate.plan.service;

import com.revaluate.domain.subscription.SubscriptionPlanDTO;
import com.revaluate.plan.persistence.SubscriptionPlan;
import com.revaluate.plan.persistence.SubscriptionPlanRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    @Autowired
    private SubscriptionPlanRepository subscriptionPlanRepository;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public List<SubscriptionPlanDTO> findAllSubscriptionPlans() {
        List<SubscriptionPlan> subscriptionPlans = subscriptionPlanRepository.findAll();

        return subscriptionPlans
                .stream()
                .map(subscriptionPlan -> dozerBeanMapper.map(subscriptionPlan, SubscriptionPlanDTO.class))
                .collect(Collectors.toList());
    }
}