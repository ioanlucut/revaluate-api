package com.revaluate.paymentjob;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.account.UserSubscriptionStatus;
import com.revaluate.payment.exception.PaymentStatusException;
import com.revaluate.payment.service.PaymentStatusService;
import com.revaluate.resource.payment.PaymentException;
import org.joda.time.LocalDateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;

@ContextConfiguration(locations = "classpath:*applicationContext__paymentjob__test.xml")
public class UserAutoSubscriberServiceImplTestIT extends AbstractIntegrationTests {

    @InjectMocks
    private UserAutoSubscriberServiceImpl userAutoSubscriberServiceMock;

    @Mock
    private PaymentStatusService paymentStatusService;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        super.tearDown();
        prepareAllMocks();
    }

    @Test
    public void autoValidate_happyFlow_ok() throws Exception {
        //-----------------------------------------------------------------
        // Mock create payment method
        //-----------------------------------------------------------------
        doReturn(Boolean.TRUE).when(paymentStatusService).isPaymentStatusDefined(anyInt());
        doReturn(null).when(paymentStatusService).subscribeToStandardPlan(anyInt());

        UserDTO createdUserDTO = createUserDTO();
        User user = userRepository.findOne(createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Make user having end trial date now and subscription expired
        //-----------------------------------------------------------------
        user.setEndTrialDate(LocalDateTime.now());
        user.setUserSubscriptionStatus(UserSubscriptionStatus.TRIAL_EXPIRED);

        //-----------------------------------------------------------------
        // Auto validate this user
        //-----------------------------------------------------------------
        userAutoSubscriberServiceMock.autoValidate(user);

        User updatedUser = userRepository.findOne(createdUserDTO.getId());
        //-----------------------------------------------------------------
        // Assertions
        //-----------------------------------------------------------------
        assertThat(updatedUser.getUserSubscriptionStatus(), is(UserSubscriptionStatus.ACTIVE));
    }

    @Test
    public void autoValidate_forAUserNotValidForThisPurpose_ok() throws Exception {
        //-----------------------------------------------------------------
        // Mock create payment method
        //-----------------------------------------------------------------
        doReturn(Boolean.FALSE).when(paymentStatusService).isPaymentStatusDefined(anyInt());

        UserDTO createdUserDTO = createUserDTO();
        User user = userRepository.findOne(createdUserDTO.getId());

        //-----------------------------------------------------------------
        // Make user having end trial date now and subscription expired
        //-----------------------------------------------------------------
        user.setEndTrialDate(LocalDateTime.now());
        user.setUserSubscriptionStatus(UserSubscriptionStatus.TRIAL_EXPIRED);
        userRepository.save(user);

        //-----------------------------------------------------------------
        // Auto validate this user
        //-----------------------------------------------------------------
        userAutoSubscriberServiceMock.autoValidate(user);

        User updatedUser = userRepository.findOne(createdUserDTO.getId());
        //-----------------------------------------------------------------
        // Assertions
        //-----------------------------------------------------------------
        assertThat(updatedUser.getUserSubscriptionStatus(), is(UserSubscriptionStatus.TRIAL_EXPIRED));
    }

    private void prepareAllMocks() throws PaymentException, PaymentStatusException {
        setFieldViaReflection(userAutoSubscriberServiceMock.getClass(), userAutoSubscriberServiceMock, "userRepository", userRepository);
    }
}