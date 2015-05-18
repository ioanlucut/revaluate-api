package com.revaluate.resource.payment;

import com.braintreegateway.Customer;
import com.braintreegateway.Result;
import com.revaluate.domain.payment.PaymentDetailsDTO;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface PaymentService {

    int MIN_VALUE = 1;

    String fetchToken(@NotEmpty String customerId) throws PaymentException;

    Customer findCustomer(@NotEmpty String customerId) throws PaymentException;

    @NotNull
    Result<Customer> createPaymentStatus(@NotNull @Valid PaymentDetailsDTO paymentDetailsDTO);
}