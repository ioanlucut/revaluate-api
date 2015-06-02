package com.revaluate.payment.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.domain.payment.insights.PaymentInsightsDTO;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PaymentStatusServiceImplTest_fetchPaymentInsightsFor_IT extends AbstractIntegrationTests {

    private static final String SANDBOX_CUSTOMER_ID = "REDACTED";

    @Autowired
    private PaymentStatusService paymentStatusService;

    @Test
    @Ignore
    public void fetchPaymentInsightsFor__validCustomerId__isOk() throws Exception {
        PaymentInsightsDTO paymentInsightsDTO = paymentStatusService.fetchPaymentInsights(SANDBOX_CUSTOMER_ID);
        assertThat(paymentInsightsDTO, is(notNullValue()));
        assertThat(paymentInsightsDTO.getPaymentCustomerDTO(), is(notNullValue()));
        assertThat(paymentInsightsDTO.getPaymentMethodDTOs(), is(notNullValue()));
        assertThat(paymentInsightsDTO.getPaymentTransactionDTOs(), is(notNullValue()));
    }
}