package com.revaluate.domain.payment;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Objects;

@GeneratePojoBuilder
public class PaymentNonceDetailsDTO {

    @NotBlank
    private String paymentMethodNonce;

    public String getPaymentMethodNonce() {
        return paymentMethodNonce;
    }

    public void setPaymentMethodNonce(String paymentMethodNonce) {
        this.paymentMethodNonce = paymentMethodNonce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentNonceDetailsDTO that = (PaymentNonceDetailsDTO) o;
        return
                Objects.equals(paymentMethodNonce, that.paymentMethodNonce);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentMethodNonce);
    }

    @Override
    public String toString() {
        return "PaymentDetailsDTO{" +
                ", paymentMethodNonce='" + paymentMethodNonce + '\'' +
                '}';
    }
}