package com.revaluate.payment.service;

import com.revaluate.domain.payment.PaymentCustomerDetailsDTO;
import com.revaluate.domain.payment.PaymentDetailsDTO;
import com.revaluate.domain.payment.PaymentNonceDetailsDTO;
import com.revaluate.domain.payment.PaymentStatusDTO;
import com.revaluate.domain.payment.insights.PaymentInsightsDTO;
import com.revaluate.payment.exception.PaymentStatusException;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface PaymentStatusService {

    @NotNull
    PaymentInsightsDTO fetchPaymentInsights(@NotEmpty String customerId) throws PaymentStatusException;

    @NotNull
    PaymentInsightsDTO subscribeToStandardPlan(int userId) throws PaymentStatusException;

    @NotNull
    PaymentStatusDTO findPaymentStatus(int userId) throws PaymentStatusException;

    boolean isPaymentStatusDefined(int userId);

    @NotNull
    PaymentStatusDTO createPaymentStatus(@NotNull @Valid PaymentDetailsDTO paymentDetailsDTO, int userId) throws PaymentStatusException;

    void deleteCustomerWithId(@NotEmpty String customerId) throws PaymentStatusException;

    @NotNull
    PaymentStatusDTO updateCustomer(@NotNull @Valid PaymentCustomerDetailsDTO paymentCustomerDetailsDTO, int userId) throws PaymentStatusException;

    @NotNull
    PaymentStatusDTO updatePaymentMethod(@NotNull @Valid PaymentNonceDetailsDTO paymentNonceDetailsDTO, int userId) throws PaymentStatusException;
}