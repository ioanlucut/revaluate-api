package com.revaluate.expense.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpenseServiceImplTest_bulkDelete_IT extends AbstractIntegrationTests {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void bulkDelete_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        int firstSize = expenseService.findAllExpensesFor(createdUserDTO.getId(), Optional.empty()).size();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expenses
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusYears(3)).build();
        ExpenseDTO expenseDTOCreated = expenseService.create(expenseDTO, createdUserDTO.getId());
        ExpenseDTO expenseDTOB = new ExpenseDTOBuilder().withValue(3.55).withDescription("my second expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusYears(3)).build();
        ExpenseDTO expenseDTOBCreated = expenseService.create(expenseDTOB, createdUserDTO.getId());

        int creationSize = expenseService.findAllExpensesFor(createdUserDTO.getId(), Optional.empty()).size();
        assertThat(firstSize).isEqualTo(creationSize - 2);

        //-----------------------------------------------------------------
        // Delete expenses
        //-----------------------------------------------------------------
        expenseService.bulkDelete(Arrays.asList(expenseDTOCreated, expenseDTOBCreated), createdUserDTO.getId());

        int postBulkDeleteSize = expenseService.findAllExpensesFor(createdUserDTO.getId(), Optional.empty()).size();
        assertThat(postBulkDeleteSize).isEqualTo(firstSize);
    }

    @Test
    public void bulkDelete_invalidData_validationWorksOk() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        exception.expect(ConstraintViolationException.class);
        expenseService.bulkDelete(null, createdUserDTO.getId());

        exception.expect(ConstraintViolationException.class);
        expenseService.bulkDelete(Arrays.asList(
                new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withSpentDate(LocalDateTime.now().minusYears(3)).build(),
                new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusYears(3)).build()), createdUserDTO.getId());

        exception.expect(ConstraintViolationException.class);
        expenseService.bulkDelete(Collections.singletonList(new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusYears(3)).build()), createdUserDTO.getId());

        exception.expect(ConstraintViolationException.class);
        List<ExpenseDTO> tooManyExpenseDTOs = new ArrayList<>(ExpenseService.MAX_SIZE_LIST + 1);
        Collections.fill(tooManyExpenseDTOs, new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusYears(3)).build());
        expenseService.bulkDelete(tooManyExpenseDTOs, createdUserDTO.getId());
    }

    @Test
    public void bulkDelete_tryToDeleteNonExistingExpense_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expenses
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusYears(3)).build();
        ExpenseDTO expenseDTOCreated = expenseService.create(expenseDTO, createdUserDTO.getId());
        ExpenseDTO expenseDTOWhichDoesNotExist = new ExpenseDTOBuilder().withValue(22222.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusYears(3)).build();

        exception.expect(ConstraintViolationException.class);
        expenseService.bulkDelete(Arrays.asList(expenseDTOCreated, expenseDTOWhichDoesNotExist), createdUserDTO.getId());
    }

}
