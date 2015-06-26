package com.revaluate.category.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class CategoryServiceImplTest_setupBulkCreateCategories_IT extends AbstractIntegrationTests {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void setupBulkCreateCategories_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name1").build();
        CategoryDTO categoryDTOB = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name2").build();
        List<CategoryDTO> categoryDTOs = Arrays.asList(categoryDTO, categoryDTOB);

        //-----------------------------------------------------------------
        // Create first time
        //-----------------------------------------------------------------
        List<CategoryDTO> firstTimeCreatedCategories = categoryService.bulkCreate(categoryDTOs, createdUserDTO.getId());
        assertThat(firstTimeCreatedCategories, is(notNullValue()));
        assertThat(firstTimeCreatedCategories.size(), is(categoryDTOs.size()));

        List<CategoryDTO> secondTimeCreatedCategories = categoryService.setupBulkCreateCategories(categoryDTOs, createdUserDTO.getId());
        assertThat(secondTimeCreatedCategories, is(notNullValue()));
        assertThat(secondTimeCreatedCategories.size(), is(categoryDTOs.size()));

        assertThat(firstTimeCreatedCategories.size(), is(not(equalTo(secondTimeCreatedCategories))));
    }

}
