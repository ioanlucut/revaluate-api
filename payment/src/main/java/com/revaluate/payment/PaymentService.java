package com.revaluate.payment;

import org.hibernate.validator.constraints.NotEmpty;

public interface PaymentService {

    String fetchToken(@NotEmpty String customerId) throws PaymentException;
}