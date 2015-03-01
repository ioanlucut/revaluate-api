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
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolationException;

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

    @Rule
    public ExpectedException exception = ExpectedException.none();

    //-----------------------------------------------------------------
    // DTO objects used
    //-----------------------------------------------------------------
    private UserDTO userDTO;
    private ExpenseDTO expenseDTO;
    private CategoryDTO categoryDTO;

    @Before
    @After
    public void tearDown() throws Exception {
        // Should cascade expenses and categories
        if (userDTO != null) {
            if (userRepository.exists(userDTO.getId())) {
                userRepository.delete(userDTO.getId());
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
    public void create_twoExpensesWithSameCategory_ok() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        categoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create two expenses
        //-----------------------------------------------------------------
        expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(categoryDTO).build();
        ExpenseDTO createdExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());
        ExpenseDTO secondCreatedExpenseDTO = expenseService.create(expenseDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert created expenses is ok
        //-----------------------------------------------------------------
        assertThat(createdExpenseDTO.getCategory(), equalTo(expenseDTO.getCategory()));
        assertThat(secondCreatedExpenseDTO.getCategory(), equalTo(expenseDTO.getCategory()));
    }

    @Test(expected = ConstraintViolationException.class)
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

    @Test(expected = ExpenseException.class)
    public void create_properCategoryButWrongUserId_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        categoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Create an invalid expense
        //-----------------------------------------------------------------
        expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withCategory(categoryDTO).withDescription("my first expense").build();
        expenseService.create(expenseDTO, createdUserDTO.getId() + 999999);
    }

    @Test
    public void create_invalidExpenseDTO_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create an invalid expense - no category
        //-----------------------------------------------------------------
        exception.expect(ConstraintViolationException.class);
        expenseService.create(new ExpenseDTOBuilder().withValue(2.55).build(), createdUserDTO.getId());
    }

    @Test(expected = ExpenseException.class)
    public void create_withAnotherUserCategory_throwsException() throws Exception {
        //-----------------------------------------------------------------
        // Create first user
        //-----------------------------------------------------------------
        UserDTO firstUserDTO = createUserDTO("xxx@xxx.xxx1");

        //-----------------------------------------------------------------
        // Create second user
        //-----------------------------------------------------------------
        UserDTO secondUserDTO = createUserDTO("xxx@xxx.xxx2");

        //-----------------------------------------------------------------
        // Create category
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor("#eee").withName("name").build();
        categoryDTO = categoryService.create(categoryDTO, secondUserDTO.getId());

        //-----------------------------------------------------------------
        // Create an expenses with category from another user
        //-----------------------------------------------------------------
        expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(categoryDTO).build();
        expenseService.create(expenseDTO, firstUserDTO.getId());
    }

    //-----------------------------------------------------------------
    // Test update part
    //-----------------------------------------------------------------
    @Test
    public void update_value_ok() throws Exception {
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
        // Expense to update - ONLY VALUE
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTOToUpdate = new ExpenseDTOBuilder().withId(expenseDTO.getId()).withValue(2.56).withCategory(createdCategoryDTO).build();
        ExpenseDTO updatedExpenseDTO = expenseService.update(expenseDTOToUpdate, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert updated expense is ok, and updated only concerned values
        //-----------------------------------------------------------------
        assertThat(updatedExpenseDTO.getId(), is(equalTo(createdExpenseDTO.getId())));
        assertThat(updatedExpenseDTO.getCategory().getColor(), equalTo(createdExpenseDTO.getCategory().getColor()));
        assertThat(updatedExpenseDTO.getCategory().getName(), equalTo(createdExpenseDTO.getCategory().getName()));
        assertThat(updatedExpenseDTO.getCategory().getId(), equalTo(createdExpenseDTO.getCategory().getId()));
        // Only this was updated
        assertThat(updatedExpenseDTO.getValue(), equalTo(expenseDTOToUpdate.getValue()));
    }

    @Test
    public void update_description_ok() throws Exception {
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
        // Expense to update - ONLY description
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTOToUpdate = new ExpenseDTOBuilder().withId(expenseDTO.getId()).withValue(2.55).withDescription("DESC").withCategory(createdCategoryDTO).build();
        ExpenseDTO updatedExpenseDTO = expenseService.update(expenseDTOToUpdate, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert updated expense is ok, and updated only concerned values
        //-----------------------------------------------------------------
        assertThat(updatedExpenseDTO.getId(), is(equalTo(createdExpenseDTO.getId())));
        assertThat(updatedExpenseDTO.getCategory().getColor(), equalTo(createdExpenseDTO.getCategory().getColor()));
        assertThat(updatedExpenseDTO.getCategory().getName(), equalTo(createdExpenseDTO.getCategory().getName()));
        assertThat(updatedExpenseDTO.getCategory().getId(), equalTo(createdExpenseDTO.getCategory().getId()));
        assertThat(updatedExpenseDTO.getValue(), equalTo(createdExpenseDTO.getValue()));
        // Only these was updated
        assertThat(updatedExpenseDTO.getDescription(), equalTo(expenseDTOToUpdate.getDescription()));
    }

    @Test
    public void update_category_ok() throws Exception {
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
        // Create new category
        //-----------------------------------------------------------------
        categoryDTO = new CategoryDTOBuilder().withColor("#fff").withName("name2").build();
        createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Expense to update - ONLY description
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTOToUpdate = new ExpenseDTOBuilder().withValue(2.55).withId(expenseDTO.getId()).withDescription("my first expense").withCategory(createdCategoryDTO).build();
        ExpenseDTO updatedExpenseDTO = expenseService.update(expenseDTOToUpdate, createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assert updated expense is ok, and updated only concerned values
        //-----------------------------------------------------------------
        assertThat(updatedExpenseDTO.getId(), is(equalTo(createdExpenseDTO.getId())));
        assertThat(updatedExpenseDTO.getValue(), equalTo(createdExpenseDTO.getValue()));
        assertThat(updatedExpenseDTO.getDescription(), equalTo(expenseDTOToUpdate.getDescription()));
        // Only these was updated
        assertThat(updatedExpenseDTO.getCategory().getColor(), equalTo(createdCategoryDTO.getColor()));
        assertThat(updatedExpenseDTO.getCategory().getName(), equalTo(createdCategoryDTO.getName()));
        assertThat(updatedExpenseDTO.getCategory().getId(), equalTo(createdCategoryDTO.getId()));
    }

    @Test(expected = ExpenseException.class)
    public void update_wrongCategory_exceptionThrown() throws Exception {
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
        // Expense to update - with category without ID
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTOToUpdate = new ExpenseDTOBuilder().withValue(2.55).withId(expenseDTO.getId()).withDescription("my first expense").withCategory(categoryDTO).build();
        expenseService.update(expenseDTOToUpdate, createdUserDTO.getId());
    }

    @Test(expected = ConstraintViolationException.class)
    public void update_noCategory_exceptionThrown() throws Exception {
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
        // Expense to update - with category without ID
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTOToUpdate = new ExpenseDTOBuilder().withValue(2.55).withId(expenseDTO.getId()).withDescription("my first expense").build();
        expenseService.update(expenseDTOToUpdate, createdUserDTO.getId());
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