package com.revaluate.resource.payment;

import com.braintreegateway.Customer;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public interface PaymentService {

    int MIN_VALUE = 1;

    String fetchToken(@NotEmpty String customerId) throws PaymentException;

    Customer findCustomer(@NotEmpty String customerId) throws PaymentException;

    Result<Transaction> pay(@NotNull @Min(MIN_VALUE) BigDecimal amount, @NotEmpty String customerId, @NotEmpty String paymentMethodNonce) throws PaymentException;
}