package com.revaluate.payment.service;

import com.revaluate.domain.payment.PaymentDetailsDTO;
import com.revaluate.domain.payment.PaymentStatusDTO;
import com.revaluate.domain.payment.insights.PaymentInsightsDTO;
import com.revaluate.payment.exception.PaymentStatusException;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface PaymentStatusService {

    @NotNull
    PaymentInsightsDTO fetchPaymentInsightsFor(@NotEmpty String customerId) throws PaymentStatusException;

    @NotNull
    PaymentInsightsDTO subscribeToStandardPlan(int userId) throws PaymentStatusException;

    @NotNull
    PaymentStatusDTO findOneByUserId(int userId) throws PaymentStatusException;

    @NotNull
    PaymentStatusDTO createPaymentStatus(@NotNull @Valid PaymentDetailsDTO paymentDetailsDTO, int userId) throws PaymentStatusException;

    @NotNull
    PaymentStatusDTO updateCustomer(@NotNull @Valid PaymentDetailsDTO paymentDetailsDTO, int userId) throws PaymentStatusException;

    @NotNull
    PaymentStatusDTO updatePaymentMethod(@NotNull @Valid PaymentDetailsDTO paymentDetailsDTO, int userId) throws PaymentStatusException;
}