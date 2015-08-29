package com.revaluate.goals.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.domain.goal.GoalDTO;
import com.revaluate.domain.goal.GoalDTOBuilder;
import com.revaluate.domain.goal.GoalStatusDTO;
import com.revaluate.domain.goal.GoalTarget;
import com.revaluate.goals.persistence.Goal;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class GoalStatusServiceImplTest_IT extends AbstractIntegrationTests {

    @Autowired
    private GoalService goalService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private GoalStatusService goalStatusService;

    //-----------------------------------------------------------------
    // Find all goals work
    //-----------------------------------------------------------------
    @Test
    public void findAll_ofExistingUser_ok() throws Exception {

        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category 1
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense 1
        //-----------------------------------------------------------------
        LocalDateTime after = LocalDateTime.now().minusSeconds(1);
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(1000).withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        ExpenseDTO expenseDTOB = new ExpenseDTOBuilder().withValue(2000).withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTOB, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create category 2
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("name2").build();
        CategoryDTO secondCreatedCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense 2
        //-----------------------------------------------------------------
        expenseDTO = new ExpenseDTOBuilder().withValue(40000).withCategory(secondCreatedCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTOB = new ExpenseDTOBuilder().withValue(60000).withCategory(secondCreatedCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTOB, createdUserDTO.getId());
        LocalDateTime before = LocalDateTime.now().plusMinutes(1);

        //-----------------------------------------------------------------
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(3100)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(after)
                .withEndDate(before)
                .build();

        goalService.create(goalDTO, createdUserDTO.getId());

        List<Goal> allByUserId = goalRepository.findAllByUserId(userDTO.getId());
        GoalStatusDTO goalStatusDTO = goalStatusService.computeGoalStatusFor(userDTO.getId(), allByUserId.stream().findAny().get());

        //-----------------------------------------------------------------
        // Find created expenses + asserts
        //-----------------------------------------------------------------
        assertThat(goalStatusDTO, is(notNullValue()));
        assertThat(goalStatusDTO.getCurrentValue(), is(3000.0));
        assertThat(goalStatusDTO.getInsightsDaily(), is(notNullValue()));
        assertThat(goalStatusDTO.getInsightsDaily().getTotalPerDayDTOs(), is(notNullValue()));
        assertThat(goalStatusDTO.getInsightsDaily().getTotalPerDayDTOs().size(), is(greaterThan(0)));
        assertThat(goalStatusDTO.isGoalAccomplished(), is(Boolean.FALSE));
    }

}