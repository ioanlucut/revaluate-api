package com.revaluate.payment.service;

import com.braintreegateway.*;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.domain.account.UserSubscriptionStatus;
import com.revaluate.domain.payment.PaymentCustomerDetailsDTO;
import com.revaluate.domain.payment.PaymentDetailsDTO;
import com.revaluate.domain.payment.PaymentNonceDetailsDTO;
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

import java.util.*;
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
                    .withId(customer.getId())
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
                            .withCreatedAt(parseDateFrom((transaction.getCreatedAt())))
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
                            .withPaymentSubscriptionDTOList(creditCard
                                    .getSubscriptions()
                                    .stream()
                                    .map(subscription -> new PaymentSubscriptionDTOBuilder()
                                            .withId(subscription.getId())
                                            .withAmount(subscription.getPrice().doubleValue())
                                            .withStatus(subscription.getStatus().toString())
                                            .withCreatedAt(parseDateFrom((subscription.getCreatedAt())))
                                            .withTrialDuration(String.valueOf(subscription.getTrialDuration()))
                                            .withTrialDurationUnit(subscription.getTrialDurationUnit().toString())
                                            .withBillingPeriodStartDate(parseDateFrom((subscription.getBillingPeriodStartDate())))
                                            .withBillingPeriodEndDate(parseDateFrom((subscription.getBillingPeriodEndDate())))
                                            .build())
                                    .collect(Collectors.toList()))
                            .build())
                    .collect(Collectors.toList());

            return new PaymentInsightsDTOBuilder()
                    .withPaymentCustomerDTO(paymentCustomerDTO)
                    .withPaymentMethodDTOs(paymentMethods)
                    .withPaymentTransactionDTOs(paymentTransactions)
                    .withSubscriptionActive(customer
                            .getCreditCards()
                            .stream()
                            .flatMap(creditCard -> creditCard.getSubscriptions().stream())
                            .anyMatch(subscription -> subscription.getStatus() == Subscription.Status.ACTIVE))
                    .build();
        } catch (PaymentException ex) {

            throw new PaymentStatusException(ex);
        }
    }

    private LocalDateTime parseDateFrom(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        return LocalDateTime.fromDateFields(calendar.getTime());
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

                throwExceptionWithGivenPaymentValidationErrors(subscriptionResult);
            }

            //-----------------------------------------------------------------
            // Update the user with the subscription status
            //-----------------------------------------------------------------
            User user = userRepository.findOne(userId);
            user.setUserSubscriptionStatus(UserSubscriptionStatus.ACTIVE);
            userRepository.save(user);

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
    public boolean isPaymentStatusDefined(int userId) {

        return paymentStatusRepository.findOneByUserId(userId).isPresent();
    }

    @Override
    public PaymentStatusDTO createPaymentStatus(PaymentDetailsDTO paymentDetailsDTO, int userId) throws PaymentStatusException {
        //-----------------------------------------------------------------
        // Do not allow another payment status entry to be added for the same user.
        //-----------------------------------------------------------------
        Optional<PaymentStatus> byUserId = paymentStatusRepository.findOneByUserId(userId);
        if (byUserId.isPresent()) {

            throw new PaymentStatusException("There is already a payment method defined.");
        }

        Result<Customer> customerResult = paymentService.createCustomerWithPaymentMethod(paymentDetailsDTO);

        //-----------------------------------------------------------------
        // Throw exception if not successful
        //-----------------------------------------------------------------
        if (!customerResult.isSuccess()) {

            throwExceptionWithGivenPaymentValidationErrors(customerResult);
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

        return dozerBeanMapper.map(savedPaymentStatus, PaymentStatusDTO.class);
    }

    @Override
    public void deleteCustomerWithId(int userId) throws PaymentStatusException {
        //-----------------------------------------------------------------
        // Do not allow another payment status entry to be added for the same user.
        //-----------------------------------------------------------------
        Optional<PaymentStatus> byUserId = paymentStatusRepository.findOneByUserId(userId);
        if (!byUserId.isPresent()) {

            throw new PaymentStatusException("There is no payment method defined.");
        }

        try {
            Result<Customer> customerResult = paymentService.deleteCustomer(byUserId.get().getCustomerId());

            //-----------------------------------------------------------------
            // Throw exception if not successful
            //-----------------------------------------------------------------
            if (!customerResult.isSuccess()) {

                throwExceptionWithGivenPaymentValidationErrors(customerResult);
            }
        } catch (PaymentException ex) {

            throw new PaymentStatusException(ex);
        }

    }

    @Override
    public PaymentStatusDTO updateCustomer(PaymentCustomerDetailsDTO paymentCustomerDetailsDTO, int userId) throws PaymentStatusException {
        //-----------------------------------------------------------------
        // Fetch the payment status for this user id. If not present, exception is thrown
        //-----------------------------------------------------------------
        PaymentStatusDTO paymentStatusDTOByUserId = findPaymentStatus(userId);
        Result<Customer> customerResult = paymentService.updateCustomerDetails(paymentStatusDTOByUserId, paymentCustomerDetailsDTO);

        //-----------------------------------------------------------------
        // Throw exception if not successful
        //-----------------------------------------------------------------
        if (!customerResult.isSuccess()) {

            throwExceptionWithGivenPaymentValidationErrors(customerResult);
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
    public PaymentStatusDTO updatePaymentMethod(PaymentNonceDetailsDTO paymentNonceDetailsDTO, int userId) throws PaymentStatusException {
        PaymentStatusDTO paymentStatusDTOByUserId = findPaymentStatus(userId);
        Result<? extends PaymentMethod> paymentMethodResult = paymentService.updatePaymentMethod(paymentStatusDTOByUserId, paymentNonceDetailsDTO);
        //-----------------------------------------------------------------
        // Throw exception if not successful
        //-----------------------------------------------------------------
        if (!paymentMethodResult.isSuccess()) {

            throwExceptionWithGivenPaymentValidationErrors(paymentMethodResult);
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

    private void throwExceptionWithGivenPaymentValidationErrors(Result<?> result) throws PaymentStatusException {
        List<String> errors = result
                .getErrors()
                .getAllDeepValidationErrors()
                .stream()
                .map(ValidationError::getMessage)
                .collect(Collectors.toList());

        throw new PaymentStatusException(errors);
    }
}