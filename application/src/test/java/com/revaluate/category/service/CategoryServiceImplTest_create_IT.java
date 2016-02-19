package com.revaluate.category.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.persistence.User;
import com.revaluate.category.exception.CategoryException;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.color.ColorDTOBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

public class CategoryServiceImplTest_create_IT extends AbstractIntegrationTests {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void create_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name1").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        assertThat(createdCategoryDTO).isNotNull();
        assertThat(createdCategoryDTO.getId()).isNotEqualTo(0);
        assertThat(categoryDTO.getColor().getColor()).isEqualTo(createdCategoryDTO.getColor().getColor());
        assertThat(categoryDTO.getName()).isEqualTo(createdCategoryDTO.getName());

        //-----------------------------------------------------------------
        // Check user with category added
        //-----------------------------------------------------------------
        User user = userRepository.findOne(userDTO.getId());

        assertThat(user).isNotNull();
        assertThat(categoryService.findAllCategoriesFor(createdUserDTO.getId()).size()).isEqualTo(1);
        assertThat(categoryService.findAllCategoriesFor(createdUserDTO.getId()).get(0).getId()).isEqualTo(createdCategoryDTO.getId())
        ;
        assertThat(categoryService.findAllCategoriesFor(createdUserDTO.getId()).get(0).getName()).isEqualTo(createdCategoryDTO.getName())
        ;
        assertThat(categoryService.findAllCategoriesFor(createdUserDTO.getId()).get(0).getColor()).isEqualTo(createdCategoryDTO.getColor())
        ;
    }

    @Test
    public void create_twoCategoriesWithSameColor_worksOk() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category - 1
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name1").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        assertThat(createdCategoryDTO).isNotNull();
        assertThat(createdCategoryDTO.getId()).isNotEqualTo(0);
        assertThat(categoryDTO.getColor().getColor()).isEqualTo(createdCategoryDTO.getColor().getColor());
        assertThat(categoryDTO.getName()).isEqualTo(createdCategoryDTO.getName());

        //-----------------------------------------------------------------
        // Create category - 2
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name2").build();
        createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        assertThat(createdCategoryDTO).isNotNull();
        assertThat(createdCategoryDTO.getId()).isNotEqualTo(0);
        assertThat(categoryDTO.getColor().getColor()).isEqualTo(createdCategoryDTO.getColor().getColor());
        assertThat(categoryDTO.getName()).isEqualTo(createdCategoryDTO.getName());

        //-----------------------------------------------------------------
        // Check user with category added
        //-----------------------------------------------------------------
        User user = userRepository.findOne(userDTO.getId());

        assertThat(user).isNotNull();
        assertThat(categoryService.findAllCategoriesFor(createdUserDTO.getId()).size()).isEqualTo(2);
    }

    @Test(expected = ConstraintViolationException.class)
    public void create_validDetailsButWrongColor_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category - with wrong color hex format
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(new ColorDTOBuilder().withColor("wrongFormat").build()).withName("name1").build();
        categoryService.create(categoryDTO, createdUserDTO.getId());
    }

    @Test(expected = CategoryException.class)
    public void create_twoCategoriesWithSameName_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category - 1
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create category - 2
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name").build();
        categoryService.create(categoryDTO, createdUserDTO.getId());
    }

}
