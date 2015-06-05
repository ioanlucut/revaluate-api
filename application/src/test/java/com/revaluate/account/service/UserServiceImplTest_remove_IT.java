package com.revaluate.account.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.persistence.User;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.payment.persistence.PaymentStatus;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class UserServiceImplTest_remove_IT extends AbstractIntegrationTests {

    @Test
    public void remove_validDetails_ok() throws Exception {
        UserDTO createdUserDTO = createUserDTO();

        //-----------------------------------------------------------------
        // Create categories
        //-----------------------------------------------------------------
        CategoryDTO categoryDTO = new CategoryDTOBuilder().withColor(FIRST_VALID_COLOR).withName("name1").build();
        CategoryDTO createdCategoryDTO = categoryService.create(categoryDTO, createdUserDTO.getId());
        assertThat(categoryService.findAllCategoriesFor(createdUserDTO.getId()).size(), is(equalTo(1)));

        //-----------------------------------------------------------------
        // Create expenses
        //-----------------------------------------------------------------
        ExpenseDTO expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusYears(3)).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        expenseDTO = new ExpenseDTOBuilder().withValue(2.55).withDescription("my first expense").withCategory(createdCategoryDTO).withSpentDate(LocalDateTime.now().minusYears(3)).build();
        expenseService.create(expenseDTO, createdUserDTO.getId());
        assertThat(expenseService.findAllExpensesFor(createdUserDTO.getId()).size(), is(equalTo(2)));

        //-----------------------------------------------------------------
        // Payment status
        //-----------------------------------------------------------------
        PaymentStatus paymentStatus = new PaymentStatus();
        User user = userRepository.findOne(createdUserDTO.getId());
        paymentStatus.setUser(user);
        paymentStatus.setCustomerId("123456");
        paymentStatus.setPaymentMethodToken("fake");
        paymentStatusRepository.save(paymentStatus);
        assertThat(paymentStatusRepository.findOneByUserId(createdUserDTO.getId()).isPresent(), is(true));

        //-----------------------------------------------------------------
        // Remove user
        //-----------------------------------------------------------------
        userService.remove(createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assertions
        //-----------------------------------------------------------------
        assertThat(userRepository.exists(createdUserDTO.getId()), not(true));
        assertThat(categoryService.findAllCategoriesFor(createdUserDTO.getId()).size(), is(equalTo(0)));
        assertThat(expenseService.findAllExpensesFor(createdUserDTO.getId()).size(), is(equalTo(0)));
        assertThat(paymentStatusRepository.findOneByUserId(createdUserDTO.getId()).isPresent(), not(true));
    }
}
