package com.revaluate.payment.service;

import com.revaluate.AbstractIntegrationTests;
import com.revaluate.domain.payment.insights.PaymentInsightsDTO;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentStatusServiceImplTest_fetchPaymentInsightsFor_IT extends AbstractIntegrationTests {

    private static final String IOAN_LUCUT_CUSTOMER_ID_SANDBOX = "61714209";

    @Autowired
    private PaymentStatusService paymentStatusService;

    @Test
    @Ignore
    public void fetchPaymentInsightsFor__validCustomerId__isOk() throws Exception {
        PaymentInsightsDTO paymentInsightsDTO = paymentStatusService.fetchPaymentInsights(IOAN_LUCUT_CUSTOMER_ID_SANDBOX);
        assertThat(paymentInsightsDTO).isNotNull();
        assertThat(paymentInsightsDTO.getPaymentCustomerDTO()).isNotNull();
        assertThat(paymentInsightsDTO.getPaymentMethodDTOs()).isNotNull();
        assertThat(paymentInsightsDTO.getPaymentTransactionDTOs()).isNotNull();
    }
}