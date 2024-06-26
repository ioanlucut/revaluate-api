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

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(insightsMonthlyDTO).isNotNull();
        assertThat(insightsMonthlyDTO.getFrom()).isNotNull();
        assertThat(insightsMonthlyDTO.getTo()).isNotNull();

        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs()).isNotNull();
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().size()).isEqualTo((2));
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().stream().anyMatch(s -> s.getCategoryDTO().getName().equals("name") && s.getTotalAmountFormatted().equals("10.10"))).isEqualTo((Boolean.TRUE));
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().stream().anyMatch(s -> s.getCategoryDTO().getName().equals("name2") && s.getTotalAmountFormatted().equals("30.10"))).isEqualTo((Boolean.TRUE));

        assertThat(insightsMonthlyDTO.getNumberOfTransactions()).isEqualTo((4L));
        assertThat(insightsMonthlyDTO.getTotalAmountSpent()).isEqualTo((40.20));
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
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs()).isNotNull();
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().size()).isEqualTo((2));
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().stream().anyMatch(s -> s.getCategoryDTO().getName().equals("name") && s.getTotalAmountFormatted().equals("10.10"))).isEqualTo((Boolean.TRUE));
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().stream().anyMatch(s -> s.getCategoryDTO().getName().equals("name2") && s.getTotalAmountFormatted().equals("12.55"))).isEqualTo((Boolean.TRUE));

        assertThat(insightsMonthlyDTO.getNumberOfTransactions()).isEqualTo((3L));
        assertThat(insightsMonthlyDTO.getTotalAmountSpent()).isEqualTo((22.65));
    }

    @Test
    public void fetchMonthlyInsightsAfterBeforePeriod_emptyCategoriesAreShown_isOk() throws Exception {
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
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(3).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        ExpenseDTO expenseDTOB = new ExpenseDTOBuilder().withValue(5).withDescription("my second expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTOB, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create category 2 (it will be empty)
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("name2").build();
        createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        LocalDateTime before = LocalDateTime.now().plusMinutes(1);

        InsightsMonthlyDTO insightsMonthlyDTO = monthlyInsightsService.fetchMonthlyInsightsAfterBeforePeriod(userDTO.getId(), after, before);

        //-----------------------------------------------------------------
        // Assert insight is ok
        //-----------------------------------------------------------------
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs()).isNotNull();
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().size()).isEqualTo((2));
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().stream().anyMatch(s -> s.getCategoryDTO().getName().equals("name") && s.getTotalAmountFormatted().equals("8.00"))).isEqualTo((Boolean.TRUE));
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().stream().anyMatch(s -> s.getCategoryDTO().getName().equals("name2") && s.getTotalAmountFormatted().equals("0.00"))).isEqualTo((Boolean.TRUE));

        assertThat(insightsMonthlyDTO.getNumberOfTransactions()).isEqualTo((2L));
        assertThat(insightsMonthlyDTO.getTotalAmountSpent()).isEqualTo((8.0));
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
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs()).isNotNull();
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().size()).isEqualTo((3));
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().get(0).getCategoryDTO().getColor().getColor()).isEqualTo((THIRD_VALID_COLOR.getColor()));
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().get(1).getCategoryDTO().getColor().getColor()).isEqualTo((FIRST_VALID_COLOR.getColor()));
        assertThat(insightsMonthlyDTO.getTotalPerCategoryInsightsDTOs().get(2).getCategoryDTO().getColor().getColor()).isEqualTo((SECOND_VALID_COLOR.getColor()));
        assertThat(insightsMonthlyDTO.getBiggestExpense().getValue()).isEqualTo((400.0));
        assertThat(insightsMonthlyDTO.getHighestAmountCategory().getName()).isEqualTo("name3");
        assertThat(insightsMonthlyDTO.getCategoryWithTheMostTransactionsInsightsDTO()).isNotNull();
        assertThat(insightsMonthlyDTO.getCategoryWithTheMostTransactionsInsightsDTO().getCategoryDTO().getName()).isEqualTo("name3");
        assertThat(insightsMonthlyDTO.getCategoryWithTheMostTransactionsInsightsDTO().getNumberOfTransactions()).isEqualTo((3));

        assertThat(insightsMonthlyDTO.getNumberOfTransactions()).isEqualTo((7L));
    }
}