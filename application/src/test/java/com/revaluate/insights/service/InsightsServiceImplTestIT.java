package com.revaluate.insights.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.domain.insights.InsightDTO;
import com.revaluate.expense.service.ExpenseService;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class InsightsServiceImplTestIT extends AbstractIntegrationTests {

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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense 1
        //-----------------------------------------------------------------
        LocalDateTime after = LocalDateTime.now().minusSeconds(1);
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        ExpenseDTO expenseDTOB = new ExpenseDTOBuilder().withValue(7.55).withDescription("my second expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTOB, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create category 2
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor("#fff").withName("name2").build();
        createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense 2
        //-----------------------------------------------------------------
        expenseDTO = new ExpenseDTOBuilder().withValue(12.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTOB = new ExpenseDTOBuilder().withValue(17.55).withDescription("my second expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTOB, createdUserDTO.getId());
        LocalDateTime before = LocalDateTime.now().plusMinutes(1);

        InsightDTO insightDTO = insightService.fetchInsightAfterBeforePeriod(userDTO.getId(), after, before);

        //-----------------------------------------------------------------
        // Assert insight is ok
        //-----------------------------------------------------------------
        assertThat(insightDTO, is(notNullValue()));
        assertThat(insightDTO.getFrom(), is(notNullValue()));
        assertThat(insightDTO.getTo(), is(notNullValue()));

        assertThat(insightDTO.getInsightData(), is(notNullValue()));
        assertThat(insightDTO.getInsightData().size(), is(2));
        assertThat(insightDTO.getInsightData().stream().anyMatch(s -> "10.10".equals(s)), is(Boolean.TRUE));
        assertThat(insightDTO.getInsightData().stream().anyMatch(s -> "30.10".equals(s)), is(Boolean.TRUE));

        assertThat(insightDTO.getInsightLabels(), is(notNullValue()));
        assertThat(insightDTO.getInsightLabels().size(), is(2));
        assertThat(insightDTO.getInsightLabels().stream().anyMatch(s -> "name".equals(s)), is(Boolean.TRUE));
        assertThat(insightDTO.getInsightLabels().stream().anyMatch(s -> "name2".equals(s)), is(Boolean.TRUE));

        assertThat(insightDTO.getInsightColors(), is(notNullValue()));
        assertThat(insightDTO.getInsightColors().size(), is(2));
        assertThat(insightDTO.getInsightColors().stream().anyMatch(s -> "#eee".equals(s)), is(Boolean.TRUE));
        assertThat(insightDTO.getInsightColors().stream().anyMatch(s -> "#fff".equals(s)), is(Boolean.TRUE));

        assertThat(insightDTO.getTotalPerCategories(), is(notNullValue()));
        assertThat(insightDTO.getTotalPerCategories().size(), is(2));
        assertThat(insightDTO.getTotalPerCategories().entrySet().stream().anyMatch(s -> s.getKey().getName().equals("name") && s.getValue().equals("10.10")), is(Boolean.TRUE));
        assertThat(insightDTO.getTotalPerCategories().entrySet().stream().anyMatch(s -> s.getKey().getName().equals("name2") && s.getValue().equals("30.10")), is(Boolean.TRUE));

        assertThat(insightDTO.getNumberOfTransactions(), is(4));
        assertThat(insightDTO.getTotalAmountSpent(), is(40.20));
    }

    @Test
    public void fetchInsight_notEqualNumberOfExpensesPerCategories_isOk() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category 1
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense 1
        //-----------------------------------------------------------------
        LocalDateTime after = LocalDateTime.now().minusSeconds(1);
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        ExpenseDTO expenseDTOB = new ExpenseDTOBuilder().withValue(7.55).withDescription("my second expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTOB, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create category 2
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor("#fff").withName("name2").build();
        createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense 2
        //-----------------------------------------------------------------
        expenseDTO = new ExpenseDTOBuilder().withValue(12.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        LocalDateTime before = LocalDateTime.now().plusMinutes(1);

        InsightDTO insightDTO = insightService.fetchInsightAfterBeforePeriod(userDTO.getId(), after, before);

        //-----------------------------------------------------------------
        // Assert insight is ok
        //-----------------------------------------------------------------
        assertThat(insightDTO.getInsightData(), is(notNullValue()));
        assertThat(insightDTO.getInsightData().size(), is(2));
        assertThat(insightDTO.getInsightData().stream().anyMatch(s -> "10.10".equals(s)), is(Boolean.TRUE));
        assertThat(insightDTO.getInsightData().stream().anyMatch(s -> "12.55".equals(s)), is(Boolean.TRUE));

        assertThat(insightDTO.getInsightLabels(), is(notNullValue()));
        assertThat(insightDTO.getInsightLabels().size(), is(2));
        assertThat(insightDTO.getInsightLabels().stream().anyMatch(s -> "name".equals(s)), is(Boolean.TRUE));
        assertThat(insightDTO.getInsightLabels().stream().anyMatch(s -> "name2".equals(s)), is(Boolean.TRUE));

        assertThat(insightDTO.getInsightColors(), is(notNullValue()));
        assertThat(insightDTO.getInsightColors().size(), is(2));
        assertThat(insightDTO.getInsightColors().stream().anyMatch(s -> "#eee".equals(s)), is(Boolean.TRUE));
        assertThat(insightDTO.getInsightColors().stream().anyMatch(s -> "#fff".equals(s)), is(Boolean.TRUE));

        assertThat(insightDTO.getTotalPerCategories(), is(notNullValue()));
        assertThat(insightDTO.getTotalPerCategories().size(), is(2));
        assertThat(insightDTO.getTotalPerCategories().entrySet().stream().anyMatch(s -> s.getKey().getName().equals("name") && s.getValue().equals("10.10")), is(Boolean.TRUE));
        assertThat(insightDTO.getTotalPerCategories().entrySet().stream().anyMatch(s -> s.getKey().getName().equals("name2") && s.getValue().equals("12.55")), is(Boolean.TRUE));

        assertThat(insightDTO.getNumberOfTransactions(), is(3));
        assertThat(insightDTO.getTotalAmountSpent(), is(22.65));
    }
}