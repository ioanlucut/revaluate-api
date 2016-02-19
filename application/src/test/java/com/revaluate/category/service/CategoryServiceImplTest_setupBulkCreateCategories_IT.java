package com.revaluate.category.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(firstTimeCreatedCategories).isNotNull();
        assertThat(firstTimeCreatedCategories).hasSameSizeAs(categoryDTOs);

        List<CategoryDTO> secondTimeCreatedCategories = categoryService.setupBulkCreateCategories(categoryDTOs, createdUserDTO.getId());
        assertThat(secondTimeCreatedCategories).isNotNull();
        assertThat(secondTimeCreatedCategories).hasSameSizeAs(categoryDTOs);

        assertThat(firstTimeCreatedCategories.size()).isEqualTo(secondTimeCreatedCategories.size());
    }

}
