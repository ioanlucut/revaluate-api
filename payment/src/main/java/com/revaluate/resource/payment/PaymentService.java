package com.revaluate.resource.payment;

import com.braintreegateway.Customer;
import com.braintreegateway.PaymentMethod;
import com.braintreegateway.Result;
import com.revaluate.domain.payment.PaymentDetailsDTO;
import com.revaluate.domain.payment.PaymentStatusDTO;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface PaymentService {

    String fetchToken(@NotEmpty String customerId) throws PaymentException;

    Customer findCustomer(@NotEmpty String customerId) throws PaymentException;

    @NotNull
    Result<Customer> createPaymentStatus(@NotNull @Valid PaymentDetailsDTO paymentDetailsDTO);

    @NotNull
    Result<Customer> updateCustomer(@NotNull @Valid PaymentStatusDTO paymentStatusDTO, @NotNull @Valid PaymentDetailsDTO paymentDetailsDTO);

    @NotNull
    Result<? extends PaymentMethod> updatePaymentMethod(@NotNull @Valid PaymentStatusDTO paymentStatusDTO, @NotNull @Valid PaymentDetailsDTO paymentDetailsDTO);
}