package com.revaluate.category.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.persistence.User;
import com.revaluate.category.exception.CategoryException;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import org.joda.money.CurrencyUnit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

public class CategoryServiceImplTestIT extends AbstractIntegrationTests {

    @Autowired
    private CategoryService categoryService;

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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
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
        CategoryDTO categoryToCreateDTO = new CategoryDTOBuilder().withColor("#fff").withName("noname").build();

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

    @Test
    public void create_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name1").build();
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
        assertThat(user.getCategories().size(), is(equalTo(1)));
        assertThat(user.getCategories().get(0).getId(), is(equalTo(createdCategoryDTO.getId())));
        assertThat(user.getCategories().get(0).getName(), is(equalTo(createdCategoryDTO.getName())));
        assertThat(user.getCategories().get(0).getColor(), is(equalTo(createdCategoryDTO.getColor())));
    }

    @Test
    public void bulkCreate_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name1").build();
        CategoryDTO categoryDTOB = new CategoryDTOBuilder().withColor("#eee").withName("name2").build();
        List<CategoryDTO> categoryDTOs = Arrays.asList(categoryDTO, categoryDTOB);

        List<CategoryDTO> all = categoryService.bulkCreate(categoryDTOs, createdUserDTO.getId());
        assertThat(all, is(notNullValue()));
        assertThat(all.size(), is(categoryDTOs.size()));
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
        categoryService.bulkCreate(Arrays.asList(new CategoryDTOBuilder().withColor("#eee").withName("na").build(), new CategoryDTOBuilder().withColor("#eee").withName("name2").build()), createdUserDTO.getId());

        exception.expect(ConstraintViolationException.class);
        categoryService.bulkCreate(Collections.singletonList(new CategoryDTOBuilder().withColor("#eee").withName("name").build()), createdUserDTO.getId());

        exception.expect(ConstraintViolationException.class);
        List<CategoryDTO> tooManyCategoryDTOs = new ArrayList<>(CategoryService.MAX_SIZE_LIST + 1);
        Collections.fill(tooManyCategoryDTOs, new CategoryDTOBuilder().withColor("#eee").withName("name").build());
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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name1").build();
        categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Try to create a list of categories while one exists
        //-----------------------------------------------------------------
        CategoryDTO categoryDTODuplicated = new CategoryDTOBuilder().withColor("#eee").withName("name1").build();
        CategoryDTO categoryDTOB = new CategoryDTOBuilder().withColor("#eee").withName("name2").build();
        List<CategoryDTO> categoryDTOs = Arrays.asList(categoryDTODuplicated, categoryDTOB);

        exception.expect(CategoryException.class);
        categoryService.bulkCreate(categoryDTOs, createdUserDTO.getId());
    }

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

    @Test
    public void create_twoCategoriesWithSameColor_worksOk() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category - 1
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name1").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        assertThat(createdCategoryDTO, is(notNullValue()));
        assertThat(createdCategoryDTO.getId(), not(equalTo(0)));
        assertThat(categoryDTO.getColor(), equalTo(createdCategoryDTO.getColor()));
        assertThat(categoryDTO.getName(), equalTo(createdCategoryDTO.getName()));

        //-----------------------------------------------------------------
        // Create category - 2
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name2").build();
        createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        assertThat(createdCategoryDTO, is(notNullValue()));
        assertThat(createdCategoryDTO.getId(), not(equalTo(0)));
        assertThat(categoryDTO.getColor(), equalTo(createdCategoryDTO.getColor()));
        assertThat(categoryDTO.getName(), equalTo(createdCategoryDTO.getName()));

        //-----------------------------------------------------------------
        // Check user with category added
        //-----------------------------------------------------------------
        User user = userRepository.findOne(userDTO.getId());

        assertThat(user, is(notNullValue()));
        assertThat(user.getCategories().size(), is(equalTo(2)));
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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("12").withName("name1").build();
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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create category - 2
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        categoryService.create(categoryDTO, createdUserDTO.getId());
    }

    @Test
    public void update_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create category updated DTO with same ID as previous
        //-----------------------------------------------------------------
        CategoryDTO categoryToUpdateDTO = new CategoryDTOBuilder().withId(createdCategoryDTO.getId()).withColor("#fff").withName("noname").build();

        //-----------------------------------------------------------------
        // Update the category
        //-----------------------------------------------------------------
        CategoryDTO updatedCategoryDTO = categoryService.update(categoryToUpdateDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assertions
        //-----------------------------------------------------------------
        assertThat(updatedCategoryDTO, is(notNullValue()));
        assertThat(updatedCategoryDTO.getColor(), not(equalTo(createdCategoryDTO.getColor())));
        assertThat(updatedCategoryDTO.getColor(), is(equalTo(categoryToUpdateDTO.getColor())));

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
        CategoryDTO categoryToUpdateDTO = new CategoryDTOBuilder().withId(99999).withColor("#fff").withName("noname").build();

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
        CategoryDTO categoryToCreateDTO = new CategoryDTOBuilder().withColor("#fff").withName("noname").build();

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
        CategoryDTO categoryToUpdateDTO = new CategoryDTOBuilder().withId(categoryToCreateDTO.getId()).withColor("#fff").withName("noname").build();

        //-----------------------------------------------------------------
        // Update the category of a different user
        //-----------------------------------------------------------------
        categoryService.update(categoryToUpdateDTO, secondCreatedUserDTO.getId());
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
        CategoryDTO categoryToCreateDTO = new CategoryDTOBuilder().withColor("#fff").withName("noname").build();
        categoryService.create(categoryToCreateDTO, createdUserDTO.getId());
        categoryToCreateDTO = new CategoryDTOBuilder().withColor("#fff").withName("noname2").build();
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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name1").build();
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
        assertThat(user.getCategories().size(), is(equalTo(1)));

        //-----------------------------------------------------------------
        // Remove the category
        //-----------------------------------------------------------------
        categoryService.remove(createdCategoryDTO.getId(), createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Check user with category removed
        //-----------------------------------------------------------------
        user = userRepository.findOne(userDTO.getId());

        assertThat(user, is(notNullValue()));
        assertThat(user.getCategories().size(), is(equalTo(0)));
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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name1").build();
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
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name1").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create category 2
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name2").build();
        createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Check user with 2 categories added
        //-----------------------------------------------------------------
        User user = userRepository.findOne(userDTO.getId());

        assertThat(user, is(notNullValue()));
        assertThat(user.getCategories().size(), is(equalTo(2)));

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
}
