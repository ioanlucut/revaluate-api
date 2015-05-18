package com.revaluate.payment.service;

import com.braintreegateway.CreditCard;
import com.braintreegateway.Customer;
import com.braintreegateway.Result;
import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.payment.PaymentDetailsDTO;
import com.revaluate.domain.payment.PaymentDetailsDTOBuilder;
import com.revaluate.domain.payment.PaymentStatusDTO;
import com.revaluate.payment.persistence.PaymentStatus;
import com.revaluate.payment.persistence.PaymentStatusRepository;
import com.revaluate.resource.payment.PaymentException;
import com.revaluate.resource.payment.PaymentService;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;

import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Matchers.anyString;

public class PaymentStatusServiceImplTest_createPaymentStatus_IT extends AbstractIntegrationTests {

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
    }

    @Test
    public void createPaymentStatus__validCustomerId__isOk() throws Exception {
        prepareAllMocks();

        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        PaymentStatusDTO paymentStatus = paymentStatusService.createPaymentStatus(new PaymentDetailsDTOBuilder().build(), createdUserDTO.getId());
        assertThat(paymentStatus, is(notNullValue()));
        assertThat(paymentStatus.getCustomerId(), is(equalTo(CUSTOMER_ID)));
        assertThat(paymentStatus.getPaymentMethodToken(), is(equalTo(METHOD_PAYMENT_TOKEN)));
        assertThat(paymentStatus.getCreatedDate(), is(notNullValue()));
        assertThat(paymentStatus.getModifiedDate(), is(notNullValue()));

        Optional<PaymentStatus> oneByUserId = paymentStatusRepository.findOneByUserId(createdUserDTO.getId());
        assertThat(oneByUserId, is(notNullValue()));
        assertThat(oneByUserId.isPresent(), is(equalTo(Boolean.TRUE)));
    }

    @Test
    public void createPaymentStatus__twiceValidCustomerId__exceptionThrown() throws Exception {
        prepareAllMocks();

        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        paymentStatusService.createPaymentStatus(new PaymentDetailsDTOBuilder().build(), createdUserDTO.getId());

        exception.expect(JpaSystemException.class);
        paymentStatusService.createPaymentStatus(new PaymentDetailsDTOBuilder().build(), createdUserDTO.getId());
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
        Mockito.when(paymentService.createPaymentStatus(Matchers.any(PaymentDetailsDTO.class))).thenReturn(customerResult);
    }

}