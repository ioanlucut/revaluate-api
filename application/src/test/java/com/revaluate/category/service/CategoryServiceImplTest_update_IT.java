package com.revaluate.category.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.category.exception.CategoryException;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import org.joda.money.CurrencyUnit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class CategoryServiceImplTest_update_IT extends AbstractIntegrationTests {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void update_validDetails_ok() throws Exception {
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
        // Create category updated DTO with same ID as previous
        //-----------------------------------------------------------------
        CategoryDTO categoryToUpdateDTO = new CategoryDTOBuilder().withId(createdCategoryDTO.getId()).withColor(SECOND_VALID_COLOR).withName("noname").build();

        //-----------------------------------------------------------------
        // Update the category
        //-----------------------------------------------------------------
        CategoryDTO updatedCategoryDTO = categoryService.update(categoryToUpdateDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assertions
        //-----------------------------------------------------------------
        assertThat(updatedCategoryDTO, is(notNullValue()));
        assertThat(updatedCategoryDTO.getColor(), not(equalTo(createdCategoryDTO.getColor())));
        assertThat(updatedCategoryDTO.getColor().getColor(), is(equalTo(categoryToUpdateDTO.getColor().getColor())));

        assertThat(updatedCategoryDTO.getName(), not(equalTo(createdCategoryDTO.getName())));
        assertThat(updatedCategoryDTO.getName(), is(equalTo(categoryToUpdateDTO.getName())));
        assertThat(updatedCategoryDTO.getId(), not(equalTo(0)));
        assertThat(updatedCategoryDTO.getId(), equalTo(createdCategoryDTO.getId()));
    }

    @Test(expected = CategoryException.class)
    public void update_inValidCategoryId_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category updated DTO with same ID as previous
        //-----------------------------------------------------------------
        CategoryDTO categoryToUpdateDTO = new CategoryDTOBuilder().withId(99999).withColor(SECOND_VALID_COLOR).withName("noname").build();

        //-----------------------------------------------------------------
        // Update the category
        //-----------------------------------------------------------------
        categoryService.update(categoryToUpdateDTO, createdUserDTO.getId());
    }

    @Test(expected = CategoryException.class)
    public void update_otherUsersCategory_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO("xx@xx.xxxC");

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
        UserDTO secondCreatedUserDTO = createUserDTO("xx@xx.xxxD", CurrencyUnit.AUD.getCode());

        //-----------------------------------------------------------------
        // Create category updated DTO with same ID as previous
        //-----------------------------------------------------------------
        CategoryDTO categoryToUpdateDTO = new CategoryDTOBuilder().withId(categoryToCreateDTO.getId()).withColor(SECOND_VALID_COLOR).withName("noname").build();

        //-----------------------------------------------------------------
        // Update the category of a different user
        //-----------------------------------------------------------------
        categoryService.update(categoryToUpdateDTO, secondCreatedUserDTO.getId());
    }

    @Test
    public void update_twoCategoriesWithSameName_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category updated DTO with same ID as previous
        //-----------------------------------------------------------------
        CategoryDTO firstCategoryToCreateDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name1").build();
        CategoryDTO secondCategoryToCreateDTO = new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("name2").build();

        //-----------------------------------------------------------------
        // Create first category
        //-----------------------------------------------------------------
        CategoryDTO firstCategoryDTO = categoryService.create(firstCategoryToCreateDTO, createdUserDTO.getId());
        //-----------------------------------------------------------------
        // Create second category
        //-----------------------------------------------------------------
        categoryService.create(secondCategoryToCreateDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Try to set the second category
        //-----------------------------------------------------------------
        firstCategoryDTO.setName(secondCategoryToCreateDTO.getName());

        //-----------------------------------------------------------------
        // Update the first category with the name of the secnd one - should break.
        //-----------------------------------------------------------------
        exception.expect(CategoryException.class);
        categoryService.update(firstCategoryDTO, createdUserDTO.getId());
    }

    @Test
    public void update_twoCategoriesWithSameNameButSecondWithUppercase_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category updated DTO with same ID as previous
        //-----------------------------------------------------------------
        CategoryDTO firstCategoryToCreateDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name1").build();
        CategoryDTO secondCategoryToCreateDTO = new CategoryDTOBuilder().withColor(SECOND_VALID_COLOR).withName("name2").build();

        //-----------------------------------------------------------------
        // Create first category
        //-----------------------------------------------------------------
        CategoryDTO firstCategoryDTO = categoryService.create(firstCategoryToCreateDTO, createdUserDTO.getId());
        //-----------------------------------------------------------------
        // Create second category
        //-----------------------------------------------------------------
        categoryService.create(secondCategoryToCreateDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Try to set the second category
        //-----------------------------------------------------------------
        firstCategoryDTO.setName(secondCategoryToCreateDTO.getName().toUpperCase());

        //-----------------------------------------------------------------
        // Update the first category with the name of the secnd one - should break.
        //-----------------------------------------------------------------
        exception.expect(CategoryException.class);
        categoryService.update(firstCategoryDTO, createdUserDTO.getId());
    }

}
