package com.revaluate.account.service;

import com.google.common.util.concurrent.Futures;
import com.revaluate.account.persistence.EmailToken;
import com.revaluate.account.persistence.EmailTokenRepository;
import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.domain.email.EmailStatus;
import com.revaluate.domain.email.SendTo;
import com.revaluate.email.SendEmailException;
import com.revaluate.email.SendEmailService;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.concurrent.Future;

@Service
@Validated
public class EmailAsyncSenderImpl implements EmailAsyncSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailAsyncSenderImpl.class);

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    private EmailTokenRepository emailTokenRepository;

    @Autowired
    private SendEmailService sendEmailService;

    @Autowired
    private ConfigProperties configProperties;

    @Async
    public Future<EmailStatus> tryToSendEmail(EmailToken emailToken) {

        //-----------------------------------------------------------------
        // Do not send email for some environments
        //-----------------------------------------------------------------
        if (configProperties.isSkipSendEmail()) {
            LOGGER.info("Email not sent - skipped.");

            return Futures.immediateCancelledFuture();
        }

        //-----------------------------------------------------------------
        // Try to send email async
        //-----------------------------------------------------------------
        SendTo sendTo = dozerBeanMapper.map(emailToken, SendTo.class);
        try {
            EmailStatus emailStatus = sendEmailService.sendNonAsyncEmailTo(sendTo);

            //-----------------------------------------------------------------
            // Mark email token as validated
            //-----------------------------------------------------------------
            emailToken.setValidated(Boolean.TRUE);
            emailTokenRepository.save(emailToken);

            return new AsyncResult<>(emailStatus);
        } catch (SendEmailException ex) {
            LOGGER.error(ex.getMessage(), ex);

            return Futures.immediateFailedFuture(ex);
        }
    }
}