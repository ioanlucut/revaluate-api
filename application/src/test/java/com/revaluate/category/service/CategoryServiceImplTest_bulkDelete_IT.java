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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name1").build();
        CategoryDTO categoryDTOB = new CategoryDTOBuilder().withColor("#eee").withName("name2").build();
        List<CategoryDTO> categoryDTOs = Arrays.asList(categoryDTO, categoryDTOB);
        List<CategoryDTO> all = categoryService.bulkCreate(categoryDTOs, createdUserDTO.getId());

        int creationSize = categoryService.findAllCategoriesFor(createdUserDTO.getId()).size();
        assertThat(firstSize, is(equalTo(creationSize - categoryDTOs.size())));

        //-----------------------------------------------------------------
        // Delete categories
        //-----------------------------------------------------------------
        categoryService.bulkDelete(all, createdUserDTO.getId());

        int postBulkDeleteSize = categoryService.findAllCategoriesFor(createdUserDTO.getId()).size();
        assertThat(postBulkDeleteSize, is(equalTo(firstSize)));
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
        categoryService.bulkDelete(Arrays.asList(new CategoryDTOBuilder().withColor("#eee").withName("na").build(), new CategoryDTOBuilder().withColor("#eee").withName("name2").build()), createdUserDTO.getId());

        exception.expect(ConstraintViolationException.class);
        categoryService.bulkDelete(Collections.singletonList(new CategoryDTOBuilder().withColor("#eee").withName("name").build()), createdUserDTO.getId());

        exception.expect(ConstraintViolationException.class);
        List<CategoryDTO> tooManyCategoryDTOs = new ArrayList<>(CategoryService.MAX_SIZE_LIST + 1);
        Collections.fill(tooManyCategoryDTOs, new CategoryDTOBuilder().withColor("#eee").withName("name").build());
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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name1").build();
        List<CategoryDTO> categoryDTOs = Collections.singletonList(categoryDTO);
        List<CategoryDTO> all = categoryService.bulkCreate(categoryDTOs, createdUserDTO.getId());

        CategoryDTO categoryDTOWhichDoesNotExist = new CategoryDTOBuilder().withColor("#eee").withName("name2").withId(99999).build();
        all.add(categoryDTOWhichDoesNotExist);

        exception.expect(CategoryException.class);
        categoryService.bulkDelete(all, createdUserDTO.getId());
    }

}
