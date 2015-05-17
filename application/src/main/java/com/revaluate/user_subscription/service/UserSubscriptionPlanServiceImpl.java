package com.revaluate.user_subscription.service;

import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.domain.user_subscription.UserSubscriptionPlanDTO;
import com.revaluate.user_subscription.exception.UserSubscriptionPlanException;
import com.revaluate.user_subscription.persistence.UserSubscriptionPlan;
import com.revaluate.user_subscription.persistence.UserSubscriptionPlanRepository;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
public class UserSubscriptionPlanServiceImpl implements UserSubscriptionPlanService {

    @Autowired
    private UserSubscriptionPlanRepository userSubscriptionPlanRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public UserSubscriptionPlanDTO findOneByUserId(int userId) throws UserSubscriptionPlanException {
        Optional<UserSubscriptionPlan> byCurrencyCode = userSubscriptionPlanRepository.findOneByUserId(userId);

        return dozerBeanMapper.map(byCurrencyCode.orElseThrow(() -> new UserSubscriptionPlanException("There is no subscription_plan plan for this user")), UserSubscriptionPlanDTO.class);
    }

    @Override
    public UserSubscriptionPlanDTO create(UserSubscriptionPlanDTO userSubscriptionPlanDTO, int userId) throws UserSubscriptionPlanException {
        UserSubscriptionPlan userSubscriptionPlan = dozerBeanMapper.map(userSubscriptionPlanDTO, UserSubscriptionPlan.class);

        User foundUser = userRepository.findOne(userId);
        userSubscriptionPlan.setUser(foundUser);
        UserSubscriptionPlan savedUserSubscriptionPlan = userSubscriptionPlanRepository.save(userSubscriptionPlan);

        return dozerBeanMapper.map(savedUserSubscriptionPlan, UserSubscriptionPlanDTO.class);
    }

}
