package com.revaluate.settings.filter;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.domain.account.UserDTO;
import com.revaluate.domain.account.UserSubscriptionStatus;
import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.container.ContainerRequestContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;


public class PaymentAuthorizationRequestFilterTest_paymentRequired_IT extends AbstractIntegrationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void paymentRequired_nonPaymentRequiredMethod_isOk() throws Exception {
        PaymentAuthorizationRequestFilter paymentAuthorizationRequestFilter = spy(new PaymentAuthorizationRequestFilter());
        doReturn(Boolean.FALSE).when(paymentAuthorizationRequestFilter).isPaymentRequired();

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getMethod()).thenReturn("POST");

        paymentAuthorizationRequestFilter.filter(containerRequestContext);

        verify(paymentAuthorizationRequestFilter, never()).abort(any());
    }

    @Test
    public void paymentRequired_optionMethod_isOk() throws Exception {
        PaymentAuthorizationRequestFilter paymentAuthorizationRequestFilter = spy(new PaymentAuthorizationRequestFilter());
        doReturn(Boolean.TRUE).when(paymentAuthorizationRequestFilter).isPaymentRequired();

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getMethod()).thenReturn("OPTIONS");

        paymentAuthorizationRequestFilter.filter(containerRequestContext);

        verify(paymentAuthorizationRequestFilter, never()).abort(any());
    }

    @Test
    public void paymentRequired_userWithTrialExpired_isOk() throws Exception {
        PaymentAuthorizationRequestFilter paymentAuthorizationRequestFilter = spy(new PaymentAuthorizationRequestFilter());
        doReturn(Boolean.TRUE).when(paymentAuthorizationRequestFilter).isPaymentRequired();

        //-----------------------------------------------------------------
        // Create a user and make it with trial expired
        //-----------------------------------------------------------------
        UserDTO userDTO = createUserDTO();
        User user = userRepository.findOne(userDTO.getId());
        user.setEndTrialDate(LocalDateTime.now().minusDays(15));
        userRepository.save(user);

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getMethod()).thenReturn("POST");
        when(containerRequestContext.getProperty(AuthorizationRequestFilter.USER_ID)).thenReturn(userDTO.getId());

        paymentAuthorizationRequestFilter.setUserRepository(userRepository);
        paymentAuthorizationRequestFilter.filter(containerRequestContext);

        verify(paymentAuthorizationRequestFilter, times(1)).abort(any());
    }

    @Test
    public void paymentRequired_userWithSubscriptionActive_isOk() throws Exception {
        PaymentAuthorizationRequestFilter paymentAuthorizationRequestFilter = spy(new PaymentAuthorizationRequestFilter());
        doReturn(Boolean.TRUE).when(paymentAuthorizationRequestFilter).isPaymentRequired();

        //-----------------------------------------------------------------
        // Create a user and make it with trial expired
        //-----------------------------------------------------------------
        UserDTO userDTO = createUserDTO();
        User user = userRepository.findOne(userDTO.getId());
        user.setUserSubscriptionStatus(UserSubscriptionStatus.ACTIVE);
        userRepository.save(user);

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getMethod()).thenReturn("POST");
        when(containerRequestContext.getProperty(AuthorizationRequestFilter.USER_ID)).thenReturn(userDTO.getId());

        paymentAuthorizationRequestFilter.setUserRepository(userRepository);
        paymentAuthorizationRequestFilter.filter(containerRequestContext);

        verify(paymentAuthorizationRequestFilter, never()).abort(any());
    }

    @Test
    public void paymentRequired_userWithSubscriptionTrialExpired_isOk() throws Exception {
        PaymentAuthorizationRequestFilter paymentAuthorizationRequestFilter = spy(new PaymentAuthorizationRequestFilter());
        doReturn(Boolean.TRUE).when(paymentAuthorizationRequestFilter).isPaymentRequired();

        //-----------------------------------------------------------------
        // Create a user and make it with trial expired
        //-----------------------------------------------------------------
        UserDTO userDTO = createUserDTO();
        User user = userRepository.findOne(userDTO.getId());
        user.setUserSubscriptionStatus(UserSubscriptionStatus.TRIAL_EXPIRED);
        userRepository.save(user);

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getMethod()).thenReturn("POST");
        when(containerRequestContext.getProperty(AuthorizationRequestFilter.USER_ID)).thenReturn(userDTO.getId());

        paymentAuthorizationRequestFilter.setUserRepository(userRepository);
        paymentAuthorizationRequestFilter.filter(containerRequestContext);

        verify(paymentAuthorizationRequestFilter, times(1)).abort(any());
    }

    @Test
    public void paymentRequired_nonExistingUser_isOk() throws Exception {
        PaymentAuthorizationRequestFilter paymentAuthorizationRequestFilter = spy(new PaymentAuthorizationRequestFilter());
        doReturn(Boolean.TRUE).when(paymentAuthorizationRequestFilter).isPaymentRequired();

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getMethod()).thenReturn("POST");
        when(containerRequestContext.getProperty(AuthorizationRequestFilter.USER_ID)).thenReturn(999999);

        paymentAuthorizationRequestFilter.setUserRepository(userRepository);
        paymentAuthorizationRequestFilter.filter(containerRequestContext);

        verify(paymentAuthorizationRequestFilter, never()).abort(any());
    }

    @Test
    public void paymentRequired_userExpiredHasNowTheStatusChangedAsTrialExpired_isOk() throws Exception {
        PaymentAuthorizationRequestFilter paymentAuthorizationRequestFilter = spy(new PaymentAuthorizationRequestFilter());
        doReturn(Boolean.TRUE).when(paymentAuthorizationRequestFilter).isPaymentRequired();

        //-----------------------------------------------------------------
        // Create a user and make it with trial expired
        //-----------------------------------------------------------------
        UserDTO createdUserDTO = createUserDTO();
        assertThat(createdUserDTO.getUserSubscriptionStatus(), is(equalTo(UserSubscriptionStatus.TRIAL)));

        //-----------------------------------------------------------------
        // Now change the user created date
        //-----------------------------------------------------------------
        User user = userRepository.findOne(createdUserDTO.getId());
        user.setEndTrialDate(LocalDateTime.now().minusDays(15));
        userRepository.save(user);

        ContainerRequestContext containerRequestContext = mock(ContainerRequestContext.class);
        when(containerRequestContext.getMethod()).thenReturn("POST");
        when(containerRequestContext.getProperty(AuthorizationRequestFilter.USER_ID)).thenReturn(createdUserDTO.getId());

        paymentAuthorizationRequestFilter.setUserRepository(userRepository);
        paymentAuthorizationRequestFilter.filter(containerRequestContext);

        verify(paymentAuthorizationRequestFilter, times(1)).abort(any());

        user = userRepository.findOne(createdUserDTO.getId());
        assertThat(user.getUserSubscriptionStatus(), is(equalTo(UserSubscriptionStatus.TRIAL_EXPIRED)));
    }
}