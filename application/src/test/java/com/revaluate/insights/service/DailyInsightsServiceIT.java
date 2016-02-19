package com.revaluate.insights.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.domain.insights.daily.InsightsDailyDTO;
import com.revaluate.expense.service.ExpenseService;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class DailyInsightsServiceIT extends AbstractIntegrationTests {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DailyInsightsService dailyInsightsService;

    @Test
    public void fetchDailyInsightsAfterBeforePeriod_fromOneMonthValidDetailsAfterBefore_isOk() throws Exception {
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
        LocalDateTime after = LocalDateTime.now();
        LocalDateTime before = LocalDateTime.now().plusMonths(1);

        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(100).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().plusMinutes(5)).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        ExpenseDTO expenseDTOB = new ExpenseDTOBuilder().withValue(1000).withDescription("my second expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().plusDays(1)).build();
        expenseService.create(expenseDTOB, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create category 2
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("name2").build();
        createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense 2
        //-----------------------------------------------------------------
        expenseDTO = new ExpenseDTOBuilder().withValue(200).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().plusMinutes(1)).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTOB = new ExpenseDTOBuilder().withValue(2000).withDescription("my second expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().plusDays(1)).build();
        expenseService.create(expenseDTOB, createdUserDTO.getId());

        InsightsDailyDTO insightsDailyDTO = dailyInsightsService.fetchDailyInsightsAfterBeforePeriod(userDTO.getId(), after, before);

        //-----------------------------------------------------------------
        // Assert insight is ok
        //-----------------------------------------------------------------
        assertThat(insightsDailyDTO).isNotNull();
        assertThat(insightsDailyDTO.getTotalPerDayDTOs()).isNotNull();
        assertThat(insightsDailyDTO.getTotalPerDayDTOs().size()).isGreaterThanOrEqualTo(28);

        assertThat(insightsDailyDTO.getTotalPerDayDTOs().stream().anyMatch(s -> s.getTotalAmount() == 3000.0)).isEqualTo((Boolean.TRUE));
        assertThat(insightsDailyDTO.getTotalPerDayDTOs().stream().anyMatch(s -> s.getTotalAmount() == 300.0)).isEqualTo((Boolean.TRUE));

        assertThat(insightsDailyDTO.getTotalPerDayDTOs().stream().filter(s -> s.getTotalAmount() == 0.0).count()).isGreaterThanOrEqualTo((27L));
    }

    @Test
    public void fetchDailyInsightsAfterBeforePeriod_withNoExpenses_isOk() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create expense 1
        //-----------------------------------------------------------------
        LocalDateTime after = LocalDateTime.now();
        LocalDateTime before = LocalDateTime.now().plusMonths(1);

        InsightsDailyDTO insightsDailyDTO = dailyInsightsService.fetchDailyInsightsAfterBeforePeriod(createdUserDTO.getId(), after, before);

        //-----------------------------------------------------------------
        // Assert insight is ok
        //-----------------------------------------------------------------
        assertThat(insightsDailyDTO).isNotNull();
        assertThat(insightsDailyDTO.getTotalPerDayDTOs()).isNotNull();
        assertThat(insightsDailyDTO.getTotalPerDayDTOs().size()).isGreaterThanOrEqualTo(28);
    }

}