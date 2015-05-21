package com.revaluate.domain.payment.insights;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@GeneratePojoBuilder
public class PaymentInsightsDTO {

    @NotNull
    @Valid
    private PaymentCustomerDTO paymentCustomerDTO;

    @NotNull
    @Valid
    private List<PaymentMethodDTO> paymentMethodDTOs;

    @NotNull
    @Valid
    private List<PaymentTransactionDTO> paymentTransactionDTOs;

    /**
     * If at least a subscription is present. We only allow one to have.
     */
    private boolean subscriptionActive;

    public PaymentCustomerDTO getPaymentCustomerDTO() {
        return paymentCustomerDTO;
    }

    public void setPaymentCustomerDTO(PaymentCustomerDTO paymentCustomerDTO) {
        this.paymentCustomerDTO = paymentCustomerDTO;
    }

    public List<PaymentMethodDTO> getPaymentMethodDTOs() {
        return paymentMethodDTOs;
    }

    public void setPaymentMethodDTOs(List<PaymentMethodDTO> paymentMethodDTOs) {
        this.paymentMethodDTOs = paymentMethodDTOs;
    }

    public List<PaymentTransactionDTO> getPaymentTransactionDTOs() {
        return paymentTransactionDTOs;
    }

    public void setPaymentTransactionDTOs(List<PaymentTransactionDTO> paymentTransactionDTOs) {
        this.paymentTransactionDTOs = paymentTransactionDTOs;
    }

    public boolean isSubscriptionActive() {
        return subscriptionActive;
    }

    public void setSubscriptionActive(boolean subscriptionActive) {
        this.subscriptionActive = subscriptionActive;
    }

    @Override
    public String toString() {
        return "PaymentInsightsDTO{" +
                "paymentCustomerDTO=" + paymentCustomerDTO +
                ", paymentMethodDTOs=" + paymentMethodDTOs +
                ", paymentTransactionDTOs=" + paymentTransactionDTOs +
                ", subscriptionActive=" + subscriptionActive +
                '}';
    }
}