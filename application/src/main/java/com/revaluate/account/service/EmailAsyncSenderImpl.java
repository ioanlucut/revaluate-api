package com.revaluate.account.service;

import com.revaluate.account.persistence.EmailToken;
import com.revaluate.account.persistence.EmailTokenRepository;
import com.revaluate.domain.email.SendTo;
import com.revaluate.email.SendEmailException;
import com.revaluate.email.SendEmailService;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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

    @Async
    public void tryToSendEmail(EmailToken savedCreateEmailToken) {
        //-----------------------------------------------------------------
        // Try to send email async
        //-----------------------------------------------------------------
        SendTo sendTo = dozerBeanMapper.map(savedCreateEmailToken, SendTo.class);
        try {
            sendEmailService.sendAsyncEmailTo(sendTo);

            //-----------------------------------------------------------------
            // Mark as validated - or sent
            //-----------------------------------------------------------------
            savedCreateEmailToken.setValidated(Boolean.TRUE);
            emailTokenRepository.save(savedCreateEmailToken);
        } catch (SendEmailException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }
}