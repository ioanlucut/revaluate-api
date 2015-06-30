package com.revaluate.email.service;

import com.revaluate.core.annotations.EmailSenderQualifier;
import com.revaluate.domain.email.MandrillEmailStatus;
import com.revaluate.domain.email.SendTo;
import com.revaluate.email.SendEmailException;
import com.revaluate.email.SendEmailWrapperException;
import com.revaluate.email.persistence.EmailToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.function.Function;

@Service
@Validated
@EmailSenderQualifier(value = EmailSenderQualifier.EmailSenderType.TO_USER)
public class EmailTokenAsyncSenderImpl extends EmailAsyncSenderAbstract<EmailToken> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailTokenAsyncSenderImpl.class);

    @Override
    public Function<EmailToken, MandrillEmailStatus> getApplyFunction() {
        return emailCandidate -> {
            try {
                return sendEmailService.sendNonAsyncEmailTo(dozerBeanMapper.map(emailCandidate, SendTo.class));
            } catch (SendEmailException ex) {
                LOGGER.error(ex.getMessage(), ex);

                throw new SendEmailWrapperException(ex.getCause());
            }
        };

    }
}