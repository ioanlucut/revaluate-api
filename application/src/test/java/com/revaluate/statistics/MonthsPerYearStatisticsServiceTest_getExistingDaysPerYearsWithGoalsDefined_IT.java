package com.revaluate.statistics;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.goal.GoalDTO;
import com.revaluate.domain.goal.GoalDTOBuilder;
import com.revaluate.domain.goal.GoalTarget;
import com.revaluate.domain.insights.statistics.MonthsPerYearsDTO;
import com.revaluate.goals.service.GoalService;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class MonthsPerYearStatisticsServiceTest_getExistingDaysPerYearsWithGoalsDefined_IT extends AbstractIntegrationTests {

    @Autowired
    private GoalService goalService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MonthsPerYearStatisticsService monthsPerYearStatisticsService;

    @Test
    public void getExistingDaysPerYearsWithGoalsDefined_goalsAreProperlyGrouped_isOk() throws Exception {
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
        // Create goal 1
        //-----------------------------------------------------------------
        LocalDateTime firstYear = LocalDateTime.now().minusYears(3);
        int firstYearFirstMonthOfYear = 2;
        int firstYearSecondMonthOfYear = 6;

        LocalDateTime secondYear = LocalDateTime.now().plusYears(3);
        int secondYearFirstMonthOfYear = 3;
        int secondYearSecondMonthOfYear = 9;

        LocalDateTime thirdYear = LocalDateTime.now().plusYears(2);
        int thirdYearFirstMonthOfYear = 1;
        int thirdYearSecondMonthOfYear = 5;

        //-----------------------------------------------------------------
        // Create goals
        //-----------------------------------------------------------------

        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(100.00)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(firstYear.withMonthOfYear(firstYearFirstMonthOfYear))
                .build();
        goalService.create(goalDTO, createdUserDTO.getId());

        goalDTO = new GoalDTOBuilder()
                .withValue(100.00)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(firstYear.withMonthOfYear(firstYearSecondMonthOfYear))
                .build();
        goalService.create(goalDTO, createdUserDTO.getId());

        goalDTO = new GoalDTOBuilder()
                .withValue(100.00)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(secondYear.withMonthOfYear(secondYearSecondMonthOfYear))
                .build();
        goalService.create(goalDTO, createdUserDTO.getId());

        goalDTO = new GoalDTOBuilder()
                .withValue(100.00)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(secondYear.withMonthOfYear(secondYearFirstMonthOfYear))
                .build();
        goalService.create(goalDTO, createdUserDTO.getId());

        goalDTO = new GoalDTOBuilder()
                .withValue(100.00)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(thirdYear.withMonthOfYear(thirdYearFirstMonthOfYear))
                .build();
        goalService.create(goalDTO, createdUserDTO.getId());

        goalDTO = new GoalDTOBuilder()
                .withValue(100.00)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(thirdYear.withMonthOfYear(thirdYearSecondMonthOfYear))
                .build();
        goalService.create(goalDTO, createdUserDTO.getId());

        MonthsPerYearsDTO monthsPerYearsDTO = monthsPerYearStatisticsService.getExistingDaysPerYearsWithGoalsDefined(userDTO.getId());
        Map<Integer, Set<Integer>> existingDaysPerYearsWithGoalsDefined = monthsPerYearsDTO.getMonthsPerYears();

        //-----------------------------------------------------------------
        // Assert grouping is ok
        //-----------------------------------------------------------------
        assertThat(existingDaysPerYearsWithGoalsDefined).isNotNull();
        assertThat(existingDaysPerYearsWithGoalsDefined.get(firstYear.getYear())).isNotNull();
        assertThat(existingDaysPerYearsWithGoalsDefined.get(firstYear.getYear()).size()).isEqualTo(2);
        assertThat(existingDaysPerYearsWithGoalsDefined.get(firstYear.getYear()).stream().anyMatch(integer -> integer == firstYearFirstMonthOfYear)).isTrue();
        assertThat(existingDaysPerYearsWithGoalsDefined.get(firstYear.getYear()).stream().anyMatch(integer -> integer == firstYearSecondMonthOfYear)).isTrue();

        assertThat(existingDaysPerYearsWithGoalsDefined.get(secondYear.getYear())).isNotNull();
        assertThat(existingDaysPerYearsWithGoalsDefined.get(secondYear.getYear()).size()).isEqualTo(2);
        assertThat(existingDaysPerYearsWithGoalsDefined.get(secondYear.getYear()).stream().anyMatch(integer -> integer == secondYearFirstMonthOfYear)).isTrue();
        assertThat(existingDaysPerYearsWithGoalsDefined.get(secondYear.getYear()).stream().anyMatch(integer -> integer == secondYearSecondMonthOfYear)).isTrue();

        assertThat(existingDaysPerYearsWithGoalsDefined.get(thirdYear.getYear())).isNotNull();
        assertThat(existingDaysPerYearsWithGoalsDefined.get(thirdYear.getYear()).size()).isEqualTo(2);
        assertThat(existingDaysPerYearsWithGoalsDefined.get(thirdYear.getYear()).stream().anyMatch(integer -> integer == thirdYearFirstMonthOfYear)).isTrue();
        assertThat(existingDaysPerYearsWithGoalsDefined.get(thirdYear.getYear()).stream().anyMatch(integer -> integer == thirdYearSecondMonthOfYear)).isTrue();
    }
}