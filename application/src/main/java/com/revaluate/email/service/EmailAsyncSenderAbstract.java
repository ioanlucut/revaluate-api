package com.revaluate.email.service;

import com.google.common.util.concurrent.Futures;
import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.domain.email.EmailStatus;
import com.revaluate.email.SendEmailService;
import com.revaluate.email.SendEmailWrapperException;
import com.revaluate.email.persistence.Email;
import com.revaluate.email.persistence.EmailRepository;
import org.dozer.DozerBeanMapper;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;
import java.util.function.Function;

@Component
public abstract class EmailAsyncSenderAbstract<T extends Email> implements EmailAsyncSender<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailAsyncSenderAbstract.class);

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    protected DozerBeanMapper dozerBeanMapper;

    @Autowired
    protected SendEmailService sendEmailService;

    @Override
    public Future<EmailStatus> tryToSendEmail(T email) {

        //-----------------------------------------------------------------
        // Do not send email for some environments
        //-----------------------------------------------------------------
        if (configProperties.isSkipSendEmail()) {
            LOGGER.info("Email not sent - skipped.");

            return Futures.immediateCancelledFuture();
        }

        //-----------------------------------------------------------------
        // Try to send email
        //-----------------------------------------------------------------
        try {
            EmailStatus emailStatus = getApplyFunction().apply(email);

            //-----------------------------------------------------------------
            // Mark email token as sent
            //-----------------------------------------------------------------
            email.setSent(Boolean.TRUE);
            email.setSentDate(LocalDateTime.now());
            emailRepository.save(email);

            return new AsyncResult<>(emailStatus);
        } catch (SendEmailWrapperException ex) {
            LOGGER.error(ex.getMessage(), ex);

            return Futures.immediateFailedFuture(ex);
        }
    }

    public abstract Function<T, EmailStatus> getApplyFunction();

}