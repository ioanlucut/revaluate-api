package com.revaluate.resource.payment;

import com.braintreegateway.*;
import com.braintreegateway.exceptions.NotFoundException;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Validated
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private BraintreeGatewayService braintreeGatewayService;

    @Override
    public String fetchToken(String customerId) throws PaymentException {
        ClientTokenRequest clientTokenRequest = new ClientTokenRequest()
                .customerId(customerId);

        Optional<String> token = Optional.ofNullable(braintreeGatewayService.getBraintreeGateway().clientToken().generate(clientTokenRequest));

        return token.orElseThrow(() -> new PaymentException("Customer id is not valid"));
    }

    @Override
    public Customer findCustomer(@NotEmpty String customerId) throws PaymentException {
        try {
            return braintreeGatewayService.getBraintreeGateway().customer().find(customerId);
        } catch (NotFoundException ex) {

            throw new PaymentException(ex);
        }
    }

    @Override
    public Result<Transaction> pay(BigDecimal amount, String customerId, String paymentMethodNonce) throws PaymentException {
        TransactionRequest request = new TransactionRequest()
                .amount(amount)
                .customerId(customerId)
                .paymentMethodNonce(paymentMethodNonce);

        return braintreeGatewayService
                .getBraintreeGateway()
                .transaction()
                .sale(request);
    }
}