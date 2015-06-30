package com.revaluate.email.service;

import com.revaluate.core.annotations.EmailSenderQualifier;
import com.revaluate.domain.email.MandrillEmailStatus;
import com.revaluate.domain.email.SendFeedbackTo;
import com.revaluate.email.SendEmailException;
import com.revaluate.email.SendEmailWrapperException;
import com.revaluate.email.persistence.EmailFeedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.function.Function;

@Service
@Validated
@EmailSenderQualifier(value = EmailSenderQualifier.EmailSenderType.FEEDBACK)
public class EmailFeedbackAsyncSenderImpl extends EmailAsyncSenderAbstract<EmailFeedback> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailFeedbackAsyncSenderImpl.class);

    @Override
    public Function<EmailFeedback, MandrillEmailStatus> getApplyFunction() {
        return emailCandidate -> {
            try {
                return sendEmailService.sendNonAsyncFeedbackEmailTo(dozerBeanMapper.map(emailCandidate, SendFeedbackTo.class));
            } catch (SendEmailException ex) {
                LOGGER.error(ex.getMessage(), ex);

                throw new SendEmailWrapperException(ex.getCause());
            }
        };

    }
}