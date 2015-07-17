package com.revaluate.insights.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.domain.insights.monthly.InsightsMonthlyDTO;
import com.revaluate.expense.service.ExpenseService;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class MonthlyInsightsServiceTestIT extends AbstractIntegrationTests {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MonthlyInsightsService monthlyInsightsService;

    @Test
    public void fetchMonthlyInsightsAfterBeforePeriod_validDetailsAfterBefore_isOk() throws Exception {
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
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        ExpenseDTO expenseDTOB = new ExpenseDTOBuilder().withValue(7.55).withDescription("my second expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTOB, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create category 2
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("name2").build();
        createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense 2
        //-----------------------------------------------------------------
        expenseDTO = new ExpenseDTOBuilder().withValue(12.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTOB = new ExpenseDTOBuilder().withValue(17.55).withDescription("my second expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTOB, createdUserDTO.getId());
        LocalDateTime before = LocalDateTime.now().plusMinutes(1);

        InsightsMonthlyDTO insightsMonthlyDTO = monthlyInsightsService.fetchMonthlyInsightsAfterBeforePeriod(userDTO.getId(), after, before);

        //-----------------------------------------------------------------
        // Assert insight is ok
        //-----------------------------------------------------------------
        assertThat(insightsMonthlyDTO, is(notNullValue()));
        assertThat(insightsMonthlyDTO.getFrom(), is(notNullValue()));
        assertThat(insightsMonthlyDTO.getTo(), is(notNullValue()));

        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs(), is(notNullValue()));
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().size(), is(2));
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().stream().anyMatch(s -> s.getCategoryDTO().getName().equals("name") && s.getTotalAmountFormatted().equals("10.10")), is(Boolean.TRUE));
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().stream().anyMatch(s -> s.getCategoryDTO().getName().equals("name2") && s.getTotalAmountFormatted().equals("30.10")), is(Boolean.TRUE));

        assertThat(insightsMonthlyDTO.getNumberOfTransactions(), is(4L));
        assertThat(insightsMonthlyDTO.getTotalAmountSpent(), is(40.20));
    }

    @Test
    public void fetchMonthlyInsightsAfterBeforePeriod_notEqualNumberOfExpensesPerCategories_isOk() throws Exception {
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
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        ExpenseDTO expenseDTOB = new ExpenseDTOBuilder().withValue(7.55).withDescription("my second expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTOB, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create category 2
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("name2").build();
        createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense 2
        //-----------------------------------------------------------------
        expenseDTO = new ExpenseDTOBuilder().withValue(12.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        LocalDateTime before = LocalDateTime.now().plusMinutes(1);

        InsightsMonthlyDTO insightsMonthlyDTO = monthlyInsightsService.fetchMonthlyInsightsAfterBeforePeriod(userDTO.getId(), after, before);

        //-----------------------------------------------------------------
        // Assert insight is ok
        //-----------------------------------------------------------------
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs(), is(notNullValue()));
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().size(), is(2));
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().stream().anyMatch(s -> s.getCategoryDTO().getName().equals("name") && s.getTotalAmountFormatted().equals("10.10")), is(Boolean.TRUE));
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().stream().anyMatch(s -> s.getCategoryDTO().getName().equals("name2") && s.getTotalAmountFormatted().equals("12.55")), is(Boolean.TRUE));

        assertThat(insightsMonthlyDTO.getNumberOfTransactions(), is(3L));
        assertThat(insightsMonthlyDTO.getTotalAmountSpent(), is(22.65));
    }
    @Test
    public void fetchMonthlyInsightsAfterBeforePeriod_differentTotalAmount_isSortedOk() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        LocalDateTime after = LocalDateTime.now().minusSeconds(1);

        //-----------------------------------------------------------------
        // Create category 1
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense 1
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(25).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTO = new ExpenseDTOBuilder().withValue(35).withDescription("my second expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create category 2
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("name2").build();
        createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense 2
        //-----------------------------------------------------------------
        expenseDTO = new ExpenseDTOBuilder().withValue(20).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTO = new ExpenseDTOBuilder().withValue(30).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create category 3
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor(THIRD_VALID_COLOR).withName("name3").build();
        createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense 3
        //-----------------------------------------------------------------
        expenseDTO = new ExpenseDTOBuilder().withValue(200).withDescription("my third expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTO = new ExpenseDTOBuilder().withValue(300).withDescription("my third expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTO = new ExpenseDTOBuilder().withValue(400).withDescription("my third expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());

        LocalDateTime before = LocalDateTime.now().plusMinutes(1);
        InsightsMonthlyDTO insightsMonthlyDTO = monthlyInsightsService.fetchMonthlyInsightsAfterBeforePeriod(userDTO.getId(), after, before);

        //-----------------------------------------------------------------
        // Assert insight is ok
        //-----------------------------------------------------------------
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs(), is(notNullValue()));
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().size(), is(3));
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().get(0).getCategoryDTO().getColor().getColor(), is(THIRD_VALID_COLOR.getColor()));
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().get(1).getCategoryDTO().getColor().getColor(), is(FIRST_VALID_COLOR.getColor()));
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().get(2).getCategoryDTO().getColor().getColor(), is(SECOND_VALID_COLOR.getColor()));
        assertThat(insightsMonthlyDTO.getBiggestExpense().getValue(), is(400.0));
        assertThat(insightsMonthlyDTO.getHighestAmountCategory().getName(), is(equalTo("name3")));
        assertThat(insightsMonthlyDTO.getCategoryWithTheMostTransactionsInsightsDTO(), is(notNullValue()));
        assertThat(insightsMonthlyDTO.getCategoryWithTheMostTransactionsInsightsDTO().getCategoryDTO().getName(), is(equalTo("name3")));
        assertThat(insightsMonthlyDTO.getCategoryWithTheMostTransactionsInsightsDTO().getNumberOfTransactions(), is(3));

        assertThat(insightsMonthlyDTO.getNumberOfTransactions(), is(7L));
    }
}