package com.revaluate.paymentjob;

import com.revaluate.account.persistence.User;
import com.revaluate.payment.exception.PaymentStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface UserAutoSubscriberService {

    @NotNull
    void autoValidate(@Valid @NotNull User user) throws PaymentStatusException;
}