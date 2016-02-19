package com.revaluate.goals.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.goal.GoalDTO;
import com.revaluate.domain.goal.GoalDTOBuilder;
import com.revaluate.domain.goal.GoalTarget;
import com.revaluate.goals.exception.GoalException;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

public class GoalServiceImplTest_update_IT extends AbstractIntegrationTests {

    @Autowired
    private GoalService goalService;

    @Autowired
    private CategoryService categoryService;

    //-----------------------------------------------------------------
    // Test update part
    //-----------------------------------------------------------------
    @Test
    public void update_value_ok() throws Exception {
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
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(2.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(categoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        GoalDTO createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        goalDTO.setId(createdGoalDTO.getId());

        //-----------------------------------------------------------------
        // Goal to update - ONLY VALUE
        //-----------------------------------------------------------------
        GoalDTO goalDTOToUpdate = new GoalDTOBuilder()
                .withId(goalDTO.getId())
                .withValue(2.56)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(categoryDTO)
                .withStartDate(goalDTO.getStartDate())
                .withEndDate(goalDTO.getEndDate())
                .build();

        GoalDTO updatedGoalDTO = goalService.update(goalDTOToUpdate, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert updated goal is ok, and updated only concerned values
        //-----------------------------------------------------------------
        assertThat(updatedGoalDTO.getId()).isEqualTo(createdGoalDTO.getId());
        assertThat(createdGoalDTO.getGoalStatusDTO()).isNotNull();
        assertThat(updatedGoalDTO.getCategory().getColor()).isEqualTo(createdGoalDTO.getCategory().getColor());
        assertThat(updatedGoalDTO.getCategory().getName()).isEqualTo(createdGoalDTO.getCategory().getName());
        assertThat(updatedGoalDTO.getCategory().getId()).isEqualTo(createdGoalDTO.getCategory().getId());
        // Only this was updated
        assertThat(updatedGoalDTO.getValue()).isEqualTo(goalDTOToUpdate.getValue());
    }

    @Test
    public void update_goalTarget_ok() throws Exception {
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
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(2.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(categoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        GoalDTO createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        goalDTO.setId(createdGoalDTO.getId());

        //-----------------------------------------------------------------
        // Goal to update - ONLY description
        //-----------------------------------------------------------------
        GoalDTO goalDTOToUpdate = new GoalDTOBuilder()
                .withId(goalDTO.getId())
                .withValue(2.55)
                .withGoalTarget(GoalTarget.LESS_THAN)
                .withCategory(categoryDTO)
                .withStartDate(goalDTO.getStartDate())
                .withEndDate(goalDTO.getEndDate())
                .build();

        GoalDTO updatedGoalDTO = goalService.update(goalDTOToUpdate, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert updated goal is ok, and updated only concerned values
        //-----------------------------------------------------------------
        assertThat(updatedGoalDTO.getId()).isEqualTo(createdGoalDTO.getId());
        assertThat(updatedGoalDTO.getCategory().getColor()).isEqualTo(createdGoalDTO.getCategory().getColor());
        assertThat(updatedGoalDTO.getCategory().getName()).isEqualTo(createdGoalDTO.getCategory().getName());
        assertThat(updatedGoalDTO.getCategory().getId()).isEqualTo(createdGoalDTO.getCategory().getId());
        assertThat(updatedGoalDTO.getValue()).isEqualTo(createdGoalDTO.getValue());
        // Only these was updated
        assertThat(updatedGoalDTO.getGoalTarget()).isEqualTo(goalDTOToUpdate.getGoalTarget());
    }

    @Test
    public void update_category_ok() throws Exception {
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
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(2.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(categoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();

        GoalDTO createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        goalDTO.setId(createdGoalDTO.getId());

        //-----------------------------------------------------------------
        // Create new category
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("name2").build();
        categoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Goal to update - ONLY description
        //-----------------------------------------------------------------
        GoalDTO goalDTOToUpdate = new GoalDTOBuilder()
                .withId(goalDTO.getId())
                .withValue(2.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(categoryDTO)
                .withStartDate(goalDTO.getStartDate())
                .withEndDate(goalDTO.getEndDate())
                .build();

        GoalDTO updatedGoalDTO = goalService.update(goalDTOToUpdate, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert updated goal is ok, and updated only concerned values
        //-----------------------------------------------------------------
        assertThat(updatedGoalDTO.getId()).isEqualTo(createdGoalDTO.getId());
        assertThat(updatedGoalDTO.getValue()).isEqualTo(createdGoalDTO.getValue());
        // Only these was updated
        assertThat(updatedGoalDTO.getCategory().getColor()).isEqualTo(categoryDTO.getColor());
        assertThat(updatedGoalDTO.getCategory().getName()).isEqualTo(categoryDTO.getName());
        assertThat(updatedGoalDTO.getCategory().getId()).isEqualTo(categoryDTO.getId());
    }

    @Test(expected = GoalException.class)
    public void update_wrongCategory_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        categoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        CategoryDTO wrongCategoryDTO = new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("name").build();

        //-----------------------------------------------------------------
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(2.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(categoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        GoalDTO createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        goalDTO.setId(createdGoalDTO.getId());

        //-----------------------------------------------------------------
        // Goal to update - with category without ID
        //-----------------------------------------------------------------
        GoalDTO goalDTOToUpdate = new GoalDTOBuilder()
                .withId(goalDTO.getId())
                .withValue(2.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(wrongCategoryDTO)
                .withStartDate(goalDTO.getStartDate())
                .withEndDate(goalDTO.getEndDate())
                .build();
        goalService.update(goalDTOToUpdate, createdUserDTO.getId());
    }

    @Test(expected = ConstraintViolationException.class)
    public void update_noCategory_exceptionThrown() throws Exception {
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
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(2.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(categoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        GoalDTO createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        goalDTO.setId(createdGoalDTO.getId());

        //-----------------------------------------------------------------
        // Goal to update - with category without ID
        //-----------------------------------------------------------------
        GoalDTO goalDTOToUpdate = new GoalDTOBuilder()
                .withId(goalDTO.getId())
                .withValue(2.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withStartDate(goalDTO.getStartDate())
                .withEndDate(goalDTO.getEndDate())
                .build();

        goalService.update(goalDTOToUpdate, createdUserDTO.getId());
    }

}