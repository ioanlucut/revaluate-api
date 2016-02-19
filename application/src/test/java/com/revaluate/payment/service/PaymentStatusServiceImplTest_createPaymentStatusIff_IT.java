package com.revaluate.payment.service;

import com.braintreegateway.CreditCard;
import com.braintreegateway.Customer;
import com.braintreegateway.Result;
import com.braintreegateway.Subscription;
import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.account.UserSubscriptionStatus;
import com.revaluate.domain.payment.PaymentDetailsDTO;
import com.revaluate.domain.payment.PaymentDetailsDTOBuilder;
import com.revaluate.domain.payment.PaymentStatusDTO;
import com.revaluate.domain.payment.insights.PaymentInsightsDTO;
import com.revaluate.payment.exception.PaymentStatusException;
import com.revaluate.payment.persistence.PaymentStatus;
import com.revaluate.payment.persistence.PaymentStatusRepository;
import com.revaluate.resource.payment.PaymentException;
import com.revaluate.resource.payment.PaymentService;
import org.dozer.DozerBeanMapper;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class PaymentStatusServiceImplTest_createPaymentStatusIff_IT extends AbstractIntegrationTests {

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

        //-----------------------------------------------------------------
        // I know, it is really ugly :(
        //-----------------------------------------------------------------
        paymentStatusServiceMock = Mockito.spy(paymentStatusServiceMock);
        prepareAllMocks();
    }

    @Test
    public void createPaymentStatusAndSubscribeToStandardPlanIfUserIsEligible__userIsNotInTrialExpiredSoActivationIsSkipped__isOk() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();

        PaymentInsightsDTO paymentInsightsDTO = paymentStatusServiceMock.createPaymentStatusAndSubscribeToStandardPlanIfUserIsEligible(new PaymentDetailsDTOBuilder().build(), createdUserDTO.getId());
        assertThat(paymentInsightsDTO).isNotNull();

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

        verify(paymentStatusServiceMock, never()).subscribeToStandardPlan(anyInt());
        verify(paymentStatusServiceMock, times(1)).fetchPaymentInsights(anyString());
        verify(paymentStatusServiceMock, times(1)).createPaymentStatus(anyObject(), anyInt());
    }

    @Test
    public void createPaymentStatusAndSubscribeToStandardPlanIfUserIsEligible__userIsInTrialExpiredSoActivationIsNotSkipped__isOk() throws Exception {
        //-----------------------------------------------------------------
        // Create user
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();
        User user = userRepository.findOne(createdUserDTO.getId());
        user.setEndTrialDate(LocalDateTime.now());
        user.setUserSubscriptionStatus(UserSubscriptionStatus.TRIAL_EXPIRED);
        userRepository.save(user);

        PaymentInsightsDTO paymentInsightsDTO = paymentStatusServiceMock.createPaymentStatusAndSubscribeToStandardPlanIfUserIsEligible(new PaymentDetailsDTOBuilder().build(), createdUserDTO.getId());
        assertThat(paymentInsightsDTO).isNotNull();

        //-----------------------------------------------------------------
        // Just check if the payment status was properly attached to this user
        //-----------------------------------------------------------------
        Optional<PaymentStatus> paymentStatusByUserId = paymentStatusRepository.findOneByUserId(createdUserDTO.getId());
        assertThat(paymentStatusByUserId).isNotNull();
        assertThat(paymentStatusByUserId.isPresent()).isTrue();

        //-----------------------------------------------------------------
        // User subscription status is active
        //-----------------------------------------------------------------
        user = userRepository.findOne(createdUserDTO.getId());
        assertThat(user).isNotNull();
        assertThat(user.getUserSubscriptionStatus()).isEqualTo(UserSubscriptionStatus.ACTIVE);

        verify(paymentStatusServiceMock, times(1)).subscribeToStandardPlan(anyInt());
        verify(paymentStatusServiceMock, times(1)).fetchPaymentInsights(anyString());
        verify(paymentStatusServiceMock, times(1)).createPaymentStatus(anyObject(), anyInt());
    }

    private void prepareAllMocks() throws PaymentException, PaymentStatusException {
        setFieldViaReflection(paymentStatusServiceMock.getClass(), paymentStatusServiceMock, "userRepository", userRepository);
        setFieldViaReflection(paymentStatusServiceMock.getClass(), paymentStatusServiceMock, "paymentStatusRepository", paymentStatusRepository);

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

        Result<Subscription> subscriptionResult = (Result<Subscription>) mock(Result.class);
        Subscription subscriptionTarget = mock(Subscription.class);
        when(subscriptionResult.getTarget()).thenReturn(subscriptionTarget);
        when(subscriptionResult.isSuccess()).thenReturn(Boolean.TRUE);
        when(paymentService.subscribeToStandardPlan(Matchers.anyObject())).thenReturn(subscriptionResult);
        when(paymentService.subscribeToStandardPlan(Matchers.any(PaymentStatusDTO.class))).thenReturn(subscriptionResult);

        doReturn(Mockito.mock(PaymentInsightsDTO.class)).when(paymentStatusServiceMock).fetchPaymentInsights(anyObject());
    }

}