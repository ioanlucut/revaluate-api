package com.revaluate.payment.service;

import com.braintreegateway.CreditCard;
import com.braintreegateway.Customer;
import com.braintreegateway.Result;
import com.braintreegateway.ValidationErrors;
import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.persistence.UserRepository;
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

public class PaymentStatusServiceImplTest_deleteCustomerWithId_IT extends AbstractIntegrationTests {

    @InjectMocks
    private PaymentStatusServiceImpl paymentStatusService;

    @Mock
    private PaymentService paymentService;

    @Autowired
    private UserRepository userRepository;

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
        Result<Customer> customerResult = (Result<Customer>) Mockito.mock(Result.class);
        Customer customerTarget = Mockito.mock(Customer.class);
        Mockito.when(customerTarget.getId()).thenReturn(CUSTOMER_ID);
        Mockito.when(customerResult.getTarget()).thenReturn(customerTarget);
        Mockito.when(customerResult.isSuccess()).thenReturn(Boolean.TRUE);
        Mockito.when(paymentService.deleteCustomer(Matchers.anyString())).thenReturn(customerResult);

        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        paymentStatusService.createPaymentStatus(new PaymentDetailsDTOBuilder().build(), createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Just check if the payment status was properly attached to this user
        //-----------------------------------------------------------------
        Optional<PaymentStatus> paymentStatusByUserId = paymentStatusRepository.findOneByUserId(createdUserDTO.getId());
        PaymentStatus paymentStatus = paymentStatusByUserId.get();

        paymentStatusService.deleteCustomerWithId(paymentStatus.getUser().getId());
    }

    @Test
    public void deleteCustomerWithId__validCustomerIdBadResponse__isOk() throws Exception {
        //-----------------------------------------------------------------
        // Mock create payment method
        //-----------------------------------------------------------------
        Result<Customer> customerResult = (Result<Customer>) Mockito.mock(Result.class);
        Customer customerTarget = Mockito.mock(Customer.class);
        Mockito.when(customerTarget.getId()).thenReturn(CUSTOMER_ID);
        Mockito.when(customerResult.getTarget()).thenReturn(customerTarget);
        Mockito.when(customerResult.isSuccess()).thenReturn(Boolean.FALSE);
        ValidationErrors validationErrors = Mockito.mock(ValidationErrors.class);
        Mockito.when(customerResult.getErrors()).thenReturn(validationErrors);
        Mockito.when(paymentService.deleteCustomer(Matchers.anyString())).thenReturn(customerResult);

        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        paymentStatusService.createPaymentStatus(new PaymentDetailsDTOBuilder().build(), createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Just check if the payment status was properly attached to this user
        //-----------------------------------------------------------------
        Optional<PaymentStatus> paymentStatusByUserId = paymentStatusRepository.findOneByUserId(createdUserDTO.getId());
        PaymentStatus paymentStatus = paymentStatusByUserId.get();

        exception.expect(PaymentStatusException.class);
        paymentStatusService.deleteCustomerWithId(paymentStatus.getUser().getId());
    }

    @Test
    public void deleteCustomerWithId__nonExistingUserId__isOk() throws Exception {
        exception.expect(PaymentStatusException.class);
        paymentStatusService.deleteCustomerWithId(99999);
    }

    private void prepareAllMocks() throws PaymentException {
        //-----------------------------------------------------------------
        // I cannot spy on it because it is an interface, so we autowire and set it.. Ugly, I know..
        //-----------------------------------------------------------------
        paymentStatusService.setUserRepository(userRepository);
        paymentStatusService.setPaymentStatusRepository(paymentStatusRepository);

        //-----------------------------------------------------------------
        // Mock fetch token
        //-----------------------------------------------------------------
        Mockito.when(paymentService.fetchToken(anyString())).thenReturn(TOKEN_PAYMENT);

        //-----------------------------------------------------------------
        // Mock fetch token
        //-----------------------------------------------------------------
        Mockito.when(paymentService.fetchToken(anyString())).thenReturn(TOKEN_PAYMENT);

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
        Mockito.when(paymentService.createCustomerWithPaymentMethod(Matchers.any(PaymentDetailsDTO.class))).thenReturn(customerResult);
    }

}