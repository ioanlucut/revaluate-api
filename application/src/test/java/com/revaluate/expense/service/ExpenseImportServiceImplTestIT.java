package com.revaluate.expense.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.importer.profile.ExpenseCategoriesMatchingProfileDTO;
import com.revaluate.domain.importer.profile.ExpenseCategoriesMatchingProfileDTOBuilder;
import com.revaluate.domain.importer.profile.MintExpenseProfileDTO;
import com.revaluate.expense.exception.ExpenseException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;

public class ExpenseImportServiceImplTestIT extends AbstractIntegrationTests {

    @Autowired
    private ExpenseImportService expenseImportService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private CategoryService categoryService;

    @Test
    public void importExpenses_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();
        String exampleString = "\"Date\",\"Description\",\"Original Description\",\"Amount\",\"Transaction Type\",\"Category\",\"Account Name\",\"Labels\",\"Notes\"\n" +
                "\"5/05/2015\",\"Sticky\",\"PaymentTo Sticky9\",\"36.98\",\"debit\",\"Home Insurance\",\"PayPal Account\",\"\",\"\"\n" +
                "\"5/04/2015\",\"Test transaction\",\"Test transaction\",\"123.00\",\"debit\",\"Movies & DVDs\",\"Cash\",\"\",\"This is a note\"";

        InputStream inputStream = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));

        //-----------------------------------------------------------------
        // Create two categories
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        CategoryDTO categoryDTOB = new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("yeaks").build();
        CategoryDTO createdCategoryDTOB = categoryService.create(categoryDTOB, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Expense profile - setup
        //-----------------------------------------------------------------
        MintExpenseProfileDTO expenseProfileDTO = new MintExpenseProfileDTO();
        ExpenseCategoriesMatchingProfileDTO expenseCategoriesMatchingProfileDTO = new ExpenseCategoriesMatchingProfileDTOBuilder().build();
        expenseCategoriesMatchingProfileDTO.getCategoriesMatchingMap().putIfAbsent("Home Insurance", createdCategoryDTO);
        expenseCategoriesMatchingProfileDTO.getCategoriesMatchingMap().putIfAbsent("Movies & DVDs", createdCategoryDTOB);

        List<ExpenseDTO> expenseDTOs = expenseImportService.parseAndAnalyse(inputStream, expenseProfileDTO);
        assertThat(expenseDTOs, is(notNullValue()));
        assertThat(expenseDTOs.size(), is(equalTo(2)));
    }

    @Test
    public void importExpenses_twoOfTheSameCategoryValidDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();
        String exampleString = "\"Date\",\"Description\",\"Original Description\",\"Amount\",\"Transaction Type\",\"Category\",\"Account Name\",\"Labels\",\"Notes\"\n" +
                "\"5/05/2015\",\"Sticky\",\"PaymentTo Sticky9\",\"36.98\",\"debit\",\"Home Insurance\",\"PayPal Account\",\"\",\"\"\n" +
                "\"5/04/2015\",\"Test transaction\",\"Test transaction\",\"123.00\",\"debit\",\"Home Insurance\",\"Cash\",\"\",\"This is a note\"";

        InputStream inputStream = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));

        //-----------------------------------------------------------------
        // Create two categories
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Expense profile - parse
        //-----------------------------------------------------------------
        MintExpenseProfileDTO expenseProfileDTO = new MintExpenseProfileDTO();
        List<ExpenseDTO> expenseDTOs = expenseImportService.parseAndAnalyse(inputStream, expenseProfileDTO);

        //-----------------------------------------------------------------
        // Expense profile - import
        //-----------------------------------------------------------------
        ExpenseCategoriesMatchingProfileDTO expenseCategoriesMatchingProfileDTO = new ExpenseCategoriesMatchingProfileDTOBuilder().build();
        expenseCategoriesMatchingProfileDTO.getCategoriesMatchingMap().putIfAbsent("Home Insurance", createdCategoryDTO);
        expenseImportService.importExpenses(expenseDTOs, expenseCategoriesMatchingProfileDTO, createdUserDTO.getId());
        List<ExpenseDTO> allExpensesFor = expenseService.findAllExpensesFor(createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert imported expenses
        //-----------------------------------------------------------------
        assertThat(allExpensesFor, is(notNullValue()));
        assertThat(allExpensesFor.size(), is(equalTo(2)));

        assertThat(expenseDTOs, is(notNullValue()));
        assertThat(expenseDTOs.size(), is(equalTo(2)));
    }

    @Test
    public void importExpenses_matchingCategoriesDefinedButNotForExistingImportCategories_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();
        String exampleString = "\"Date\",\"Description\",\"Original Description\",\"Amount\",\"Transaction Type\",\"Category\",\"Account Name\",\"Labels\",\"Notes\"\n" +
                "\"5/05/2015\",\"Sticky\",\"PaymentTo Sticky9\",\"36.98\",\"debit\",\"Home Insurance\",\"PayPal Account\",\"\",\"\"\n" +
                "\"5/04/2015\",\"Test transaction\",\"Test transaction\",\"123.00\",\"debit\",\"Movies & DVDs\",\"Cash\",\"\",\"This is a note\"";

        InputStream inputStream = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));

        //-----------------------------------------------------------------
        // Create two categories
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Expense profile - parse
        //-----------------------------------------------------------------
        MintExpenseProfileDTO expenseProfileDTO = new MintExpenseProfileDTO();
        List<ExpenseDTO> expenseDTOs = expenseImportService.parseAndAnalyse(inputStream, expenseProfileDTO);

        //-----------------------------------------------------------------
        // Expense profile - import
        //-----------------------------------------------------------------
        ExpenseCategoriesMatchingProfileDTO expenseCategoriesMatchingProfileDTO = new ExpenseCategoriesMatchingProfileDTOBuilder().build();
        expenseCategoriesMatchingProfileDTO.getCategoriesMatchingMap().putIfAbsent("Home InsuranceX", createdCategoryDTO);
        expenseCategoriesMatchingProfileDTO.getCategoriesMatchingMap().putIfAbsent("Movies Y", createdCategoryDTO);

        exception.expect(ExpenseException.class);
        exception.expectMessage("categories defined from total of");
        expenseImportService.importExpenses(expenseDTOs, expenseCategoriesMatchingProfileDTO, createdUserDTO.getId());
    }

    @Test
    public void importExpenses_invalidCategoryMatcher_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();
        String exampleString = "\"Date\",\"Description\",\"Original Description\",\"Amount\",\"Transaction Type\",\"Category\",\"Account Name\",\"Labels\",\"Notes\"\n" +
                "\"5/05/2015\",\"Sticky\",\"PaymentTo Sticky9\",\"36.98\",\"debit\",\"Home Insurance\",\"PayPal Account\",\"\",\"\"\n" +
                "\"5/04/2015\",\"Test transaction\",\"Test transaction\",\"123.00\",\"debit\",\"Movies & DVDs\",\"Cash\",\"\",\"This is a note\"";

        InputStream inputStream = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));

        //-----------------------------------------------------------------
        // Create two categories
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Expense profile - parse
        //-----------------------------------------------------------------
        MintExpenseProfileDTO expenseProfileDTO = new MintExpenseProfileDTO();
        List<ExpenseDTO> expenseDTOs = expenseImportService.parseAndAnalyse(inputStream, expenseProfileDTO);

        //-----------------------------------------------------------------
        // Expense profile - import
        //-----------------------------------------------------------------
        ExpenseCategoriesMatchingProfileDTO expenseCategoriesMatchingProfileDTO = new ExpenseCategoriesMatchingProfileDTOBuilder().build();
        expenseCategoriesMatchingProfileDTO.getCategoriesMatchingMap().putIfAbsent("Home Insurance", createdCategoryDTO);

        exception.expect(ExpenseException.class);
        exception.expectMessage("categories defined from total of");
        expenseImportService.importExpenses(expenseDTOs, expenseCategoriesMatchingProfileDTO, createdUserDTO.getId());
    }
}