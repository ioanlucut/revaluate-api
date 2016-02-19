package com.revaluate.category.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import org.joda.money.CurrencyUnit;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryServiceImplTest_common_IT extends AbstractIntegrationTests {

    @Test
    public void isUniqueName_validName_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        assertThat(categoryService.isUnique("name", createdUserDTO.getId())).isTrue();
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

        assertThat(categoryService.isUnique(categoryDTO.getName(), createdUserDTO.getId())).isFalse();
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
        assertThat(unique).isTrue();
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
        assertThat(allCategoriesFor).isNotNull();
        assertThat(allCategoriesFor).hasSize(2);
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
        assertThat(allCategoriesFor).isNotNull();
        assertThat(allCategoriesFor).isEmpty();
    }
}
