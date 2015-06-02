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

    private static final String IOAN_LUCUT_CUSTOMER_ID_SANDBOX = "61714209";

    @Autowired
    private PaymentStatusService paymentStatusService;

    @Test
    @Ignore
    public void fetchPaymentInsightsFor__validCustomerId__isOk() throws Exception {
        PaymentInsightsDTO paymentInsightsDTO = paymentStatusService.fetchPaymentInsights(IOAN_LUCUT_CUSTOMER_ID_SANDBOX);
        assertThat(paymentInsightsDTO, is(notNullValue()));
        assertThat(paymentInsightsDTO.getPaymentCustomerDTO(), is(notNullValue()));
        assertThat(paymentInsightsDTO.getPaymentMethodDTOs(), is(notNullValue()));
        assertThat(paymentInsightsDTO.getPaymentTransactionDTOs(), is(notNullValue()));
    }
}