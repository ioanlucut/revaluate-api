package com.revaluate.payment.service;

import com.braintreegateway.CreditCard;
import com.braintreegateway.Customer;
import com.braintreegateway.Result;
import com.braintreegateway.ValidationErrors;
import com.revaluate.AbstractIntegrationTests;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.payment.PaymentDetailsDTO;
import com.revaluate.domain.payment.PaymentDetailsDTOBuilder;
import com.revaluate.payment.exception.PaymentStatusException;
import com.revaluate.payment.persistence.PaymentStatus;
import com.revaluate.payment.persistence.PaymentStatusRepository;
import com.revaluate.resource.payment.PaymentException;
import com.revaluate.resource.payment.PaymentService;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentStatusServiceImplTest_deleteCustomerWithId_IT extends AbstractIntegrationTests {

    @InjectMocks
    private PaymentStatusServiceImpl paymentStatusServiceMock;

    @Mock
    private PaymentService paymentService;

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    @Spy
    private DozerBeanMapper dozerBeanMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        super.tearDown();
        prepareAllMocks();
    }

    @Test
    public void deleteCustomerWithId__validCustomerIdGoodResponse__isOk() throws Exception {
        //-----------------------------------------------------------------
        // Mock create payment method
        //-----------------------------------------------------------------
        Result<Customer> customerResult = (Result<Customer>) mock(Result.class);
        Customer customerTarget = mock(Customer.class);
        when(customerTarget.getId()).thenReturn(CUSTOMER_ID);
        when(customerResult.getTarget()).thenReturn(customerTarget);
        when(customerResult.isSuccess()).thenReturn(Boolean.TRUE);
        when(paymentService.deleteCustomer(Matchers.anyString())).thenReturn(customerResult);

        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        paymentStatusServiceMock.createPaymentStatus(new PaymentDetailsDTOBuilder().build(), createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Just check if the payment status was properly attached to this user
        //-----------------------------------------------------------------
        Optional<PaymentStatus> paymentStatusByUserId = paymentStatusRepository.findOneByUserId(createdUserDTO.getId());
        PaymentStatus paymentStatus = paymentStatusByUserId.get();

        paymentStatusServiceMock.deleteCustomerWithId(paymentStatus.getCustomerId());
    }

    @Test
    public void deleteCustomerWithId__validCustomerIdBadResponse__isOk() throws Exception {
        //-----------------------------------------------------------------
        // Mock create payment method
        //-----------------------------------------------------------------
        Result<Customer> customerResult = (Result<Customer>) mock(Result.class);
        Customer customerTarget = mock(Customer.class);
        when(customerTarget.getId()).thenReturn(CUSTOMER_ID);
        when(customerResult.getTarget()).thenReturn(customerTarget);
        when(customerResult.isSuccess()).thenReturn(Boolean.FALSE);
        ValidationErrors validationErrors = mock(ValidationErrors.class);
        when(customerResult.getErrors()).thenReturn(validationErrors);
        when(paymentService.deleteCustomer(Matchers.anyString())).thenReturn(customerResult);

        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        paymentStatusServiceMock.createPaymentStatus(new PaymentDetailsDTOBuilder().build(), createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Just check if the payment status was properly attached to this user
        //-----------------------------------------------------------------
        Optional<PaymentStatus> paymentStatusByUserId = paymentStatusRepository.findOneByUserId(createdUserDTO.getId());
        PaymentStatus paymentStatus = paymentStatusByUserId.get();

        exception.expect(PaymentStatusException.class);
        paymentStatusServiceMock.deleteCustomerWithId(paymentStatus.getCustomerId());
    }

    @Test
    public void deleteCustomerWithId__nonExistingUserId__isOk() throws Exception {
        //-----------------------------------------------------------------
        // Mock create payment method
        //-----------------------------------------------------------------
        Result<Customer> customerResult = (Result<Customer>) mock(Result.class);
        Customer customerTarget = mock(Customer.class);
        when(customerTarget.getId()).thenReturn(CUSTOMER_ID);
        when(customerResult.getTarget()).thenReturn(customerTarget);
        when(customerResult.isSuccess()).thenReturn(Boolean.FALSE);
        ValidationErrors validationErrors = mock(ValidationErrors.class);
        when(customerResult.getErrors()).thenReturn(validationErrors);
        when(paymentService.deleteCustomer(Matchers.anyString())).thenReturn(customerResult);

        exception.expect(PaymentStatusException.class);
        paymentStatusServiceMock.deleteCustomerWithId("abcdefghij");
    }

    private void prepareAllMocks() throws PaymentException {
        setFieldViaReflection(paymentStatusServiceMock.getClass(), paymentStatusServiceMock, "userRepository", userRepository);
        setFieldViaReflection(paymentStatusServiceMock.getClass(), paymentStatusServiceMock, "paymentStatusRepository", paymentStatusRepository);

        //-----------------------------------------------------------------
        // Mock fetch token
        //-----------------------------------------------------------------
        when(paymentService.fetchToken(anyString())).thenReturn(TOKEN_PAYMENT);

        //-----------------------------------------------------------------
        // Mock fetch token
        //-----------------------------------------------------------------
        when(paymentService.fetchToken(anyString())).thenReturn(TOKEN_PAYMENT);

        //-----------------------------------------------------------------
        // Mock create payment method
        //-----------------------------------------------------------------
        Result<Customer> customerResult = (Result<Customer>) mock(Result.class);
        Customer customerTarget = mock(Customer.class);
        when(customerTarget.getId()).thenReturn(CUSTOMER_ID);
        CreditCard creditCard = mock(CreditCard.class);
        when(creditCard.getToken()).thenReturn(METHOD_PAYMENT_TOKEN);
        when(customerTarget.getCreditCards()).thenReturn(Collections.singletonList(creditCard));
        when(customerResult.getTarget()).thenReturn(customerTarget);
        when(customerResult.isSuccess()).thenReturn(Boolean.TRUE);
        when(paymentService.createCustomerWithPaymentMethod(Matchers.any(PaymentDetailsDTO.class))).thenReturn(customerResult);
    }

}