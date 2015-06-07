package com.revaluate.account.service;

import com.braintreegateway.CreditCard;
import com.braintreegateway.Customer;
import com.braintreegateway.Result;
import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.persistence.User;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.category.CategoryDTOBuilder;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpenseDTOBuilder;
import com.revaluate.domain.payment.PaymentStatusDTO;
import com.revaluate.payment.exception.PaymentStatusException;
import com.revaluate.payment.persistence.PaymentStatus;
import com.revaluate.payment.service.PaymentStatusServiceImpl;
import com.revaluate.resource.payment.PaymentException;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

public class UserServiceImplTest_remove_IT extends AbstractIntegrationTests {

    @InjectMocks
    private UserServiceImpl userServiceImplMock;

    @Spy
    private PaymentStatusServiceImpl paymentStatusService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        super.tearDown();
        prepareAllMocks();
    }

    @Test
    public void remove_validDetails_ok() throws Exception {
        //-----------------------------------------------------------------
        // Mock create payment method
        //-----------------------------------------------------------------
        Result<Customer> customerResult = (Result<Customer>) Mockito.mock(Result.class);
        Customer customerTarget = Mockito.mock(Customer.class);
        Mockito.when(customerTarget.getId()).thenReturn(CUSTOMER_ID);
        CreditCard creditCard = Mockito.mock(CreditCard.class);
        Mockito.when(creditCard.getToken()).thenReturn(METHOD_PAYMENT_TOKEN);
        Mockito.when(customerTarget.getCreditCards()).thenReturn(Collections.singletonList(creditCard));
        Mockito.when(customerResult.getTarget()).thenReturn(customerTarget);
        Mockito.when(customerResult.isSuccess()).thenReturn(Boolean.TRUE);
        Mockito.doNothing().when(paymentStatusService).deleteCustomerWithId(Mockito.anyString());
        Mockito.doReturn(true).when(paymentStatusService).isPaymentStatusDefined(Matchers.anyInt());

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
        PaymentStatus savedPaymentStatus = paymentStatusRepository.save(paymentStatus);

        assertThat(paymentStatusRepository.findOneByUserId(createdUserDTO.getId()).isPresent(), is(true));

        //-----------------------------------------------------------------
        // Return created payment status
        //-----------------------------------------------------------------
        Mockito
                .doReturn(dozerBeanMapper.map(savedPaymentStatus, PaymentStatusDTO.class))
                .when(paymentStatusService).findPaymentStatus(Matchers.anyInt());

        //-----------------------------------------------------------------
        // Remove user
        //-----------------------------------------------------------------
        userServiceImplMock.remove(createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assertions
        //-----------------------------------------------------------------
        assertThat(userRepository.exists(createdUserDTO.getId()), not(true));
        assertThat(categoryService.findAllCategoriesFor(createdUserDTO.getId()).size(), is(equalTo(0)));
        assertThat(expenseService.findAllExpensesFor(createdUserDTO.getId()).size(), is(equalTo(0)));
        assertThat(paymentStatusRepository.findOneByUserId(createdUserDTO.getId()).isPresent(), not(true));
    }

    @Test
    public void remove_rollbackPerformedIfBraintreeApiCallIsFailed_ok() throws Exception {
        //-----------------------------------------------------------------
        // Mock create payment method
        //-----------------------------------------------------------------
        Result<Customer> customerResult = (Result<Customer>) Mockito.mock(Result.class);
        Customer customerTarget = Mockito.mock(Customer.class);
        Mockito.when(customerTarget.getId()).thenReturn(CUSTOMER_ID);
        CreditCard creditCard = Mockito.mock(CreditCard.class);
        Mockito.when(creditCard.getToken()).thenReturn(METHOD_PAYMENT_TOKEN);
        Mockito.when(customerTarget.getCreditCards()).thenReturn(Collections.singletonList(creditCard));
        Mockito.when(customerResult.getTarget()).thenReturn(customerTarget);
        Mockito.when(customerResult.isSuccess()).thenReturn(Boolean.TRUE);
        Mockito.doThrow(Exception.class).when(paymentStatusService).deleteCustomerWithId(Mockito.anyString());
        Mockito.doReturn(true).when(paymentStatusService).isPaymentStatusDefined(Matchers.anyInt());

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
        PaymentStatus savedPaymentStatus = paymentStatusRepository.save(paymentStatus);

        assertThat(paymentStatusRepository.findOneByUserId(createdUserDTO.getId()).isPresent(), is(true));

        //-----------------------------------------------------------------
        // Return created payment status
        //-----------------------------------------------------------------
        Mockito
                .doReturn(dozerBeanMapper.map(savedPaymentStatus, PaymentStatusDTO.class))
                .when(paymentStatusService).findPaymentStatus(Matchers.anyInt());

        //-----------------------------------------------------------------
        // Remove user
        //-----------------------------------------------------------------
        exception.expect(Exception.class);
        userServiceImplMock.remove(createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Assertions
        //-----------------------------------------------------------------
        assertThat(userRepository.exists(createdUserDTO.getId()), is(true));
        assertThat(categoryService.findAllCategoriesFor(createdUserDTO.getId()).size(), is(equalTo(1)));
        assertThat(expenseService.findAllExpensesFor(createdUserDTO.getId()).size(), is(equalTo(2)));
        assertThat(paymentStatusRepository.findOneByUserId(createdUserDTO.getId()).isPresent(), is(true));
    }

    private void prepareAllMocks() throws PaymentException, PaymentStatusException {
        setFieldViaReflection(userServiceImplMock.getClass(), userServiceImplMock, "paymentStatusRepository", paymentStatusRepository);
        setFieldViaReflection(userServiceImplMock.getClass(), userServiceImplMock, "emailRepository", emailRepository);
        setFieldViaReflection(userServiceImplMock.getClass(), userServiceImplMock, "expenseRepository", expenseRepository);
        setFieldViaReflection(userServiceImplMock.getClass(), userServiceImplMock, "categoryRepository", categoryRepository);
        setFieldViaReflection(userServiceImplMock.getClass(), userServiceImplMock, "userRepository", userRepository);
    }

}
