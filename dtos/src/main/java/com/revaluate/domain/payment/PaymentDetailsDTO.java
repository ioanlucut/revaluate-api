package com.revaluate.domain.payment;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@GeneratePojoBuilder
public class PaymentDetailsDTO {

    @NotNull
    @Valid
    private PaymentCustomerDetailsDTO paymentCustomerDetailsDTO;

    @NotNull
    @Valid
    private PaymentNonceDetailsDTO paymentNonceDetailsDTO;

    public PaymentCustomerDetailsDTO getPaymentCustomerDetailsDTO() {
        return paymentCustomerDetailsDTO;
    }

    public void setPaymentCustomerDetailsDTO(PaymentCustomerDetailsDTO paymentCustomerDetailsDTO) {
        this.paymentCustomerDetailsDTO = paymentCustomerDetailsDTO;
    }

    public PaymentNonceDetailsDTO getPaymentNonceDetailsDTO() {
        return paymentNonceDetailsDTO;
    }

    public void setPaymentNonceDetailsDTO(PaymentNonceDetailsDTO paymentNonceDetailsDTO) {
        this.paymentNonceDetailsDTO = paymentNonceDetailsDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDetailsDTO that = (PaymentDetailsDTO) o;
        return Objects.equals(paymentCustomerDetailsDTO, that.paymentCustomerDetailsDTO) &&
                Objects.equals(paymentNonceDetailsDTO, that.paymentNonceDetailsDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentCustomerDetailsDTO, paymentNonceDetailsDTO);
    }

    @Override
    public String toString() {
        return "PaymentDetailsDTO{" +
                "paymentCustomerDetailsDTO=" + paymentCustomerDetailsDTO +
                ", paymentNonceDetailsDTO=" + paymentNonceDetailsDTO +
                '}';
    }
}