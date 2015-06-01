package com.revaluate.email.service;

import com.revaluate.core.annotations.EmailSenderQualifier;
import com.revaluate.domain.email.EmailStatus;
import com.revaluate.domain.email.SendTo;
import com.revaluate.email.SendEmailException;
import com.revaluate.email.SendEmailWrapperException;
import com.revaluate.email.persistence.EmailToken;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.function.Function;

@Service
@Validated
@EmailSenderQualifier(value = EmailSenderQualifier.EmailSenderType.TO_USER)
public class EmailTokenAsyncSenderImpl extends EmailAsyncSenderAbstract<EmailToken> {

    @Override
    public Function<EmailToken, EmailStatus> getApplyFunction() {
        return emailCandidate -> {
            try {
                return sendEmailService.sendNonAsyncEmailTo(dozerBeanMapper.map(emailCandidate, SendTo.class));
            } catch (SendEmailException ex) {
                throw new SendEmailWrapperException(ex.getCause());
            }
        };

    }
}