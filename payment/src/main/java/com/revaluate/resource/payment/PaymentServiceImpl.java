package com.revaluate.resource.payment;

import com.braintreegateway.ClientTokenRequest;
import com.braintreegateway.Result;
import com.braintreegateway.Transaction;
import com.braintreegateway.TransactionRequest;
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