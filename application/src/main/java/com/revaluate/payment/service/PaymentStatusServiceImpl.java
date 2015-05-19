package com.revaluate.payment.service;

import com.braintreegateway.*;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.domain.account.UserSubscriptionStatus;
import com.revaluate.domain.payment.PaymentDetailsDTO;
import com.revaluate.domain.payment.PaymentStatusDTO;
import com.revaluate.domain.payment.insights.*;
import com.revaluate.payment.exception.PaymentStatusException;
import com.revaluate.payment.persistence.PaymentStatus;
import com.revaluate.payment.persistence.PaymentStatusRepository;
import com.revaluate.resource.payment.PaymentException;
import com.revaluate.resource.payment.PaymentService;
import org.dozer.DozerBeanMapper;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Validated
public class PaymentStatusServiceImpl implements PaymentStatusService {

    @Autowired
    private PaymentStatusRepository paymentStatusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setPaymentStatusRepository(PaymentStatusRepository paymentStatusRepository) {
        this.paymentStatusRepository = paymentStatusRepository;
    }

    @Override
    public PaymentInsightsDTO fetchPaymentInsights(String customerId) throws PaymentStatusException {
        try {
            //-----------------------------------------------------------------
            // Fetch customer details
            //-----------------------------------------------------------------
            Customer customer = paymentService.findCustomer(customerId);
            PaymentCustomerDTO paymentCustomerDTO = new PaymentCustomerDTOBuilder()
                    .withEmail(customer.getEmail())
                    .withFirstName(customer.getFirstName())
                    .withLastName(customer.getLastName())
                    .build();

            //-----------------------------------------------------------------
            // Fetch transactions
            //-----------------------------------------------------------------
            ResourceCollection<Transaction> resourceCollection = paymentService.findTransactions(customerId);
            List<Transaction> transactions = StreamSupport
                    .stream(Spliterators.spliteratorUnknownSize(resourceCollection.iterator(), Spliterator.ORDERED),
                            Boolean.FALSE)
                    .collect(Collectors.toList());

            List<PaymentTransactionDTO> paymentTransactions = transactions
                    .stream()
                    .map(transaction -> new PaymentTransactionDTOBuilder()
                            .withId(transaction.getId())
                            .withAmount(transaction.getAmount().doubleValue())
                            .withCurrencyCode(transaction.getCurrencyIsoCode())
                            .withRecurring(transaction.getRecurring())
                            .withStatus(transaction.getStatus().toString())
                            .withCreatedAt(LocalDateTime.fromDateFields((transaction.getCreatedAt().getTime())))
                            .build())
                    .collect(Collectors.toList());

            //-----------------------------------------------------------------
            // Fetch payment details
            //-----------------------------------------------------------------
            List<PaymentMethodDTO> paymentMethods = customer
                    .getCreditCards()
                    .stream()
                    .map(creditCard -> new PaymentMethodDTOBuilder()
                            .withBin(creditCard.getBin())
                            .withCardType(creditCard.getCardType())
                            .withExpirationMonth(creditCard.getExpirationMonth())
                            .withExpirationYear(creditCard.getExpirationYear())
                            .withImageUrl(creditCard.getImageUrl())
                            .withLast4(creditCard.getLast4())
                            .build())
                    .collect(Collectors.toList());

            return new PaymentInsightsDTOBuilder()
                    .withPaymentCustomerDTO(paymentCustomerDTO)
                    .withPaymentMethodDTOs(paymentMethods)
                    .withPaymentTransactionDTOs(paymentTransactions)
                    .build();
        } catch (PaymentException ex) {

            throw new PaymentStatusException(ex);
        }
    }

    @Override
    public PaymentInsightsDTO subscribeToStandardPlan(int userId) throws PaymentStatusException {
        //-----------------------------------------------------------------
        // Fetch payment status
        //-----------------------------------------------------------------
        PaymentStatusDTO paymentStatusDTO = findPaymentStatus(userId);

        //-----------------------------------------------------------------
        // Apply subscription
        //-----------------------------------------------------------------
        try {
            Result<Subscription> subscriptionResult = paymentService.subscribeToStandardPlan(paymentStatusDTO);

            //-----------------------------------------------------------------
            // Throw exception if not successful
            //-----------------------------------------------------------------
            if (!subscriptionResult.isSuccess()) {
                List<String> errors = subscriptionResult
                        .getErrors()
                        .getAllDeepValidationErrors()
                        .stream()
                        .map(ValidationError::getMessage)
                        .collect(Collectors.toList());

                throw new PaymentStatusException(errors);
            }

            return fetchPaymentInsights(paymentStatusDTO.getCustomerId());

        } catch (PaymentException ex) {

            throw new PaymentStatusException(ex);
        }
    }

    @Override
    public PaymentStatusDTO findPaymentStatus(int userId) throws PaymentStatusException {
        Optional<PaymentStatus> byUserId = paymentStatusRepository.findOneByUserId(userId);

        return dozerBeanMapper.map(byUserId.orElseThrow(() -> new PaymentStatusException("There is no payment status for this user")), PaymentStatusDTO.class);
    }

    @Override
    public PaymentStatusDTO createPaymentStatus(PaymentDetailsDTO paymentDetailsDTO, int userId) throws PaymentStatusException {
        Result<Customer> customerResult = paymentService.createCustomerWithPaymentMethod(paymentDetailsDTO);

        //-----------------------------------------------------------------
        // Throw exception if not successful
        //-----------------------------------------------------------------
        if (!customerResult.isSuccess()) {
            List<String> errors = customerResult
                    .getErrors()
                    .getAllDeepValidationErrors()
                    .stream()
                    .map(ValidationError::getMessage)
                    .collect(Collectors.toList());

            throw new PaymentStatusException(errors);
        }
        Customer customer = customerResult.getTarget();

        PaymentStatus paymentStatus = new PaymentStatus();
        //-----------------------------------------------------------------
        // Set user
        //-----------------------------------------------------------------
        User user = userRepository.findOne(userId);
        paymentStatus.setUser(user);

        //-----------------------------------------------------------------
        // Set customer id
        //-----------------------------------------------------------------
        paymentStatus.setCustomerId(customer.getId());

        //-----------------------------------------------------------------
        // Payment token optional
        //-----------------------------------------------------------------
        Optional<String> paymentTokenOptional = customer.getCreditCards().stream().findFirst().map(CreditCard::getToken);
        paymentStatus.setPaymentMethodToken(paymentTokenOptional.orElseThrow(() -> new PaymentStatusException("No payment method defined!")));

        //-----------------------------------------------------------------
        // Save and return
        //-----------------------------------------------------------------
        PaymentStatus savedPaymentStatus = paymentStatusRepository.save(paymentStatus);

        //-----------------------------------------------------------------
        // Update the user with the subscription status
        //-----------------------------------------------------------------
        user.setUserSubscriptionStatus(UserSubscriptionStatus.ACTIVE);
        userRepository.save(user);

        return dozerBeanMapper.map(savedPaymentStatus, PaymentStatusDTO.class);
    }

    @Override
    public PaymentStatusDTO updateCustomer(PaymentDetailsDTO paymentDetailsDTO, int userId) throws PaymentStatusException {
        PaymentStatusDTO paymentStatusDTOByUserId = findPaymentStatus(userId);
        Result<Customer> customerResult = paymentService.updateCustomerDetails(paymentStatusDTOByUserId, paymentDetailsDTO);

        //-----------------------------------------------------------------
        // Throw exception if not successful
        //-----------------------------------------------------------------
        if (!customerResult.isSuccess()) {
            List<String> errors = customerResult
                    .getErrors()
                    .getAllDeepValidationErrors()
                    .stream()
                    .map(ValidationError::getMessage)
                    .collect(Collectors.toList());

            throw new PaymentStatusException(errors);
        }
        Customer customer = customerResult.getTarget();

        Optional<PaymentStatus> byCurrencyCode = paymentStatusRepository.findOneByUserId(userId);
        PaymentStatus paymentStatus = byCurrencyCode.orElseThrow(() -> new PaymentStatusException("There is no payment status for this user"));

        //-----------------------------------------------------------------
        // Set customer id
        //-----------------------------------------------------------------
        paymentStatus.setCustomerId(customer.getId());

        //-----------------------------------------------------------------
        // Save and return
        //-----------------------------------------------------------------
        PaymentStatus savedPaymentStatus = paymentStatusRepository.save(paymentStatus);

        return dozerBeanMapper.map(savedPaymentStatus, PaymentStatusDTO.class);
    }

    @Override
    public PaymentStatusDTO updatePaymentMethod(PaymentDetailsDTO paymentDetailsDTO, int userId) throws PaymentStatusException {
        PaymentStatusDTO paymentStatusDTOByUserId = findPaymentStatus(userId);
        Result<? extends PaymentMethod> paymentMethodResult = paymentService.updatePaymentMethod(paymentStatusDTOByUserId, paymentDetailsDTO);
        //-----------------------------------------------------------------
        // Throw exception if not successful
        //-----------------------------------------------------------------
        if (!paymentMethodResult.isSuccess()) {
            List<String> errors = paymentMethodResult
                    .getErrors()
                    .getAllDeepValidationErrors()
                    .stream()
                    .map(ValidationError::getMessage)
                    .collect(Collectors.toList());

            throw new PaymentStatusException(errors);
        }
        PaymentMethod paymentMethod = paymentMethodResult.getTarget();

        Optional<PaymentStatus> byCurrencyCode = paymentStatusRepository.findOneByUserId(userId);
        PaymentStatus paymentStatus = byCurrencyCode.orElseThrow(() -> new PaymentStatusException("There is no payment status for this user"));

        //-----------------------------------------------------------------
        // Set newly payment token id
        //-----------------------------------------------------------------
        paymentStatus.setPaymentMethodToken(paymentMethod.getToken());

        //-----------------------------------------------------------------
        // Save and return
        //-----------------------------------------------------------------
        PaymentStatus savedPaymentStatus = paymentStatusRepository.save(paymentStatus);

        return dozerBeanMapper.map(savedPaymentStatus, PaymentStatusDTO.class);
    }
}