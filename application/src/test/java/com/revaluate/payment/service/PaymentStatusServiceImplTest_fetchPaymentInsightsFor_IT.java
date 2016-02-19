package com.revaluate.payment.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.domain.payment.insights.PaymentInsightsDTO;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentStatusServiceImplTest_fetchPaymentInsightsFor_IT extends AbstractIntegrationTests {

    private static final String SANDBOX_CUSTOMER_ID = "REDACTED";

    @Autowired
    private PaymentStatusService paymentStatusService;

    @Test
    @Ignore
    public void fetchPaymentInsightsFor__validCustomerId__isOk() throws Exception {
        PaymentInsightsDTO paymentInsightsDTO = paymentStatusService.fetchPaymentInsights(SANDBOX_CUSTOMER_ID);
        assertThat(paymentInsightsDTO).isNotNull();
        assertThat(paymentInsightsDTO.getPaymentCustomerDTO()).isNotNull();
        assertThat(paymentInsightsDTO.getPaymentMethodDTOs()).isNotNull();
        assertThat(paymentInsightsDTO.getPaymentTransactionDTOs()).isNotNull();
    }
}