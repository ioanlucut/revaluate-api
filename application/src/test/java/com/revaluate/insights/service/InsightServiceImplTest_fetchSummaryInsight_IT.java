package com.revaluate.insights.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.domain.insights.SummaryInsightsDTO;
import com.revaluate.expense.service.ExpenseService;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class InsightServiceImplTest_fetchSummaryInsight_IT extends AbstractIntegrationTests {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private InsightService insightService;

    @Test
    public void fetchInsight_validDetailsAfterBefore_isOk() throws Exception {
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
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusYears(3)).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        ExpenseDTO expenseDTOB = new ExpenseDTOBuilder().withValue(7.55).withDescription("my second expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().plusYears(3)).build();
        expenseService.create(expenseDTOB, createdUserDTO.getId());
        ExpenseDTO expenseDTOC = new ExpenseDTOBuilder().withValue(1.55).withDescription("my third expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().plusYears(2)).build();
        expenseService.create(expenseDTOC, createdUserDTO.getId());

        SummaryInsightsDTO summaryInsightsDTO = insightService.computeSummaryInsights(userDTO.getId());

        //-----------------------------------------------------------------
        // Assert insight is ok
        //-----------------------------------------------------------------
        assertThat(summaryInsightsDTO, is(notNullValue()));
        assertThat(summaryInsightsDTO.getFirstExistingExpenseDate().getYear(), is(expenseDTO.getSpentDate().getYear()));
        assertThat(summaryInsightsDTO.getLastExistingExpenseDate().getYear(), is(expenseDTOB.getSpentDate().getYear()));
    }
}