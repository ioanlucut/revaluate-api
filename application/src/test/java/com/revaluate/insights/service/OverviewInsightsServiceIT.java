package com.revaluate.insights.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.domain.insights.overview.InsightsOverviewDTO;
import com.revaluate.expense.service.ExpenseService;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class OverviewInsightsServiceIT extends AbstractIntegrationTests {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OverviewInsightsService overviewInsightsService;

    @Test
    public void fetchInsight_fromOneMonthValidDetailsAfterBefore_isOk() throws Exception {
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
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(100).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        ExpenseDTO expenseDTOB = new ExpenseDTOBuilder().withValue(1000).withDescription("my second expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTOB, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create category 2
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("name2").build();
        createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense 2
        //-----------------------------------------------------------------
        expenseDTO = new ExpenseDTOBuilder().withValue(200).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTOB = new ExpenseDTOBuilder().withValue(2000).withDescription("my second expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTOB, createdUserDTO.getId());
        LocalDateTime before = LocalDateTime.now().plusMinutes(1);

        InsightsOverviewDTO insightsOverviewDTO = overviewInsightsService.getOverviewInsightsBetween(userDTO.getId(), after, before);

        //-----------------------------------------------------------------
        // Assert insight is ok
        //-----------------------------------------------------------------
        assertThat(insightsOverviewDTO, is(notNullValue()));
        assertThat(insightsOverviewDTO.getInsightsOverview(), is(notNullValue()));
        assertThat(insightsOverviewDTO.getInsightsOverview().size(), is(1));

        assertThat(insightsOverviewDTO.getInsightsOverview().stream().anyMatch(s -> s.getTotalAmountFormatted().equals("3300.00")), is(Boolean.TRUE));
        assertThat(insightsOverviewDTO.getInsightsOverview().stream().anyMatch(s -> s.getTotalAmount() == 3300.0), is(Boolean.TRUE));

        assertThat(insightsOverviewDTO.getInsightsOverview().stream().filter(s -> s.getTotalAmount() == 0.0).count(), is(0));
    }

    @Test
    public void fetchInsight_fromTwoDifferentMonthsValidDetailsAfterBefore_isOk() throws Exception {
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
        LocalDateTime after = LocalDateTime.now().minusMonths(2).minusSeconds(1);
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(100).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        ExpenseDTO expenseDTOB = new ExpenseDTOBuilder().withValue(1000).withDescription("my second expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusMonths(2)).build();
        expenseService.create(expenseDTOB, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create category 2
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("name2").build();
        createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense 2
        //-----------------------------------------------------------------
        expenseDTO = new ExpenseDTOBuilder().withValue(200).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTOB = new ExpenseDTOBuilder().withValue(2000).withDescription("my second expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusMonths(2)).build();
        expenseService.create(expenseDTOB, createdUserDTO.getId());
        LocalDateTime before = LocalDateTime.now().plusMinutes(1);

        InsightsOverviewDTO insightsOverviewDTO = overviewInsightsService.getOverviewInsightsBetween(userDTO.getId(), after, before);

        //-----------------------------------------------------------------
        // Assert insight is ok
        //-----------------------------------------------------------------
        assertThat(insightsOverviewDTO, is(notNullValue()));
        assertThat(insightsOverviewDTO.getInsightsOverview(), is(notNullValue()));

        assertThat(insightsOverviewDTO.getInsightsOverview().stream().anyMatch(s -> s.getTotalAmountFormatted().equals("300.00")), is(Boolean.TRUE));
        assertThat(insightsOverviewDTO.getInsightsOverview().stream().anyMatch(s -> s.getTotalAmount() == 300.0), is(Boolean.TRUE));
        assertThat(insightsOverviewDTO.getInsightsOverview().stream().anyMatch(s -> s.getTotalAmountFormatted().equals("3000.00")), is(Boolean.TRUE));
        assertThat(insightsOverviewDTO.getInsightsOverview().stream().anyMatch(s -> s.getTotalAmount() == 3000.0), is(Boolean.TRUE));

        assertThat(insightsOverviewDTO.getInsightsOverview().stream().filter(s -> s.getTotalAmount() == 0.0).count(), is(0));
    }

    @Test
    public void fetchInsight_fromTwoDifferentMonthsWithGapsValidDetailsAfterBefore_isOk() throws Exception {
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
        InsightsOverviewDTO insightsOverviewDTO = overviewInsightsService.getOverviewInsightsBetween(userDTO.getId(), after, before);

        //-----------------------------------------------------------------
        // Assert insight is ok
        //-----------------------------------------------------------------
        assertThat(insightsOverviewDTO, is(notNullValue()));
        assertThat(insightsOverviewDTO.getInsightsOverview(), is(notNullValue()));
        assertThat(insightsOverviewDTO.getInsightsOverview().stream().filter(s -> s.getTotalAmount() == 0.0).count(), is(3L));
    }

}