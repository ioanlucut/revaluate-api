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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class GoalServiceImplTest_findAll_IT extends AbstractIntegrationTests {

    @Autowired
    private GoalService goalService;

    @Autowired
    private CategoryService categoryService;

    //-----------------------------------------------------------------
    // Find all goals work
    //-----------------------------------------------------------------
    @Test
    public void findAll_ofExistingUser_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
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

        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Find created expenses + asserts
        //-----------------------------------------------------------------
        List<GoalDTO> allGoalsFor = goalService.findAllGoalsFor(createdUserDTO.getId());
        assertThat(allGoalsFor, is(notNullValue()));
        assertThat(allGoalsFor.size(), is(equalTo(2)));
    }

    @Test
    public void findAll_ofExistingUserIfNoGoalExist_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Find created expenses + asserts
        //-----------------------------------------------------------------
        List<GoalDTO> allGoalsFor = goalService.findAllGoalsFor(createdUserDTO.getId());
        assertThat(allGoalsFor, is(notNullValue()));
        assertThat(allGoalsFor.size(), is(equalTo(0)));
    }

    //-----------------------------------------------------------------
    // Find all goals work - AFTER - BEFORE
    //-----------------------------------------------------------------
    @Test
    public void findAll_ofExistingUserBetweenTwoDates_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
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
        LocalDateTime startDate = LocalDateTime.now().minusSeconds(1);
        LocalDateTime endDate = LocalDateTime.now().plusHours(1);

        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(2.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(categoryDTO)
                .withStartDate(startDate)
                .withEndDate(endDate)
                .build();

        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Find created expenses + asserts
        //-----------------------------------------------------------------
        List<GoalDTO> allGoalsFor = goalService.findAllGoalsAfterBefore(createdUserDTO.getId(), startDate.minusMinutes(1), endDate.plusMinutes(1));
        assertThat(allGoalsFor, is(notNullValue()));
        assertThat(allGoalsFor.size(), is(equalTo(7)));
    }

    //-----------------------------------------------------------------
    // Find all goals work - AFTER a date
    //-----------------------------------------------------------------
    @Test
    public void findAll_ofExistingUserBetweenAfterADate_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
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
        LocalDateTime startDate = LocalDateTime.now().minusSeconds(1);
        LocalDateTime endDate = LocalDateTime.now().plusHours(1);

        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(2.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(startDate)
                .withEndDate(endDate)
                .build();

        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Find created expenses + asserts after a date
        //-----------------------------------------------------------------
        List<GoalDTO> allGoalsFor = goalService.findAllGoalsAfterBefore(createdUserDTO.getId(), startDate.minusMinutes(1), endDate.plusMinutes(1));
        assertThat(allGoalsFor, is(notNullValue()));
        assertThat(allGoalsFor.size(), is(equalTo(7)));
    }

    //-----------------------------------------------------------------
    // Find all goals work - With a category id
    //-----------------------------------------------------------------
    @Test
    public void findAll_ofExistingUserWithCategoryId_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        CategoryDTO categoryDTOTwo = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name2").build();
        CategoryDTO secondCreatedCategoryDTO = categoryService.create(categoryDTOTwo, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create goal
        //-----------------------------------------------------------------
        LocalDateTime startDate = LocalDateTime.now().minusSeconds(1);
        LocalDateTime endDate = LocalDateTime.now().plusHours(1);

        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(2.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(startDate)
                .withEndDate(endDate)
                .build();

        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());

        goalDTO = new GoalDTOBuilder()
                .withValue(2.55)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(secondCreatedCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();

        goalService.create(goalDTO, createdUserDTO.getId());
        goalService.create(goalDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Find created expenses + asserts after a date
        //-----------------------------------------------------------------
        List<GoalDTO> allGoalsFor = goalService.findAllGoalsAfterBefore(createdUserDTO.getId(), startDate.minusMinutes(1), endDate.plusMinutes(1));
        assertThat(allGoalsFor, is(notNullValue()));
        assertThat(allGoalsFor.size(), is(equalTo(2)));
    }
}