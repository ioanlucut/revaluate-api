package com.revaluate.insights.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.domain.insights.progress.ProgressInsightsDTO;
import com.revaluate.expense.service.ExpenseService;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class ProgressInsightsServiceImplTestIT extends AbstractIntegrationTests {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProgressInsightsService progressInsightsService;

    @Test
    public void fetchProgressInsightsBetween_validDetailsAfterBefore_isOk() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Add expense time
        //-----------------------------------------------------------------
        LocalDateTime after = LocalDateTime.now().minusMonths(5).minusSeconds(1);

        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(1).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());

        ExpenseDTO expenseDTOB = new ExpenseDTOBuilder().withValue(2).withDescription("my second expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusMonths(2)).build();
        expenseService.create(expenseDTOB, createdUserDTO.getId());

        expenseDTOB = new ExpenseDTOBuilder().withValue(3).withDescription("my second expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusMonths(5)).build();
        expenseService.create(expenseDTOB, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Add end expense time
        //-----------------------------------------------------------------
        LocalDateTime before = LocalDateTime.now().plusMinutes(1);

        ProgressInsightsDTO progressInsights = progressInsightsService.fetchProgressInsightsBetween(userDTO.getId(), after, before);

        //-----------------------------------------------------------------
        // Assert insight is ok
        //-----------------------------------------------------------------
        assertThat(progressInsights, is(notNullValue()));
        assertThat(progressInsights.getFrom(), is(notNullValue()));
        assertThat(progressInsights.getTo(), is(notNullValue()));
        assertThat(progressInsights.getInsightsMonthlyDTO().stream().filter(s -> s.getTotalAmountSpent() == 0.0).count(), is(3L));
    }
}