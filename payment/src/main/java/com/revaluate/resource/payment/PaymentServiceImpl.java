package com.revaluate.resource.payment;

import com.braintreegateway.*;
import com.braintreegateway.exceptions.NotFoundException;
import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.domain.payment.PaymentCustomerDetailsDTO;
import com.revaluate.domain.payment.PaymentDetailsDTO;
import com.revaluate.domain.payment.PaymentNonceDetailsDTO;
import com.revaluate.domain.payment.PaymentStatusDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@Validated
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private BraintreeGatewayService braintreeGatewayService;

    @Autowired
    private ConfigProperties configProperties;

    @Override
    public String fetchToken(String customerId) throws PaymentException {
        ClientTokenRequest clientTokenRequest = new ClientTokenRequest();

        Optional<String> token = Optional.ofNullable(braintreeGatewayService
                .getBraintreeGateway()
                .clientToken()
                .generate(clientTokenRequest));

        return token.orElseThrow(() -> new PaymentException("Customer id is not valid"));
    }

    @Override
    public String fetchToken() throws PaymentException {

        return braintreeGatewayService
                .getBraintreeGateway()
                .clientToken()
                .generate();
    }

    @Override
    public Customer findCustomer(String customerId) throws PaymentException {
        try {
            return braintreeGatewayService
                    .getBraintreeGateway()
                    .customer()
                    .find(customerId);
        } catch (NotFoundException ex) {

            throw new PaymentException(ex);
        }
    }

    @Override
    public ResourceCollection<Transaction> findTransactions(String customerId) {
        TransactionSearchRequest request = new TransactionSearchRequest()
                .customerId()
                .is(customerId);

        return braintreeGatewayService
                .getBraintreeGateway()
                .transaction()
                .search(request);
    }

    @Override
    public Result<Customer> createCustomerWithPaymentMethod(PaymentDetailsDTO paymentDetailsDTO) {
        CustomerRequest customerRequest = new CustomerRequest()
                .firstName(paymentDetailsDTO.getPaymentCustomerDetailsDTO().getFirstName())
                .lastName(paymentDetailsDTO.getPaymentCustomerDetailsDTO().getLastName())
                .email(paymentDetailsDTO.getPaymentCustomerDetailsDTO().getEmail())
                .paymentMethodNonce(paymentDetailsDTO.getPaymentNonceDetailsDTO().getPaymentMethodNonce())
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
    public Result<Subscription> subscribeToStandardPlan(PaymentStatusDTO paymentStatusDTO) throws PaymentException {
        Customer customer = findCustomer(paymentStatusDTO.getCustomerId());
        boolean isAlreadySubscriptionDefined = customer
                .getCreditCards()
                .stream()
                .flatMap(creditCard -> creditCard.getSubscriptions().stream())
                .anyMatch(subscription -> subscription.getPlanId().equals(configProperties.getBraintreePlanId()));

        //-----------------------------------------------------------------
        // Throw exception if someone tries to add subscription again
        //-----------------------------------------------------------------
        if (isAlreadySubscriptionDefined) {

            throw new PaymentException("Subscription already defined");
        }

        //-----------------------------------------------------------------
        // We check if there is a plan with given plan ID
        //-----------------------------------------------------------------
        String planId = braintreeGatewayService
                .getBraintreeGateway()
                .plan()
                .all()
                .stream()
                .filter(plan -> plan.getId().equals(configProperties.getBraintreePlanId()))
                .findAny()
                .map(Plan::getId)
                .orElseThrow(() -> new PaymentException("No plan found"));

        //-----------------------------------------------------------------
        // Subscription request
        //-----------------------------------------------------------------
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest()
                .paymentMethodToken(paymentStatusDTO.getPaymentMethodToken())
                .planId(planId);

        return braintreeGatewayService
                .getBraintreeGateway()
                .subscription()
                .create(subscriptionRequest);
    }

    @Override
    public Result<Customer> updateCustomerDetails(PaymentStatusDTO paymentStatusDTO, PaymentCustomerDetailsDTO paymentCustomerDetailsDTO) {
        CustomerRequest updateCustomerRequest = new CustomerRequest()
                .firstName(paymentCustomerDetailsDTO.getFirstName())
                .lastName(paymentCustomerDetailsDTO.getLastName())
                .email(paymentCustomerDetailsDTO.getEmail());

        return braintreeGatewayService
                .getBraintreeGateway()
                .customer()
                .update(paymentStatusDTO.getCustomerId(), updateCustomerRequest);
    }

    @Override
    public Result<? extends PaymentMethod> updatePaymentMethod(PaymentStatusDTO paymentStatusDTO, PaymentNonceDetailsDTO paymentNonceDetailsDTO) {
        PaymentMethodRequest paymentMethodRequest = new PaymentMethodRequest()
                .paymentMethodNonce(paymentNonceDetailsDTO.getPaymentMethodNonce())
                    .options()
                        .verifyCard(Boolean.TRUE)
                    .done();

        return braintreeGatewayService
                .getBraintreeGateway()
                .paymentMethod()
                .update(paymentStatusDTO.getPaymentMethodToken(), paymentMethodRequest);
    }

}