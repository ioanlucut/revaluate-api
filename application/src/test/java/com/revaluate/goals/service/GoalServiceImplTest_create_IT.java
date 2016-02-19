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
import org.joda.money.CurrencyUnit;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

public class GoalServiceImplTest_create_IT extends AbstractIntegrationTests {

    @Autowired
    private GoalService goalService;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void create_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(100.00)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        GoalDTO createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        goalDTO.setId(createdGoalDTO.getId());

        //-----------------------------------------------------------------
        // Assert created goal is ok
        //-----------------------------------------------------------------
        assertThat(createdGoalDTO).isNotNull();
        assertThat(createdGoalDTO.getGoalStatusDTO()).isNotNull();
        assertThat(createdGoalDTO.getId()).isNotEqualTo(0);
        assertThat(createdGoalDTO.getCategory()).isEqualTo(goalDTO.getCategory());
        assertThat(createdGoalDTO.getGoalTarget()).isEqualTo(GoalTarget.MORE_THAN);
        assertThat(createdGoalDTO.getValue()).isEqualTo(goalDTO.getValue());
        assertThat(createdGoalDTO.getStartDate().getYear()).isEqualTo(goalDTO.getStartDate().getYear());
        assertThat(createdGoalDTO.getEndDate().getYear()).isEqualTo(goalDTO.getEndDate().getYear());
    }

    @Test
    public void create_testMaxAndMinValues_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(0.01)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        goalService.create(goalDTO, createdUserDTO.getId());

        exception.expect(ConstraintViolationException.class);
        goalDTO = new GoalDTOBuilder()
                .withValue(0.001)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        GoalDTO createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());

        goalDTO = new GoalDTOBuilder()
                .withValue(111_111_111_111_111_111_11.11)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        assertThat(createdGoalDTO.getValue()).isEqualTo(goalDTO.getValue());

        goalDTO = new GoalDTOBuilder()
                .withValue(899_999_999_999_999_999_99.11)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        assertThat(createdGoalDTO.getValue()).isEqualTo(goalDTO.getValue());

        exception.expect(ConstraintViolationException.class);
        goalDTO = new GoalDTOBuilder()
                .withValue(999_999_999_999_999_999_99.11)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();

        createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        assertThat(createdGoalDTO.getValue()).isEqualTo(goalDTO.getValue());
    }

    @Test
    public void create_withDifferentPriceCombinations_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        GoalDTO goalDTO;
        GoalDTO createdGoalDTO;

        //-----------------------------------------------------------------
        // Create goals
        //-----------------------------------------------------------------
        goalDTO = new GoalDTOBuilder()
                .withValue(2.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        assertThat(createdGoalDTO.getValue()).isEqualTo(goalDTO.getValue());

        goalDTO = new GoalDTOBuilder()
                .withValue(9999.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        assertThat(createdGoalDTO.getValue()).isEqualTo(goalDTO.getValue());

        goalDTO = new GoalDTOBuilder()
                .withValue(2147483647)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        assertThat(createdGoalDTO.getValue()).isEqualTo(goalDTO.getValue());

        goalDTO = new GoalDTOBuilder()
                .withValue(2147483647.99)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        assertThat(createdGoalDTO.getValue()).isEqualTo(goalDTO.getValue());
    }

    @Test
    public void create_twoGoalsWithSameCategory_ok() throws Exception {
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

        GoalDTO createdGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());
        GoalDTO secondCreatedGoalDTO = goalService.create(goalDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert created goals is ok
        //-----------------------------------------------------------------
        assertThat(createdGoalDTO.getCategory()).isEqualTo(goalDTO.getCategory());
        assertThat(secondCreatedGoalDTO.getCategory()).isEqualTo(goalDTO.getCategory());
    }

    @Test(expected = ConstraintViolationException.class)
    public void create_withoutCategoryForTwoGoals_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create an invalid goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(9999.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();

        goalService.create(goalDTO, createdUserDTO.getId());
    }

    @Test(expected = GoalException.class)
    public void create_properCategoryButWrongUserId_throwsException() throws Exception {
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
        // Create an invalid goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(9999.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(categoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        goalService.create(goalDTO, createdUserDTO.getId() + 999999);
    }

    @Test
    public void create_invalidGoalDTO_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create an invalid goal - no category
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        goalService.create(new GoalDTOBuilder().withValue(2.55).build(), createdUserDTO.getId());
    }

    @Test(expected = GoalException.class)
    public void create_withAnotherUserCategory_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO firstUserDTO = createUserDTO(TEST_EMAIL);

        //-----------------------------------------------------------------
        // Create second user
        //-----------------------------------------------------------------
        UserDTO secondUserDTO = createUserDTO("xxx@xxx.xxx2", CurrencyUnit.AUD.getCurrencyCode());

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        categoryDTO = categoryService.create(categoryDTO, secondUserDTO.getId());

        //-----------------------------------------------------------------
        // Create an goals with category from another user
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(9999.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(categoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();
        goalService.create(goalDTO, firstUserDTO.getId());
    }

}