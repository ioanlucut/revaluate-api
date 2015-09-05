package com.revaluate.goals.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.goal.GoalDTO;
import com.revaluate.domain.goal.GoalDTOBuilder;
import com.revaluate.domain.goal.GoalTarget;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class GoalServiceImplTest_isUniqueCategory_IT extends AbstractIntegrationTests {

    @Autowired
    private GoalService goalService;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void isUniqueGoalWithCategoryBetween_shouldWork_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        categoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create two goals
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(9999.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(categoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();

        //-----------------------------------------------------------------
        // Is unique
        //-----------------------------------------------------------------
        assertThat(goalService.isUniqueGoalWithCategoryBetween(createdUserDTO.getId(), categoryDTO.getId(), goalDTO.getStartDate().minusSeconds(1), goalDTO.getEndDate().plusSeconds(1)), is(true));

        //-----------------------------------------------------------------
        // Create the goal
        //-----------------------------------------------------------------
        goalService.create(goalDTO, createdUserDTO.getId());

        // Is not unique
        //-----------------------------------------------------------------
        assertThat(goalService.isUniqueGoalWithCategoryBetween(createdUserDTO.getId(), categoryDTO.getId(), goalDTO.getStartDate().minusSeconds(1), goalDTO.getEndDate().plusSeconds(1)), is(false));
    }

}