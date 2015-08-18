package com.revaluate.expense.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.domain.expense.ExpensesQueryResponseDTO;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ExpenseServiceImplTest_findExpensesGroupBySpentDate_IT extends AbstractIntegrationTests {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void findAll_groupedOfExistingUserBetweenTwoDates_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense
        //-----------------------------------------------------------------
        ExpenseDTOBuilder expenseDTOBuilder = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO);

        expenseService.create(expenseDTOBuilder.withSpentDate(LocalDateTime.now()).build(), createdUserDTO.getId());

        expenseService.create(expenseDTOBuilder.withSpentDate(LocalDateTime.now().minusDays(1)).build(), createdUserDTO.getId());
        expenseService.create(expenseDTOBuilder.withSpentDate(LocalDateTime.now().minusDays(1)).build(), createdUserDTO.getId());

        expenseService.create(expenseDTOBuilder.withSpentDate(LocalDateTime.now().minusDays(2)).build(), createdUserDTO.getId());

        expenseService.create(expenseDTOBuilder.withSpentDate(LocalDateTime.now().minusMonths(1)).build(), createdUserDTO.getId());

        expenseService.create(expenseDTOBuilder.withSpentDate(LocalDateTime.now().minusMonths(1).minusDays(1)).build(), createdUserDTO.getId());

        expenseService.create(expenseDTOBuilder.withSpentDate(LocalDateTime.now().minusYears(1)).build(), createdUserDTO.getId());

        expenseService.create(expenseDTOBuilder.withSpentDate(LocalDateTime.now().minusYears(1).minusDays(1)).build(), createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Find created expenses + asserts
        //-----------------------------------------------------------------
        ExpensesQueryResponseDTO expensesAfterBeforeAndGroupBySpentDate = expenseService.findExpensesGroupBySpentDate(createdUserDTO.getId(),
                new PageRequest(0, 10, new Sort(
                        new Sort.Order(Sort.Direction.DESC, "spentDate"),
                        new Sort.Order(Sort.Direction.DESC, "createdDate")
                )));
        assertThat(expensesAfterBeforeAndGroupBySpentDate.getGroupedExpensesDTOList().size(), is(7));
        assertThat(expensesAfterBeforeAndGroupBySpentDate.getCurrentPage(), is(0));
        assertThat(expensesAfterBeforeAndGroupBySpentDate.getCurrentSize(), is(8));

        expensesAfterBeforeAndGroupBySpentDate = expenseService.findExpensesGroupBySpentDate(createdUserDTO.getId(),
                new PageRequest(1, 10, new Sort(
                        new Sort.Order(Sort.Direction.DESC, "spentDate"),
                        new Sort.Order(Sort.Direction.DESC, "createdDate")
                )));
        assertThat(expensesAfterBeforeAndGroupBySpentDate.getGroupedExpensesDTOList().size(), is(0));
        assertThat(expensesAfterBeforeAndGroupBySpentDate.getCurrentPage(), is(1));
        assertThat(expensesAfterBeforeAndGroupBySpentDate.getCurrentSize(), is(0));
    }

}
