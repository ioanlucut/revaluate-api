package com.revaluate.user_subscription.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.subscription_plan.SubscriptionPlanDTO;
import com.revaluate.domain.subscription_plan.SubscriptionPlanDTOBuilder;
import com.revaluate.domain.user_subscription.UserSubscriptionPlanDTO;
import com.revaluate.domain.user_subscription.UserSubscriptionPlanDTOBuilder;
import com.revaluate.subscription_plan.service.SubscriptionPlanService;
import com.revaluate.user_subscription.exception.UserSubscriptionPlanException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class UserSubscriptionPlanServiceImplTestIT extends AbstractIntegrationTests {

    @Autowired
    private UserSubscriptionPlanService userSubscriptionPlanService;

    @Autowired
    private SubscriptionPlanService subscriptionPlanService;

    @Test
    public void findOneByUserId_inexisting_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        exception.expect(UserSubscriptionPlanException.class);
        userSubscriptionPlanService.findOneByUserId(createdUserDTO.getId());
    }

    @Test
    public void findOneByUserId_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        exception.expect(UserSubscriptionPlanException.class);
        userSubscriptionPlanService.findOneByUserId(createdUserDTO.getId());
    }

    @Test
    public void create_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create subscriptionPlan
        //-----------------------------------------------------------------
        List<SubscriptionPlanDTO> allSubscriptionPlans = subscriptionPlanService.findAllSubscriptionPlans();
        assertThat(allSubscriptionPlans.size(), is(greaterThan(0)));
        SubscriptionPlanDTO subscriptionPlanDTO = allSubscriptionPlans.get(0);

        //-----------------------------------------------------------------
        // Create userSubscriptionPlan
        //-----------------------------------------------------------------
        UserSubscriptionPlanDTO userSubscriptionPlanDTO = new UserSubscriptionPlanDTOBuilder().withSubscriptionPlanDTO(subscriptionPlanDTO).build();

        //-----------------------------------------------------------------
        // Create
        //-----------------------------------------------------------------
        UserSubscriptionPlanDTO createdUserSubscriptionPlanDTO = userSubscriptionPlanService.create(userSubscriptionPlanDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert created expense is ok
        //-----------------------------------------------------------------
        assertThat(createdUserSubscriptionPlanDTO, is(notNullValue()));
        assertThat(createdUserSubscriptionPlanDTO.getId(), not(equalTo(0)));
        assertThat(createdUserSubscriptionPlanDTO.getSubscriptionPlanDTO(), equalTo(subscriptionPlanDTO));
    }

    @Test
    public void create_validDetailsTwice_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create subscriptionPlan
        //-----------------------------------------------------------------
        List<SubscriptionPlanDTO> allSubscriptionPlans = subscriptionPlanService.findAllSubscriptionPlans();
        assertThat(allSubscriptionPlans.size(), is(greaterThan(0)));
        SubscriptionPlanDTO subscriptionPlanDTO = allSubscriptionPlans.get(0);

        //-----------------------------------------------------------------
        // Create userSubscriptionPlan
        //-----------------------------------------------------------------
        UserSubscriptionPlanDTO userSubscriptionPlanDTO = new UserSubscriptionPlanDTOBuilder().withSubscriptionPlanDTO(subscriptionPlanDTO).build();

        //-----------------------------------------------------------------
        // Create second time should throw exception as user_id is unique
        //-----------------------------------------------------------------
        userSubscriptionPlanService.create(userSubscriptionPlanDTO, createdUserDTO.getId());

        exception.expect(JpaSystemException.class);
        userSubscriptionPlanService.create(userSubscriptionPlanDTO, createdUserDTO.getId());
    }

    @Test
    public void create_invalidSubscriptionPlan_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create subscriptionPlan which is not ok
        //-----------------------------------------------------------------
        SubscriptionPlanDTO subscriptionPlanDTO = new SubscriptionPlanDTOBuilder().build();

        //-----------------------------------------------------------------
        // Create userSubscriptionPlan
        //-----------------------------------------------------------------
        UserSubscriptionPlanDTO userSubscriptionPlanDTO = new UserSubscriptionPlanDTOBuilder().withSubscriptionPlanDTO(subscriptionPlanDTO).build();

        //-----------------------------------------------------------------
        // Create should throw exception due to invalid subscription plan
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        userSubscriptionPlanService.create(userSubscriptionPlanDTO, createdUserDTO.getId());
    }

    @Test
    public void create_notInvalidSubscriptionPlanButWrongId_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create subscriptionPlan which is not ok
        //-----------------------------------------------------------------
        List<SubscriptionPlanDTO> allSubscriptionPlans = subscriptionPlanService.findAllSubscriptionPlans();
        assertThat(allSubscriptionPlans.size(), is(greaterThan(0)));
        SubscriptionPlanDTO subscriptionPlanDTO = allSubscriptionPlans.get(0);
        subscriptionPlanDTO.setId(9999);

        //-----------------------------------------------------------------
        // Create userSubscriptionPlan
        //-----------------------------------------------------------------
        UserSubscriptionPlanDTO userSubscriptionPlanDTO = new UserSubscriptionPlanDTOBuilder().withSubscriptionPlanDTO(subscriptionPlanDTO).build();

        //-----------------------------------------------------------------
        // Create should throw exception due to invalid subscription plan
        //-----------------------------------------------------------------
        exception.expect(IllegalArgumentException.class);
        userSubscriptionPlanService.create(userSubscriptionPlanDTO, createdUserDTO.getId());
    }

    @Test
    public void create_invalidDetails_handledCorrectly() throws Exception {
        exception.expect(ConstraintViolationException.class);
        userSubscriptionPlanService.create(null, 0);
    }

}