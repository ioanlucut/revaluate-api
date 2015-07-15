package com.revaluate.insights.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.domain.insights.InsightsMonthsPerYearsDTO;
import com.revaluate.expense.service.ExpenseService;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class InsightMonthsPerYearServiceImplTestIT extends AbstractIntegrationTests {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private InsightMonthsPerYearService insightMonthsPerYearService;

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

        InsightsMonthsPerYearsDTO insightsMonthsPerYearsDTO = insightMonthsPerYearService.getExistingDaysPerYearsWithExpensesDefined(userDTO.getId());
        Map<Integer, Set<Integer>> existingDaysPerYearsWithExpensesDefined = insightsMonthsPerYearsDTO.getInsightsMonthsPerYears();

        //-----------------------------------------------------------------
        // Assert grouping is ok
        //-----------------------------------------------------------------
        assertThat(existingDaysPerYearsWithExpensesDefined, is(notNullValue()));
        assertThat(existingDaysPerYearsWithExpensesDefined.get(firstYear.getYear()), is(notNullValue()));
        assertThat(existingDaysPerYearsWithExpensesDefined.get(firstYear.getYear()).size(), is(equalTo(2)));
        assertThat(existingDaysPerYearsWithExpensesDefined.get(firstYear.getYear()).stream().anyMatch(integer -> integer == firstYearFirstMonthOfYear), is(true));
        assertThat(existingDaysPerYearsWithExpensesDefined.get(firstYear.getYear()).stream().anyMatch(integer -> integer == firstYearSecondMonthOfYear), is(true));

        assertThat(existingDaysPerYearsWithExpensesDefined.get(secondYear.getYear()), is(notNullValue()));
        assertThat(existingDaysPerYearsWithExpensesDefined.get(secondYear.getYear()).size(), is(equalTo(2)));
        assertThat(existingDaysPerYearsWithExpensesDefined.get(secondYear.getYear()).stream().anyMatch(integer -> integer == secondYearFirstMonthOfYear), is(true));
        assertThat(existingDaysPerYearsWithExpensesDefined.get(secondYear.getYear()).stream().anyMatch(integer -> integer == secondYearSecondMonthOfYear), is(true));

        assertThat(existingDaysPerYearsWithExpensesDefined.get(thirdYear.getYear()), is(notNullValue()));
        assertThat(existingDaysPerYearsWithExpensesDefined.get(thirdYear.getYear()).size(), is(equalTo(2)));
        assertThat(existingDaysPerYearsWithExpensesDefined.get(thirdYear.getYear()).stream().anyMatch(integer -> integer == thirdYearFirstMonthOfYear), is(true));
        assertThat(existingDaysPerYearsWithExpensesDefined.get(thirdYear.getYear()).stream().anyMatch(integer -> integer == thirdYearSecondMonthOfYear), is(true));
    }
}