package com.revaluate.resource.payment;

import com.braintreegateway.*;
import com.braintreegateway.exceptions.NotFoundException;
import com.revaluate.domain.payment.PaymentDetailsDTO;
import com.revaluate.domain.payment.PaymentStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
                .email(paymentDetailsDTO.getEmail())
                .paymentMethodNonce(paymentDetailsDTO.getPaymentMethodNonce())
                .creditCard()
                    .options()
                        .failOnDuplicatePaymentMethod(Boolean.TRUE)
                        .makeDefault(Boolean.TRUE)
                        .verifyCard(Boolean.TRUE)
                    .done()
                .done();

        return braintreeGatewayService
                .getBraintreeGateway()
                .customer()
                .create(customerRequest);
    }

    @Override
    public Result<Customer> updateCustomer(@NotNull @Valid PaymentStatusDTO paymentStatusDTO, @NotNull @Valid PaymentDetailsDTO paymentDetailsDTO) {
        CustomerRequest updateCustomerRequest = new CustomerRequest()
                .firstName(paymentDetailsDTO.getFirstName())
                .lastName(paymentDetailsDTO.getLastName())
                .email(paymentDetailsDTO.getEmail());

        return braintreeGatewayService
                .getBraintreeGateway()
                .customer()
                .update(paymentStatusDTO.getCustomerId(), updateCustomerRequest);
    }

    @Override
    public Result<? extends  PaymentMethod> updatePaymentMethod(@NotNull @Valid PaymentStatusDTO paymentStatusDTO, @NotNull @Valid PaymentDetailsDTO paymentDetailsDTO) {
        PaymentMethodRequest paymentMethodRequest = new PaymentMethodRequest()
                .paymentMethodNonce(paymentDetailsDTO.getPaymentMethodNonce())
                    .options()
                        .verifyCard(Boolean.TRUE)
                    .done();

        return braintreeGatewayService
                .getBraintreeGateway()
                .paymentMethod()
                .update(paymentStatusDTO.getPaymentMethodToken(), paymentMethodRequest);
    }

}