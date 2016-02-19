package com.revaluate.payment.service;

import com.braintreegateway.CreditCard;
import com.braintreegateway.Customer;
import com.braintreegateway.Result;
import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.account.UserSubscriptionStatus;
import com.revaluate.domain.payment.PaymentDetailsDTO;
import com.revaluate.domain.payment.PaymentDetailsDTOBuilder;
import com.revaluate.domain.payment.PaymentStatusDTO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentStatusServiceImplTest_createPaymentStatus_IT extends AbstractIntegrationTests {

    @InjectMocks
    private PaymentStatusServiceImpl paymentStatusServiceMock;

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
    public void createPaymentStatus__validCustomerId__isOk() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        PaymentStatusDTO paymentStatus = paymentStatusServiceMock.createPaymentStatus(new PaymentDetailsDTOBuilder().build(), createdUserDTO.getId());
        assertThat(paymentStatus).isNotNull();
        assertThat(paymentStatus.getCustomerId()).isEqualTo(CUSTOMER_ID);
        assertThat(paymentStatus.getPaymentMethodToken()).isEqualTo(METHOD_PAYMENT_TOKEN);
        assertThat(paymentStatus.getCreatedDate()).isNotNull();
        assertThat(paymentStatus.getModifiedDate()).isNotNull();

        //-----------------------------------------------------------------
        // Just check if the payment status was properly attached to this user
        //-----------------------------------------------------------------
        Optional<PaymentStatus> paymentStatusByUserId = paymentStatusRepository.findOneByUserId(createdUserDTO.getId());
        assertThat(paymentStatusByUserId).isNotNull();
        assertThat(paymentStatusByUserId.isPresent()).isTrue();

        //-----------------------------------------------------------------
        // User subscription status is active
        //-----------------------------------------------------------------
        User user = userRepository.findOne(createdUserDTO.getId());
        assertThat(user).isNotNull();
        assertThat(user.getUserSubscriptionStatus()).isEqualTo(UserSubscriptionStatus.TRIAL);
    }

    @Test
    public void createPaymentStatus__twiceValidCustomerId__exceptionThrown() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        paymentStatusServiceMock.createPaymentStatus(new PaymentDetailsDTOBuilder().build(), createdUserDTO.getId());

        exception.expect(PaymentStatusException.class);
        paymentStatusServiceMock.createPaymentStatus(new PaymentDetailsDTOBuilder().build(), createdUserDTO.getId());
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