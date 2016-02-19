package com.revaluate.category.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.category.exception.CategoryException;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryServiceImplTest_bulkCreate_IT extends AbstractIntegrationTests {

    @Autowired
    private CategoryService categoryService;


    @Test
    public void bulkCreate_validDetails_ok() throws Exception {
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

        List<CategoryDTO> all = categoryService.bulkCreate(categoryDTOs, createdUserDTO.getId());
        assertThat(all).isNotNull();
        assertThat(all).hasSameSizeAs(categoryDTOs);
    }

    @Test
    public void bulkCreate_invalidData_validationWorksOk() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        exception.expect(ConstraintViolationException.class);
        categoryService.bulkCreate(null, createdUserDTO.getId());

        exception.expect(ConstraintViolationException.class);
        categoryService.bulkCreate(Arrays.asList(new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("na").build(), new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name2").build()), createdUserDTO.getId());

        exception.expect(ConstraintViolationException.class);
        categoryService.bulkCreate(Collections.singletonList(new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build()), createdUserDTO.getId());

        exception.expect(ConstraintViolationException.class);
        List<CategoryDTO> tooManyCategoryDTOs = new ArrayList<>(CategoryService.MAX_SIZE_LIST + 1);
        Collections.fill(tooManyCategoryDTOs, new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build());
        categoryService.bulkCreate(tooManyCategoryDTOs, createdUserDTO.getId());
    }

    @Test
    public void bulkCreate_withNonUniqueCategoryFromList_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category (first time)
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name1").build();
        categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Try to create a list of categories while one exists
        //-----------------------------------------------------------------
        CategoryDTO categoryDTODuplicated = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name1").build();
        CategoryDTO categoryDTOB = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name2").build();
        List<CategoryDTO> categoryDTOs = Arrays.asList(categoryDTODuplicated, categoryDTOB);

        exception.expect(CategoryException.class);
        categoryService.bulkCreate(categoryDTOs, createdUserDTO.getId());
    }
}
