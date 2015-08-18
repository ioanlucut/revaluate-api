package com.revaluate.expense.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.category.service.CategoryService;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.importer.profile.ExpenseCategoryMatchingProfileDTO;
import com.revaluate.domain.importer.profile.ExpenseCategoryMatchingProfileDTOBuilder;
import com.revaluate.domain.importer.profile.ExpensesImportDTO;
import com.revaluate.domain.importer.profile.MintExpenseProfileDTO;
import com.revaluate.expense.exception.ExpenseException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        ExpenseCategoryMatchingProfileDTO expenseCategoryMatchingProfileDTOA = new ExpenseCategoryMatchingProfileDTOBuilder()
                .withCategoryCandidateName("Home Insurance")
                .withCategoryDTO(createdCategoryDTO)
                .build();
        ExpenseCategoryMatchingProfileDTO expenseCategoryMatchingProfileDTOB = new ExpenseCategoryMatchingProfileDTOBuilder()
                .withCategoryCandidateName("Movies & DVDs")
                .withCategoryDTO(createdCategoryDTOB)
                .build();
        List<ExpenseCategoryMatchingProfileDTO> expenseCategoryMatchingProfileDTOs = Arrays.asList(expenseCategoryMatchingProfileDTOA, expenseCategoryMatchingProfileDTOB);

        //-----------------------------------------------------------------
        // Parse and analyse
        //-----------------------------------------------------------------
        ExpensesImportDTO expensesImportDTO = expenseImportService.parseAndAnalyse(inputStream, expenseProfileDTO);

        //-----------------------------------------------------------------
        // Assert the parse result
        //-----------------------------------------------------------------
        assertThat(expensesImportDTO, is(notNullValue()));
        assertThat(expensesImportDTO.getExpenseDTOs().size(), is(equalTo(2)));
        assertThat(expensesImportDTO.getTotalCategoriesFound(), is(equalTo(2)));
        assertThat(expenseCategoryMatchingProfileDTOs, is(notNullValue()));
        assertThat(expenseCategoryMatchingProfileDTOs, is(notNullValue()));
        assertThat(expenseCategoryMatchingProfileDTOs.size(), is(equalTo(2)));
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

        //-----------------------------------------------------------------
        // Parse and analyse
        //-----------------------------------------------------------------
        ExpensesImportDTO expensesImportDTO = expenseImportService.parseAndAnalyse(inputStream, expenseProfileDTO);

        //-----------------------------------------------------------------
        // Assert the parse result
        //-----------------------------------------------------------------
        assertThat(expensesImportDTO, is(notNullValue()));
        assertThat(expensesImportDTO.getExpenseDTOs().size(), is(equalTo(2)));
        assertThat(expensesImportDTO.getTotalCategoriesFound(), is(equalTo(1)));
        assertThat(expensesImportDTO.getExpenseCategoryMatchingProfileDTOs(), is(notNullValue()));
        assertThat(expensesImportDTO.getExpenseCategoryMatchingProfileDTOs(), is(notNullValue()));
        assertThat(expensesImportDTO.getExpenseCategoryMatchingProfileDTOs().size(), is(equalTo(1)));

        //-----------------------------------------------------------------
        // Expense profile - setup
        //-----------------------------------------------------------------
        expensesImportDTO
                .getExpenseCategoryMatchingProfileDTOs()
                .stream()
                .forEach(expenseCategoryMatchingProfileDTO -> {
                    expenseCategoryMatchingProfileDTO.setCategoryDTO(createdCategoryDTO);
                });

        List<ExpenseDTO> expenseDTOs = expenseImportService.importExpenses(expensesImportDTO, createdUserDTO.getId());
        List<ExpenseDTO> allExpensesFor = expenseService.findAllExpensesFor(createdUserDTO.getId(), Optional.empty());

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

        //-----------------------------------------------------------------
        // Parse and analyse
        //-----------------------------------------------------------------
        ExpensesImportDTO expensesImportDTO = expenseImportService.parseAndAnalyse(inputStream, expenseProfileDTO);

        //-----------------------------------------------------------------
        // Assert the parse result
        //-----------------------------------------------------------------
        assertThat(expensesImportDTO, is(notNullValue()));
        assertThat(expensesImportDTO.getExpenseDTOs().size(), is(equalTo(2)));
        assertThat(expensesImportDTO.getExpenseCategoryMatchingProfileDTOs(), is(notNullValue()));
        assertThat(expensesImportDTO.getExpenseCategoryMatchingProfileDTOs().size(), is(equalTo(2)));

        //-----------------------------------------------------------------
        // Expense profile - clear list and populate with wrong categories
        //-----------------------------------------------------------------
        expensesImportDTO.getExpenseCategoryMatchingProfileDTOs().clear();

        ExpenseCategoryMatchingProfileDTO expenseCategoryMatchingProfileDTOA = new ExpenseCategoryMatchingProfileDTOBuilder()
                .withCategoryCandidateName("Home InsuranceX")
                .withCategoryDTO(createdCategoryDTO)
                .build();
        ExpenseCategoryMatchingProfileDTO expenseCategoryMatchingProfileDTOB = new ExpenseCategoryMatchingProfileDTOBuilder()
                .withCategoryCandidateName("Movies & DVDsY")
                .withCategoryDTO(createdCategoryDTO)
                .build();
        List<ExpenseCategoryMatchingProfileDTO> expenseCategoryMatchingProfileDTOs = Arrays.asList(expenseCategoryMatchingProfileDTOA, expenseCategoryMatchingProfileDTOB);

        //-----------------------------------------------------------------
        // Update expenses import (with wrong matching categories)
        //-----------------------------------------------------------------
        expensesImportDTO.setExpenseCategoryMatchingProfileDTOs(expenseCategoryMatchingProfileDTOs);

        exception.expect(ExpenseException.class);
        exception.expectMessage("Not all categories have a match");
        expenseImportService.importExpenses(expensesImportDTO, createdUserDTO.getId());
    }

    @Test
    public void importExpenses_invalidCategoriesCountSentBack_ok() throws Exception {
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

        //-----------------------------------------------------------------
        // Parse and analyse
        //-----------------------------------------------------------------
        ExpensesImportDTO expensesImportDTO = expenseImportService.parseAndAnalyse(inputStream, expenseProfileDTO);

        //-----------------------------------------------------------------
        // Assert the parse result
        //-----------------------------------------------------------------
        assertThat(expensesImportDTO, is(notNullValue()));
        assertThat(expensesImportDTO.getExpenseDTOs().size(), is(equalTo(2)));
        assertThat(expensesImportDTO.getExpenseCategoryMatchingProfileDTOs(), is(notNullValue()));
        assertThat(expensesImportDTO.getExpenseCategoryMatchingProfileDTOs().size(), is(equalTo(2)));

        //-----------------------------------------------------------------
        // Expense profile - clear list and populate with wrong categories
        //-----------------------------------------------------------------
        expensesImportDTO.getExpenseCategoryMatchingProfileDTOs().clear();

        ExpenseCategoryMatchingProfileDTO expenseCategoryMatchingProfileDTOA = new ExpenseCategoryMatchingProfileDTOBuilder()
                .withCategoryCandidateName("Home Insurance")
                .withCategoryDTO(createdCategoryDTO)
                .build();
        expensesImportDTO.setExpenseCategoryMatchingProfileDTOs(Collections.singletonList(expenseCategoryMatchingProfileDTOA));

        exception.expect(ExpenseException.class);
        expenseImportService.importExpenses(expensesImportDTO, createdUserDTO.getId());
    }

    @Test
    public void importExpenses_validDetailsButNotSelected_ok() throws Exception {
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

        //-----------------------------------------------------------------
        // Parse and analyse
        //-----------------------------------------------------------------
        ExpensesImportDTO expensesImportDTO = expenseImportService.parseAndAnalyse(inputStream, expenseProfileDTO);

        //-----------------------------------------------------------------
        // Assert the parse result
        //-----------------------------------------------------------------
        assertThat(expensesImportDTO, is(notNullValue()));
        assertThat(expensesImportDTO.getExpenseDTOs().size(), is(equalTo(2)));
        assertThat(expensesImportDTO.getTotalCategoriesFound(), is(equalTo(2)));
        assertThat(expensesImportDTO.getExpenseCategoryMatchingProfileDTOs(), is(notNullValue()));
        assertThat(expensesImportDTO.getExpenseCategoryMatchingProfileDTOs().size(), is(equalTo(2)));

        //-----------------------------------------------------------------
        // Expense profile - setup
        //-----------------------------------------------------------------
        expensesImportDTO
                .getExpenseCategoryMatchingProfileDTOs()
                .stream()
                .forEach(expenseCategoryMatchingProfileDTO -> {
                    expenseCategoryMatchingProfileDTO.setCategoryDTO(createdCategoryDTO);
                });

        expensesImportDTO
                .getExpenseCategoryMatchingProfileDTOs()
                .get(0).setSelected(Boolean.FALSE);

        List<ExpenseDTO> expenseDTOs = expenseImportService.importExpenses(expensesImportDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert imported expenses
        //-----------------------------------------------------------------
        assertThat(expenseDTOs, is(notNullValue()));
        assertThat(expenseDTOs.size(), is(equalTo(1)));
    }

}
