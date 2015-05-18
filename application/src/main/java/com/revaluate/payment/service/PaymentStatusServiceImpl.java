package com.revaluate.payment.service;

import com.braintreegateway.CreditCard;
import com.braintreegateway.Customer;
import com.braintreegateway.Result;
import com.braintreegateway.ValidationError;
import com.revaluate.account.persistence.User;
import com.revaluate.account.persistence.UserRepository;
import com.revaluate.domain.payment.PaymentDetailsDTO;
import com.revaluate.domain.payment.PaymentStatusDTO;
import com.revaluate.payment.exception.PaymentStatusException;
import com.revaluate.payment.persistence.PaymentStatus;
import com.revaluate.payment.persistence.PaymentStatusRepository;
import com.revaluate.resource.payment.PaymentService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public PaymentStatusDTO findOneByUserId(int userId) throws PaymentStatusException {
        Optional<PaymentStatus> byCurrencyCode = paymentStatusRepository.findOneByUserId(userId);

        return dozerBeanMapper.map(byCurrencyCode.orElseThrow(() -> new PaymentStatusException("There is no payment status for this user")), PaymentStatusDTO.class);

    }

    @Override
    public PaymentStatusDTO createPaymentStatus(PaymentDetailsDTO paymentDetailsDTO, int userId) throws PaymentStatusException {
        Result<Customer> customerResult = paymentService.createPaymentStatus(paymentDetailsDTO);

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
        paymentStatus.setPaymentMethodToken(paymentTokenOptional.orElseThrow(() -> new PaymentStatusException("No payment method exists!")));

        //-----------------------------------------------------------------
        // Save and return
        //-----------------------------------------------------------------
        PaymentStatus savedPaymentStatus = paymentStatusRepository.save(paymentStatus);

        return dozerBeanMapper.map(savedPaymentStatus, PaymentStatusDTO.class);
    }
}