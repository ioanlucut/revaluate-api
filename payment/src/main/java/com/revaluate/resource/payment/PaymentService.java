package com.revaluate.resource.payment;

import com.braintreegateway.*;
import com.revaluate.domain.payment.PaymentCustomerDetailsDTO;
import com.revaluate.domain.payment.PaymentDetailsDTO;
import com.revaluate.domain.payment.PaymentNonceDetailsDTO;
import com.revaluate.domain.payment.PaymentStatusDTO;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface PaymentService {

    String fetchToken(@NotEmpty String customerId) throws PaymentException;

    String fetchToken() throws PaymentException;

    Customer findCustomer(@NotEmpty String customerId) throws PaymentException;

    ResourceCollection<Transaction> findTransactions(@NotEmpty String customerId);

    @NotNull
    Result<Customer> createCustomerWithPaymentMethod(@NotNull @Valid PaymentDetailsDTO paymentDetailsDTO);

    @NotNull
    Result<Subscription> subscribeToStandardPlan(@NotNull @Valid PaymentStatusDTO paymentStatusDTO) throws PaymentException;

    @NotNull
    Result<Customer> updateCustomerDetails(@NotNull @Valid PaymentStatusDTO paymentStatusDTO, @NotNull @Valid PaymentCustomerDetailsDTO paymentCustomerDetailsDTO);

    @NotNull
    Result<? extends PaymentMethod> updatePaymentMethod(@NotNull @Valid PaymentStatusDTO paymentStatusDTO, @NotNull @Valid PaymentNonceDetailsDTO paymentNonceDetailsDTO);
}