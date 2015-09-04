package com.revaluate.category.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.persistence.User;
import com.revaluate.category.exception.CategoryException;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.domain.goal.GoalDTO;
import com.revaluate.domain.goal.GoalDTOBuilder;
import com.revaluate.domain.goal.GoalTarget;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class CategoryServiceImplTest_remove_IT extends AbstractIntegrationTests {

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
        assertThat(categoryDTO.getColor().getColor(), equalTo(createdCategoryDTO.getColor().getColor()));
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
    public void remove_removeACategoryWithExpensesUsingThisCategory_removesEverything() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());
        assertThat(categoryService.findAllCategoriesFor(userDTO.getId()).size(), is(1));

        //-----------------------------------------------------------------
        // Create expense
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusYears(3)).build();
        ExpenseDTO createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTO.setId(createdExpenseDTO.getId());
        assertThat(expenseService.findAllExpensesWithCategoryIdFor(createdCategoryDTO.getId(), userDTO.getId()).size(), is(1));

        categoryService.remove(createdCategoryDTO.getId(), userDTO.getId());
        assertThat(expenseService.findAllExpensesWithCategoryIdFor(createdCategoryDTO.getId(), userDTO.getId()).size(), is(0));
        assertThat(categoryService.findAllCategoriesFor(userDTO.getId()).size(), is(0));
    }

    @Test
    public void remove_removeACategoryWithGoalsUsingThisCategory_removesEverything() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());
        assertThat(categoryService.findAllCategoriesFor(userDTO.getId()).size(), is(1));

        //-----------------------------------------------------------------
        // Create goal
        //-----------------------------------------------------------------
        GoalDTO goalDTO = new GoalDTOBuilder()
                .withValue(0.01)
                .withGoalTarget(GoalTarget.MORE_THAN)
                .withCategory(createdCategoryDTO)
                .withStartDate(LocalDateTime.now().minusYears(3))
                .withEndDate(LocalDateTime.now())
                .build();

        goalService.create(goalDTO, createdUserDTO.getId());
        assertThat(goalService.findAllGoalsFor(createdUserDTO.getId()).size(), is(1));

        categoryService.remove(createdCategoryDTO.getId(), userDTO.getId());
        assertThat(goalService.findAllGoalsFor(createdUserDTO.getId()).size(), is(0));
    }
}
