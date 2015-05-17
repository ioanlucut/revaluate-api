package com.revaluate.user_subscription.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.domain.user_subscription.UserSubscriptionPlanDTO;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.user_subscription.exception.UserSubscriptionPlanException;
import com.revaluate.user_subscription.persistence.UserSubscriptionPlanRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserSubscriptionPlanServiceImplTestIT extends AbstractIntegrationTests {

    @Autowired
    private UserSubscriptionPlanService userSubscriptionPlanService;

    @Autowired
    private UserSubscriptionPlanRepository userSubscriptionPlanRepository;

    @Test
    public void findOneByUserId_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        exception.expect(UserSubscriptionPlanException.class);
        UserSubscriptionPlanDTO oneByUserId = userSubscriptionPlanService.findOneByUserId(createdUserDTO.getId());
    }
}