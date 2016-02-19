package com.revaluate.expense.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.expense.exception.ExpenseException;
import org.joda.money.CurrencyUnit;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpenseServiceImplTestIT extends AbstractIntegrationTests {

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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusYears(3)).build();
        ExpenseDTO createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTO.setId(createdExpenseDTO.getId());

        //-----------------------------------------------------------------
        // Assert created expense is ok
        //-----------------------------------------------------------------
        assertThat(createdExpenseDTO).isNotNull();
        assertThat(createdExpenseDTO.getId()).isNotEqualTo(0);
        assertThat(createdExpenseDTO.getCategory()).isEqualTo(expenseDTO.getCategory());
        assertThat(createdExpenseDTO.getDescription()).isEqualTo(expenseDTO.getDescription());
        assertThat(createdExpenseDTO.getValue()).isEqualTo(expenseDTO.getValue());
        assertThat(createdExpenseDTO.getSpentDate().getYear()).isEqualTo(expenseDTO.getSpentDate().getYear());
    }

    @Test
    public void create_testMaxAndMinValues_ok() throws Exception {
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
        // Create expense
        //-----------------------------------------------------------------

        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(0.01).withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        ExpenseDTO createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());

        exception.expect(ConstraintViolationException.class);
        expenseDTO = new ExpenseDTOBuilder().withValue(0.001).withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());

        expenseDTO = new ExpenseDTOBuilder().withValue(111_111_111_111_111_111_11.11).withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());
        assertThat(createdExpenseDTO.getValue()).isEqualTo(expenseDTO.getValue());

        expenseDTO = new ExpenseDTOBuilder().withValue(899_999_999_999_999_999_99.11).withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());
        assertThat(createdExpenseDTO.getValue()).isEqualTo(expenseDTO.getValue());

        exception.expect(ConstraintViolationException.class);
        expenseDTO = new ExpenseDTOBuilder().withValue(999_999_999_999_999_999_99.11).withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());
        assertThat(createdExpenseDTO.getValue()).isEqualTo(expenseDTO.getValue());
    }

    @Test
    public void create_withDifferentPriceCombinations_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        ExpenseDTO expenseDTO;
        ExpenseDTO createdExpenseDTO;

        //-----------------------------------------------------------------
        // Create expenses
        //-----------------------------------------------------------------
        expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusYears(3)).build();
        createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());
        assertThat(createdExpenseDTO.getValue()).isEqualTo(expenseDTO.getValue());

        expenseDTO = new ExpenseDTOBuilder().withValue(9999.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusYears(3)).build();
        createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());
        assertThat(createdExpenseDTO.getValue()).isEqualTo(expenseDTO.getValue());

        expenseDTO = new ExpenseDTOBuilder().withValue(2147483647).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusYears(3)).build();
        createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());
        assertThat(createdExpenseDTO.getValue()).isEqualTo(expenseDTO.getValue());

        expenseDTO = new ExpenseDTOBuilder().withValue(2147483647.99).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusYears(3)).build();
        createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());
        assertThat(createdExpenseDTO.getValue()).isEqualTo(expenseDTO.getValue());
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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
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
        assertThat(createdExpenseDTO.getCategory()).isEqualTo(expenseDTO.getCategory());
        assertThat(secondCreatedExpenseDTO.getCategory()).isEqualTo(expenseDTO.getCategory());
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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
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
        UserDTO firstUserDTO = createUserDTO(TEST_EMAIL);

        //-----------------------------------------------------------------
        // Create second user
        //-----------------------------------------------------------------
        UserDTO secondUserDTO = createUserDTO("xxx@xxx.xxx2", CurrencyUnit.AUD.getCurrencyCode());

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
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
        assertThat(updatedExpenseDTO.getId()).isEqualTo(createdExpenseDTO.getId());
        assertThat(updatedExpenseDTO.getCategory().getColor()).isEqualTo(createdExpenseDTO.getCategory().getColor());
        assertThat(updatedExpenseDTO.getCategory().getName()).isEqualTo(createdExpenseDTO.getCategory().getName());
        assertThat(updatedExpenseDTO.getCategory().getId()).isEqualTo(createdExpenseDTO.getCategory().getId());
        // Only this was updated
        assertThat(updatedExpenseDTO.getValue()).isEqualTo(expenseDTOToUpdate.getValue());
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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
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
        assertThat(updatedExpenseDTO.getId()).isEqualTo(createdExpenseDTO.getId());
        assertThat(updatedExpenseDTO.getCategory().getColor()).isEqualTo(createdExpenseDTO.getCategory().getColor());
        assertThat(updatedExpenseDTO.getCategory().getName()).isEqualTo(createdExpenseDTO.getCategory().getName());
        assertThat(updatedExpenseDTO.getCategory().getId()).isEqualTo(createdExpenseDTO.getCategory().getId());
        assertThat(updatedExpenseDTO.getValue()).isEqualTo(createdExpenseDTO.getValue());
        // Only these was updated
        assertThat(updatedExpenseDTO.getDescription()).isEqualTo(expenseDTOToUpdate.getDescription());
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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
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
        categoryDTO = new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("name2").build();
        createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Expense to update - ONLY description
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTOToUpdate = new ExpenseDTOBuilder().withValue(2.55).withId(expenseDTO.getId()).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        ExpenseDTO updatedExpenseDTO = expenseService.update(expenseDTOToUpdate, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert updated expense is ok, and updated only concerned values
        //-----------------------------------------------------------------
        assertThat(updatedExpenseDTO.getId()).isEqualTo(createdExpenseDTO.getId());
        assertThat(updatedExpenseDTO.getValue()).isEqualTo(createdExpenseDTO.getValue());
        assertThat(updatedExpenseDTO.getDescription()).isEqualTo(expenseDTOToUpdate.getDescription());
        // Only these was updated
        assertThat(updatedExpenseDTO.getCategory().getColor()).isEqualTo(createdCategoryDTO.getColor());
        assertThat(updatedExpenseDTO.getCategory().getName()).isEqualTo(createdCategoryDTO.getName());
        assertThat(updatedExpenseDTO.getCategory().getId()).isEqualTo(createdCategoryDTO.getId());
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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
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
        List<ExpenseDTO> allExpensesFor = expenseService.findAllExpensesFor(createdUserDTO.getId(), Optional.empty());
        assertThat(allExpensesFor).isNotNull();
        assertThat(allExpensesFor.size()).isEqualTo(2);
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
        List<ExpenseDTO> allExpensesFor = expenseService.findAllExpensesFor(createdUserDTO.getId(), Optional.empty());
        assertThat(allExpensesFor).isNotNull();
        assertThat(allExpensesFor.size()).isEqualTo(0);
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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
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
        assertThat(allExpensesFor).isNotNull();
        assertThat(allExpensesFor.size()).isEqualTo(7);
    }

    @Test
    public void findAllExpensesWithCategoryIdAfterBefore_ofExistingUserBetweenTwoDatesAndACategory_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO createdCategoryDTO = categoryService.create(new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build(), createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create second category
        //-----------------------------------------------------------------
        CategoryDTO secondCreatedCategoryDTO = categoryService.create(new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("name2").build(), createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now()).build();
        ExpenseDTO expenseDTOOfTheSecondCategory = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(secondCreatedCategoryDTO).withSpentDate(LocalDateTime.now()).build();

        LocalDateTime before = LocalDateTime.now().minusSeconds(1);
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTOOfTheSecondCategory, createdUserDTO.getId());
        expenseService.create(expenseDTOOfTheSecondCategory, createdUserDTO.getId());
        expenseService.create(expenseDTOOfTheSecondCategory, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseService.create(expenseDTO, createdUserDTO.getId());
        LocalDateTime after = LocalDateTime.now();

        //-----------------------------------------------------------------
        // Find created expenses + asserts (should be only with the first category)
        //-----------------------------------------------------------------
        List<ExpenseDTO> allExpensesFor = expenseService.findAllExpensesWithCategoryIdAfterBefore(createdUserDTO.getId(), createdCategoryDTO.getId(), before, after);
        allExpensesFor.stream().forEach(expenseDTOCandidate -> assertThat(expenseDTOCandidate.getCategory().getName()).isEqualTo(createdCategoryDTO.getName()));
        assertThat(allExpensesFor).isNotNull();
        assertThat(allExpensesFor.size()).isEqualTo(7);
        assertThat(expenseService.findAllExpensesAfterBefore(createdUserDTO.getId(), before, after).size()).isEqualTo(10);
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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
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
        assertThat(allExpensesFor).isNotNull();
        assertThat(allExpensesFor.size()).isEqualTo(7);
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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
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
        assertThat(allExpensesFor).isNotNull();
        assertThat(allExpensesFor.size()).isEqualTo(7);
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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        CategoryDTO categoryDTOTwo = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name2").build();
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
        assertThat(allExpensesFor).isNotNull();
        assertThat(allExpensesFor.size()).isEqualTo(2);
    }
}
