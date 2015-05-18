package com.revaluate.resource.payment;

import com.braintreegateway.ClientTokenRequest;
import com.braintreegateway.Customer;
import com.braintreegateway.CustomerRequest;
import com.braintreegateway.Result;
import com.braintreegateway.exceptions.NotFoundException;
import com.revaluate.domain.payment.PaymentDetailsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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
    public Customer findCustomer(String customerId) throws PaymentException {
        try {
            return braintreeGatewayService.getBraintreeGateway().customer().find(customerId);
        } catch (NotFoundException ex) {

            throw new PaymentException(ex);
        }
    }

    @Override
    public Result<Customer> createPaymentStatus(PaymentDetailsDTO paymentDetailsDTO) {
        CustomerRequest customerRequest = new CustomerRequest()
                .firstName(paymentDetailsDTO.getFirstName())
                .lastName(paymentDetailsDTO.getLastName())
                .paymentMethodNonce(paymentDetailsDTO.getPaymentMethodNonce());

        //-----------------------------------------------------------------
        // Only one payment method allowed + make default true.
        //-----------------------------------------------------------------
        customerRequest.creditCard()
                .options()
                .failOnDuplicatePaymentMethod(Boolean.TRUE)
                .makeDefault(Boolean.TRUE)
                .done();

        return braintreeGatewayService
                .getBraintreeGateway()
                .customer()
                .create(customerRequest);
    }
}