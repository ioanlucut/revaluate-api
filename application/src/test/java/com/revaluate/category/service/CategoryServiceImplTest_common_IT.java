package com.revaluate.category.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.persistence.User;
import com.revaluate.category.exception.CategoryException;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.expense.service.ExpenseService;
import org.joda.money.CurrencyUnit;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class CategoryServiceImplTest_common_IT extends AbstractIntegrationTests {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ExpenseService expenseService;

    @Test
    public void isUniqueName_validName_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        assertThat(categoryService.isUnique("name", createdUserDTO.getId()), is(true));
    }

    @Test
    public void isUniqueName_existingName_isFalse() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        categoryService.create(categoryDTO, createdUserDTO.getId());

        assertThat(categoryService.isUnique(categoryDTO.getName(), createdUserDTO.getId()), is(false));
    }

    @Test
    public void isUniqueName_existingNameForAnotherUser_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO("xx@xx.xxxA");

        //-----------------------------------------------------------------
        // Create category updated DTO with same ID as previous
        //-----------------------------------------------------------------
        CategoryDTO categoryToCreateDTO = new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("noname").build();

        //-----------------------------------------------------------------
        // Update the category
        //-----------------------------------------------------------------
        categoryService.create(categoryToCreateDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create the second first user
        //-----------------------------------------------------------------
        UserDTO secondCreatedUserDTO = createUserDTO("xx@xx.xxxB", CurrencyUnit.AUD.getCode());

        //-----------------------------------------------------------------
        // Check if the other user category name is unique
        //-----------------------------------------------------------------
        boolean unique = categoryService.isUnique(categoryToCreateDTO.getName(), secondCreatedUserDTO.getId());
        assertThat(unique, is(true));
    }

    //-----------------------------------------------------------------
    // Find all categories work
    //-----------------------------------------------------------------
    @Test
    public void findAll_ofExistingUser_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create two categories
        //-----------------------------------------------------------------
        CategoryDTO categoryToCreateDTO = new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("noname").build();
        categoryService.create(categoryToCreateDTO, createdUserDTO.getId());
        categoryToCreateDTO = new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("noname2").build();
        categoryService.create(categoryToCreateDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Find created categories + asserts
        //-----------------------------------------------------------------
        List<CategoryDTO> allCategoriesFor = categoryService.findAllCategoriesFor(createdUserDTO.getId());
        assertThat(allCategoriesFor, is(notNullValue()));
        assertThat(allCategoriesFor.size(), is(equalTo(2)));
    }

    @Test
    public void findAll_ofExistingUserIfNoPresent_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Find created categories + asserts
        //-----------------------------------------------------------------
        List<CategoryDTO> allCategoriesFor = categoryService.findAllCategoriesFor(createdUserDTO.getId());
        assertThat(allCategoriesFor, is(notNullValue()));
        assertThat(allCategoriesFor.size(), is(equalTo(0)));
    }

    @Test
    public void remove_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name1").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        assertThat(createdCategoryDTO, is(notNullValue()));
        assertThat(createdCategoryDTO.getId(), not(equalTo(0)));
        assertThat(categoryDTO.getColor(), equalTo(createdCategoryDTO.getColor()));
        assertThat(categoryDTO.getName(), equalTo(createdCategoryDTO.getName()));

        //-----------------------------------------------------------------
        // Check user with category added
        //-----------------------------------------------------------------
        User user = userRepository.findOne(userDTO.getId());

        assertThat(user, is(notNullValue()));
        assertThat(categoryService.findAllCategoriesFor(user.getId()).size(), is(equalTo(1)));

        //-----------------------------------------------------------------
        // Remove the category
        //-----------------------------------------------------------------
        categoryService.remove(createdCategoryDTO.getId(), createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Check user with category removed
        //-----------------------------------------------------------------
        user = userRepository.findOne(userDTO.getId());

        assertThat(user, is(notNullValue()));
        assertThat(categoryService.findAllCategoriesFor(user.getId()).size(), is(equalTo(0)));
    }

    @Test(expected = CategoryException.class)
    public void remove_categoryInvalid_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category 1
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name1").build();
        categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Remove invalid category id
        //-----------------------------------------------------------------
        categoryService.remove(99999, createdUserDTO.getId());
    }

    @Test
    public void remove_removeAUserRemovesItsCategories_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category 1
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name1").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create category 2
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name2").build();
        categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Check user with 2 categories added
        //-----------------------------------------------------------------
        User user = userRepository.findOne(userDTO.getId());

        assertThat(user, is(notNullValue()));
        assertThat(categoryService.findAllCategoriesFor(user.getId()).size(), is(equalTo(2)));

        //-----------------------------------------------------------------
        // Remove the category
        //-----------------------------------------------------------------
        userService.remove(user.getId());

        //-----------------------------------------------------------------
        // Check user with category removed
        //-----------------------------------------------------------------
        List<User> all = userRepository.findAll();
        assertThat(all.size(), is(equalTo(0)));
    }

    @Test
    public void remove_removeACategoryWithExpensesUsingThisCategory_exceptionThrown() throws Exception {
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

        exception.expect(CategoryException.class);
        categoryService.remove(createdCategoryDTO.getId(), userDTO.getId());
    }
}
