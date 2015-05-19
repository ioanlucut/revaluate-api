package com.revaluate.payment.service;

import com.revaluate.domain.payment.PaymentDetailsDTO;
import com.revaluate.domain.payment.PaymentStatusDTO;
import com.revaluate.payment.exception.PaymentStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface PaymentStatusService {

    @NotNull
    PaymentStatusDTO findOneByUserId(int userId) throws PaymentStatusException;

    @NotNull
    PaymentStatusDTO createPaymentStatus(@NotNull @Valid PaymentDetailsDTO paymentDetailsDTO, int userId) throws PaymentStatusException;

    @NotNull
    PaymentStatusDTO updateCustomer(@NotNull @Valid PaymentDetailsDTO paymentDetailsDTO, int userId) throws PaymentStatusException;

    @NotNull
    PaymentStatusDTO createPaymentMethod(@NotNull @Valid PaymentDetailsDTO paymentDetailsDTO, int userId) throws PaymentStatusException;
}