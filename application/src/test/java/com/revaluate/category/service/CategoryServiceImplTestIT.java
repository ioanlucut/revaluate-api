package com.revaluate.category.service;

import com.revaluate.account.domain.UserDTO;
import com.revaluate.account.domain.UserDTOBuilder;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.account.service.UserService;
import com.revaluate.category.domain.CategoryDTO;
import com.revaluate.category.domain.CategoryDTOBuilder;
import com.revaluate.category.exception.CategoryException;
import com.revaluate.category.persistence.CategoryRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class CategoryServiceImplTestIT {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private UserDTO userDTO;
    private CategoryDTO categoryDTO;

    @Before
    @After
    public void tearDown() throws Exception {
        if (userDTO != null) {
            if (userRepository.exists(userDTO.getId())) {
                userRepository.delete(userDTO.getId());
            }
        }
        if (categoryDTO != null) {
            if (categoryRepository.exists(categoryDTO.getId())) {
                categoryRepository.delete(categoryDTO.getId());
            }
        }
    }

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
        categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
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
        UserDTO secondCreatedUserDTO = createUserDTO("xx@xx.xxxB");

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
        categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name1").build();
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
    public void create_twoCategoriesWithSameColor_worksOk() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category - 1
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name1").build();
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

    @Test(expected = CategoryException.class)
    public void create_twoCategoriesWithSameName_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category - 1
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
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
        categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
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
        UserDTO secondCreatedUserDTO = createUserDTO("xx@xx.xxxD");

        //-----------------------------------------------------------------
        // Create category updated DTO with same ID as previous
        //-----------------------------------------------------------------
        CategoryDTO categoryToUpdateDTO = new CategoryDTOBuilder().withId(categoryToCreateDTO.getId()).withColor("#fff").withName("noname").build();

        //-----------------------------------------------------------------
        // Update the category of a different user
        //-----------------------------------------------------------------
        categoryService.update(categoryToUpdateDTO, secondCreatedUserDTO.getId());
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
        categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name1").build();
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
        categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name1").build();
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
        categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name1").build();
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
        userRepository.deleteAll();

        //-----------------------------------------------------------------
        // Check user with category removed
        //-----------------------------------------------------------------
        List<User> all = userRepository.findAll();
        assertThat(all.size(), is(equalTo(0)));
    }

    //-----------------------------------------------------------------
    // Common methods
    //-----------------------------------------------------------------

    private UserDTO createUserDTO(String email) throws com.revaluate.account.exception.UserException {
        userDTO = new UserDTOBuilder().withEmail(email).withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdCategoryDTO = userService.create(userDTO);
        userDTO.setId(createdCategoryDTO.getId());

        return createdCategoryDTO;
    }

    private UserDTO createUserDTO() throws com.revaluate.account.exception.UserException {
        return createUserDTO("xx@xx.xx");
    }
}
