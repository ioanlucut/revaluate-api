package com.revaluate.category.service;

import com.revaluate.AbstractIntegrationTests;
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

public class CategoryServiceImplTest_bulkDelete_IT extends AbstractIntegrationTests {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void bulkDelete_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        int firstSize = categoryService.findAllCategoriesFor(createdUserDTO.getId()).size();

        //-----------------------------------------------------------------
        // Create categories first
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name1").build();
        CategoryDTO categoryDTOB = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name2").build();
        List<CategoryDTO> categoryDTOs = Arrays.asList(categoryDTO, categoryDTOB);
        List<CategoryDTO> all = categoryService.bulkCreate(categoryDTOs, createdUserDTO.getId());

        int creationSize = categoryService.findAllCategoriesFor(createdUserDTO.getId()).size();
        assertThat(firstSize).isEqualTo(creationSize - categoryDTOs.size());

        //-----------------------------------------------------------------
        // Delete categories
        //-----------------------------------------------------------------
        categoryService.bulkDelete(all, createdUserDTO.getId());

        int postBulkDeleteSize = categoryService.findAllCategoriesFor(createdUserDTO.getId()).size();
        assertThat(postBulkDeleteSize).isEqualTo(firstSize);
    }

    @Test
    public void bulkDelete_invalidData_validationWorksOk() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        exception.expect(ConstraintViolationException.class);
        categoryService.bulkDelete(null, createdUserDTO.getId());

        exception.expect(ConstraintViolationException.class);
        categoryService.bulkDelete(Arrays.asList(new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("na").build(), new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name2").build()), createdUserDTO.getId());

        exception.expect(ConstraintViolationException.class);
        categoryService.bulkDelete(Collections.singletonList(new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build()), createdUserDTO.getId());

        exception.expect(ConstraintViolationException.class);
        List<CategoryDTO> tooManyCategoryDTOs = new ArrayList<>(CategoryService.MAX_SIZE_LIST + 1);
        Collections.fill(tooManyCategoryDTOs, new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build());
        categoryService.bulkDelete(tooManyCategoryDTOs, createdUserDTO.getId());
    }

    @Test
    public void bulkDelete_tryToDeleteNonExistingCategory_exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create categories first
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name1").build();
        List<CategoryDTO> categoryDTOs = Collections.singletonList(categoryDTO);
        List<CategoryDTO> all = categoryService.bulkCreate(categoryDTOs, createdUserDTO.getId());

        CategoryDTO categoryDTOWhichDoesNotExist = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name2").withId(99999).build();
        all.add(categoryDTOWhichDoesNotExist);

        exception.expect(ConstraintViolationException.class);
        categoryService.bulkDelete(all, createdUserDTO.getId());
    }

}
