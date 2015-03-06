package com.revaluate.expense.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.domain.UserDTO;
import com.revaluate.category.domain.CategoryDTO;
import com.revaluate.category.domain.CategoryDTOBuilder;
import com.revaluate.category.service.CategoryService;
import com.revaluate.expense.domain.ExpenseDTO;
import com.revaluate.expense.domain.ExpenseDTOBuilder;
import com.revaluate.expense.exception.ExpenseException;
import org.joda.money.CurrencyUnit;
import org.joda.time.LocalDateTime;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class ExpenseServiceImplTestIT extends AbstractIntegrationTests {

    @Rule
    public ExpectedException exception = ExpectedException.none();
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private CategoryService categoryService;

    @Test
    public void create_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        ExpenseDTO createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTO.setId(createdExpenseDTO.getId());

        //-----------------------------------------------------------------
        // Assert created expense is ok
        //-----------------------------------------------------------------
        assertThat(createdExpenseDTO, is(notNullValue()));
        assertThat(createdExpenseDTO.getId(), not(equalTo(0)));
        assertThat(createdExpenseDTO.getCategory(), equalTo(expenseDTO.getCategory()));
        assertThat(createdExpenseDTO.getDescription(), equalTo(expenseDTO.getDescription()));
        assertThat(createdExpenseDTO.getValue(), equalTo(expenseDTO.getValue()));
    }

    @Test
    public void create_twoExpensesWithSameCategory_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        categoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create two expenses
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(categoryDTO).withSpentDate(LocalDateTime.now()).build();
        ExpenseDTO createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());
        ExpenseDTO secondCreatedExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert created expenses is ok
        //-----------------------------------------------------------------
        assertThat(createdExpenseDTO.getCategory(), equalTo(expenseDTO.getCategory()));
        assertThat(secondCreatedExpenseDTO.getCategory(), equalTo(expenseDTO.getCategory()));
    }

    @Test(expected = ConstraintViolationException.class)
    public void create_withoutCategoryForTwoExpenses_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create an invalid expense
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
    }

    @Test(expected = ExpenseException.class)
    public void create_properCategoryButWrongUserId_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        categoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create an invalid expense
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withCategory(categoryDTO).withDescription("my first expense").withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId() + 999999);
    }

    @Test
    public void create_invalidExpenseDTO_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create an invalid expense - no category
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        expenseService.create(new ExpenseDTOBuilder().withValue(2.55).build(), createdUserDTO.getId());
    }

    @Test(expected = ExpenseException.class)
    public void create_withAnotherUserCategory_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO firstUserDTO = createUserDTO(FAKE_EMAIL);

        //-----------------------------------------------------------------
        // Create second user
        //-----------------------------------------------------------------
        UserDTO secondUserDTO = createUserDTO("xxx@xxx.xxx2", CurrencyUnit.AUD.getCurrencyCode());

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        categoryDTO = categoryService.create(categoryDTO, secondUserDTO.getId());

        //-----------------------------------------------------------------
        // Create an expenses with category from another user
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withSpentDate(LocalDateTime.now()).withCategory(categoryDTO).build();
        expenseService.create(expenseDTO, firstUserDTO.getId());
    }

    //-----------------------------------------------------------------
    // Test update part
    //-----------------------------------------------------------------
    @Test
    public void update_value_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        ExpenseDTO createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTO.setId(createdExpenseDTO.getId());

        //-----------------------------------------------------------------
        // Expense to update - ONLY VALUE
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTOToUpdate = new ExpenseDTOBuilder().withId(expenseDTO.getId()).withValue(2.56).withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        ExpenseDTO updatedExpenseDTO = expenseService.update(expenseDTOToUpdate, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert updated expense is ok, and updated only concerned values
        //-----------------------------------------------------------------
        assertThat(updatedExpenseDTO.getId(), is(equalTo(createdExpenseDTO.getId())));
        assertThat(updatedExpenseDTO.getCategory().getColor(), equalTo(createdExpenseDTO.getCategory().getColor()));
        assertThat(updatedExpenseDTO.getCategory().getName(), equalTo(createdExpenseDTO.getCategory().getName()));
        assertThat(updatedExpenseDTO.getCategory().getId(), equalTo(createdExpenseDTO.getCategory().getId()));
        // Only this was updated
        assertThat(updatedExpenseDTO.getValue(), equalTo(expenseDTOToUpdate.getValue()));
    }

    @Test
    public void update_description_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        ExpenseDTO createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTO.setId(createdExpenseDTO.getId());

        //-----------------------------------------------------------------
        // Expense to update - ONLY description
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTOToUpdate = new ExpenseDTOBuilder().withId(expenseDTO.getId()).withValue(2.55).withDescription("DESC").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        ExpenseDTO updatedExpenseDTO = expenseService.update(expenseDTOToUpdate, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert updated expense is ok, and updated only concerned values
        //-----------------------------------------------------------------
        assertThat(updatedExpenseDTO.getId(), is(equalTo(createdExpenseDTO.getId())));
        assertThat(updatedExpenseDTO.getCategory().getColor(), equalTo(createdExpenseDTO.getCategory().getColor()));
        assertThat(updatedExpenseDTO.getCategory().getName(), equalTo(createdExpenseDTO.getCategory().getName()));
        assertThat(updatedExpenseDTO.getCategory().getId(), equalTo(createdExpenseDTO.getCategory().getId()));
        assertThat(updatedExpenseDTO.getValue(), equalTo(createdExpenseDTO.getValue()));
        // Only these was updated
        assertThat(updatedExpenseDTO.getDescription(), equalTo(expenseDTOToUpdate.getDescription()));
    }

    @Test
    public void update_category_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        ExpenseDTO createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTO.setId(createdExpenseDTO.getId());

        //-----------------------------------------------------------------
        // Create new category
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor("#fff").withName("name2").build();
        createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Expense to update - ONLY description
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTOToUpdate = new ExpenseDTOBuilder().withValue(2.55).withId(expenseDTO.getId()).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        ExpenseDTO updatedExpenseDTO = expenseService.update(expenseDTOToUpdate, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert updated expense is ok, and updated only concerned values
        //-----------------------------------------------------------------
        assertThat(updatedExpenseDTO.getId(), is(equalTo(createdExpenseDTO.getId())));
        assertThat(updatedExpenseDTO.getValue(), equalTo(createdExpenseDTO.getValue()));
        assertThat(updatedExpenseDTO.getDescription(), equalTo(expenseDTOToUpdate.getDescription()));
        // Only these was updated
        assertThat(updatedExpenseDTO.getCategory().getColor(), equalTo(createdCategoryDTO.getColor()));
        assertThat(updatedExpenseDTO.getCategory().getName(), equalTo(createdCategoryDTO.getName()));
        assertThat(updatedExpenseDTO.getCategory().getId(), equalTo(createdCategoryDTO.getId()));
    }

    @Test(expected = ExpenseException.class)
    public void update_wrongCategory_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        ExpenseDTO createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTO.setId(createdExpenseDTO.getId());

        //-----------------------------------------------------------------
        // Expense to update - with category without ID
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTOToUpdate = new ExpenseDTOBuilder().withValue(2.55).withId(expenseDTO.getId()).withDescription("my first expense").withSpentDate(LocalDateTime.now()).withCategory(categoryDTO).build();
        expenseService.update(expenseDTOToUpdate, createdUserDTO.getId());
    }

    @Test(expected = ConstraintViolationException.class)
    public void update_noCategory_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        ExpenseDTO createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTO.setId(createdExpenseDTO.getId());

        //-----------------------------------------------------------------
        // Expense to update - with category without ID
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTOToUpdate = new ExpenseDTOBuilder().withValue(2.55).withId(expenseDTO.getId()).withDescription("my first expense").build();
        expenseService.update(expenseDTOToUpdate, createdUserDTO.getId());
    }

    //-----------------------------------------------------------------
    // Find all expenses work
    //-----------------------------------------------------------------
    @Test
    public void findAll_ofExistingUser_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Find created epenses + asserts
        //-----------------------------------------------------------------
        List<ExpenseDTO> allExpensesFor = expenseService.findAllExpensesFor(createdUserDTO.getId());
        assertThat(allExpensesFor, is(notNullValue()));
        assertThat(allExpensesFor.size(), is(equalTo(2)));
    }

    @Test
    public void findAll_ofExistingUserIfNoExpenseExist_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Find created epenses + asserts
        //-----------------------------------------------------------------
        List<ExpenseDTO> allExpensesFor = expenseService.findAllExpensesFor(createdUserDTO.getId());
        assertThat(allExpensesFor, is(notNullValue()));
        assertThat(allExpensesFor.size(), is(equalTo(0)));
    }

    //-----------------------------------------------------------------
    // Find all expenses work - AFTER - BEFORE
    //-----------------------------------------------------------------
    @Test
    public void findAll_ofExistingUserBetweenTwoDates_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        LocalDateTime before = LocalDateTime.now().minusSeconds(1);
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        LocalDateTime after = LocalDateTime.now();

        //-----------------------------------------------------------------
        // Find created epenses + asserts
        //-----------------------------------------------------------------
        List<ExpenseDTO> allExpensesFor = expenseService.findAllExpensesAfterBefore(createdUserDTO.getId(), before, after);
        assertThat(allExpensesFor, is(notNullValue()));
        assertThat(allExpensesFor.size(), is(equalTo(7)));
    }

    //-----------------------------------------------------------------
    // Find all expenses work - AFTER a date
    //-----------------------------------------------------------------
    @Test
    public void findAll_ofExistingUserBetweenAfterADate_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        LocalDateTime startDate = LocalDateTime.now().minusSeconds(1);
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Find created epenses + asserts after a date
        //-----------------------------------------------------------------
        List<ExpenseDTO> allExpensesFor = expenseService.findAllExpensesAfter(createdUserDTO.getId(), startDate);
        assertThat(allExpensesFor, is(notNullValue()));
        assertThat(allExpensesFor.size(), is(equalTo(7)));
    }

    //-----------------------------------------------------------------
    // Find all expenses work - BEFORE a date
    //-----------------------------------------------------------------
    @Test
    public void findAll_ofExistingUserBetweenBeforeADate_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        LocalDateTime endDate = LocalDateTime.now();

        //-----------------------------------------------------------------
        // Find created epenses + asserts after a date
        //-----------------------------------------------------------------
        List<ExpenseDTO> allExpensesFor = expenseService.findAllExpensesBefore(createdUserDTO.getId(), endDate);
        assertThat(allExpensesFor, is(notNullValue()));
        assertThat(allExpensesFor.size(), is(equalTo(7)));
    }

    //-----------------------------------------------------------------
    // Find all expenses work - With a category id
    //-----------------------------------------------------------------
    @Test
    public void findAll_ofExistingUserWithCategoryId_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        CategoryDTO categoryDTOTwo = new CategoryDTOBuilder().withColor("#eee").withName("name2").build();
        CategoryDTO createdCategoryDTOTwo = categoryService.create(categoryDTOTwo, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        LocalDateTime endDate = LocalDateTime.now();
        expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTOTwo).withSpentDate(LocalDateTime.now()).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Find created epenses + asserts after a date
        //-----------------------------------------------------------------
        List<ExpenseDTO> allExpensesFor = expenseService.findAllExpensesBefore(createdUserDTO.getId(), endDate);
        assertThat(allExpensesFor, is(notNullValue()));
        assertThat(allExpensesFor.size(), is(equalTo(2)));
    }
}
