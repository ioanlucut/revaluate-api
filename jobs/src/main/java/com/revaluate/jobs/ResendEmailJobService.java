package com.revaluate.jobs;

import com.revaluate.core.annotations.EmailSenderQualifier;
import com.revaluate.domain.email.EmailStatus;
import com.revaluate.email.persistence.EmailFeedback;
import com.revaluate.email.persistence.EmailFeedbackRepository;
import com.revaluate.email.persistence.EmailToken;
import com.revaluate.email.persistence.EmailTokenRepository;
import com.revaluate.email.service.EmailAsyncSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResendEmailJobService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResendEmailJobService.class);
    public static final int RE_SEND_EMAIL_FIXED_DELAYS = 15 * 600000; // 15 min

    @Autowired
    @EmailSenderQualifier(value = EmailSenderQualifier.EmailSenderType.TO_USER)
    private EmailAsyncSender<EmailToken> emailTokenAsyncSender;

    @Autowired
    @EmailSenderQualifier(value = EmailSenderQualifier.EmailSenderType.FEEDBACK)
    private EmailAsyncSender<EmailFeedback> feedbackEmailAsyncSender;

    @Autowired
    private EmailFeedbackRepository emailFeedbackRepository;

    @Autowired
    private EmailTokenRepository emailTokenRepository;

    public void sendTokenEmails() {
        List<EmailToken> allByTokenValidatedFalse = emailTokenRepository.findAllByEmailStatus(EmailStatus.SENT_UNSUCCESSFUL);
        LOGGER.info(String.format("Fetched %s email tokens", allByTokenValidatedFalse));

        //-----------------------------------------------------------------
        // Try to sent everything async
        //-----------------------------------------------------------------
        allByTokenValidatedFalse.forEach(emailTokenAsyncSender::tryToSendEmail);
    }

    public void sendFeedbackEmails() {
        List<EmailFeedback> allBySentFalse = emailFeedbackRepository.findAllByEmailStatus(EmailStatus.SENT_UNSUCCESSFUL);
        LOGGER.info(String.format("Fetched %s feedback emails", allBySentFalse));

        //-----------------------------------------------------------------
        // Try to sent everything async
        //-----------------------------------------------------------------
        allBySentFalse.forEach(feedbackEmailAsyncSender::tryToSendEmail);
    }
}