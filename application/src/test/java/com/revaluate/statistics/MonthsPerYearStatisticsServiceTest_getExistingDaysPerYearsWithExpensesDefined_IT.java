package com.revaluate.statistics;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.domain.insights.statistics.MonthsPerYearsDTO;
import com.revaluate.expense.service.ExpenseService;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class MonthsPerYearStatisticsServiceTest_getExistingDaysPerYearsWithExpensesDefined_IT extends AbstractIntegrationTests {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MonthsPerYearStatisticsService monthsPerYearStatisticsService;

    @Test
    public void getExistingDaysPerYearsWithExpensesDefined_expensesAreProperlyGrouped_isOk() throws Exception {
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
        LocalDateTime firstYear = LocalDateTime.now().minusYears(3);
        int firstYearFirstMonthOfYear = 2;
        int firstYearSecondMonthOfYear = 6;

        LocalDateTime secondYear = LocalDateTime.now().plusYears(3);
        int secondYearFirstMonthOfYear = 3;
        int secondYearSecondMonthOfYear = 9;

        LocalDateTime thirdYear = LocalDateTime.now().plusYears(2);
        int thirdYearFirstMonthOfYear = 1;
        int thirdYearSecondMonthOfYear = 5;

        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(firstYear.withMonthOfYear(firstYearFirstMonthOfYear)).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(firstYear.withMonthOfYear(firstYearSecondMonthOfYear)).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        ExpenseDTO expenseDTOB = new ExpenseDTOBuilder().withValue(7.55).withDescription("my second expense").withCategory(createdCategoryDTO).withSpentDate(secondYear.withMonthOfYear(secondYearFirstMonthOfYear)).build();
        expenseService.create(expenseDTOB, createdUserDTO.getId());
        expenseDTOB = new ExpenseDTOBuilder().withValue(7.55).withDescription("my second expense").withCategory(createdCategoryDTO).withSpentDate(secondYear.withMonthOfYear(secondYearSecondMonthOfYear)).build();
        expenseService.create(expenseDTOB, createdUserDTO.getId());
        ExpenseDTO expenseDTOC = new ExpenseDTOBuilder().withValue(1.55).withDescription("my third expense").withCategory(createdCategoryDTO).withSpentDate(thirdYear.withMonthOfYear(thirdYearFirstMonthOfYear)).build();
        expenseService.create(expenseDTOC, createdUserDTO.getId());
        expenseDTOC = new ExpenseDTOBuilder().withValue(1.55).withDescription("my third expense").withCategory(createdCategoryDTO).withSpentDate(thirdYear.withMonthOfYear(thirdYearSecondMonthOfYear)).build();
        expenseService.create(expenseDTOC, createdUserDTO.getId());

        MonthsPerYearsDTO monthsPerYearsDTO = monthsPerYearStatisticsService.getExistingDaysPerYearsWithExpensesDefined(userDTO.getId());
        Map<Integer, Set<Integer>> existingDaysPerYearsWithExpensesDefined = monthsPerYearsDTO.getMonthsPerYears();

        //-----------------------------------------------------------------
        // Assert grouping is ok
        //-----------------------------------------------------------------
        assertThat(existingDaysPerYearsWithExpensesDefined).isNotNull();
        assertThat(existingDaysPerYearsWithExpensesDefined.get(firstYear.getYear())).isNotNull();
        assertThat(existingDaysPerYearsWithExpensesDefined.get(firstYear.getYear()).size()).isEqualTo(2);
        assertThat(existingDaysPerYearsWithExpensesDefined.get(firstYear.getYear()).stream().anyMatch(integer -> integer == firstYearFirstMonthOfYear)).isTrue();
        assertThat(existingDaysPerYearsWithExpensesDefined.get(firstYear.getYear()).stream().anyMatch(integer -> integer == firstYearSecondMonthOfYear)).isTrue();

        assertThat(existingDaysPerYearsWithExpensesDefined.get(secondYear.getYear())).isNotNull();
        assertThat(existingDaysPerYearsWithExpensesDefined.get(secondYear.getYear()).size()).isEqualTo(2);
        assertThat(existingDaysPerYearsWithExpensesDefined.get(secondYear.getYear()).stream().anyMatch(integer -> integer == secondYearFirstMonthOfYear)).isTrue();
        assertThat(existingDaysPerYearsWithExpensesDefined.get(secondYear.getYear()).stream().anyMatch(integer -> integer == secondYearSecondMonthOfYear)).isTrue();

        assertThat(existingDaysPerYearsWithExpensesDefined.get(thirdYear.getYear())).isNotNull();
        assertThat(existingDaysPerYearsWithExpensesDefined.get(thirdYear.getYear()).size()).isEqualTo(2);
        assertThat(existingDaysPerYearsWithExpensesDefined.get(thirdYear.getYear()).stream().anyMatch(integer -> integer == thirdYearFirstMonthOfYear)).isTrue();
        assertThat(existingDaysPerYearsWithExpensesDefined.get(thirdYear.getYear()).stream().anyMatch(integer -> integer == thirdYearSecondMonthOfYear)).isTrue();
    }
}