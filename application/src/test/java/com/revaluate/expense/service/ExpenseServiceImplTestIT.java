package com.revaluate.expense.service;

import com.revaluate.account.domain.UserDTO;
import com.revaluate.account.domain.UserDTOBuilder;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.account.service.UserService;
import com.revaluate.category.domain.CategoryDTO;
import com.revaluate.category.domain.CategoryDTOBuilder;
import com.revaluate.category.persistence.CategoryRepository;
import com.revaluate.category.service.CategoryService;
import com.revaluate.expense.domain.ExpenseDTO;
import com.revaluate.expense.domain.ExpenseDTOBuilder;
import com.revaluate.expense.exception.ExpenseException;
import com.revaluate.expense.persistence.ExpenseRepository;
import org.dozer.DozerBeanMapper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ExpenseServiceImplTestIT {

    @Autowired
    private UserService userService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    //-----------------------------------------------------------------
    // DTO objects used
    //-----------------------------------------------------------------
    private UserDTO userDTO;
    private ExpenseDTO expenseDTO;
    private CategoryDTO categoryDTO;

    @After
    public void tearDown() throws Exception {
        if (userDTO != null) {
            if (userRepository.exists(userDTO.getId())) {
                userRepository.delete(userDTO.getId());
            }
        }
        if (expenseDTO != null) {
            if (expenseRepository.exists(expenseDTO.getId())) {
                expenseRepository.delete(expenseDTO.getId());
            }
        }
        if (categoryDTO != null) {
            if (categoryRepository.exists(categoryDTO.getId())) {
                categoryRepository.delete(categoryDTO.getId());
            }
        }
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
        categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create expense
        //-----------------------------------------------------------------
        expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).build();
        ExpenseDTO createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTO.setId(createdExpenseDTO.getId());

        //-----------------------------------------------------------------
        // Assert created expense is ok
        //-----------------------------------------------------------------
        assertThat(createdExpenseDTO, is(notNullValue()));
        assertThat(createdExpenseDTO.getId(), not(equalTo(0)));
        assertThat(createdExpenseDTO.getCategory(), equalTo(expenseDTO.getCategory()));
        assertThat(createdExpenseDTO.getDescription(), equalTo(expenseDTO.getDescription()));
        assertThat(createdExpenseDTO.getValue(), equalTo(expenseDTO.getValue()));
    }

    @Test
    public void create_aCategoryForTwoExpenses_ok() throws Exception {
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
        // Create two expenses
        //-----------------------------------------------------------------
        expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).build();
        ExpenseDTO createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());
        ExpenseDTO secondCreatedExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert created expenses is ok
        //-----------------------------------------------------------------
        assertThat(createdExpenseDTO.getCategory(), equalTo(expenseDTO.getCategory()));
        assertThat(secondCreatedExpenseDTO.getCategory(), equalTo(expenseDTO.getCategory()));
    }

    @Test(expected = ExpenseException.class)
    public void create_withoutCategoryForTwoExpenses_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create an invalid expense
        //-----------------------------------------------------------------
        expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
    }

    //-----------------------------------------------------------------
    // Common methods
    //-----------------------------------------------------------------
    
    private UserDTO createUserDTO(String email) throws com.revaluate.account.exception.UserException {
        userDTO = new UserDTOBuilder().withEmail(email).withFirstName("fn").withLastName("ln").withPassword("1234567").build();
        UserDTO createdExpenseDTO = userService.create(userDTO);
        userDTO.setId(createdExpenseDTO.getId());

        return createdExpenseDTO;
    }

    private UserDTO createUserDTO() throws com.revaluate.account.exception.UserException {
        return createUserDTO("xx@xx.xx");
    }
}