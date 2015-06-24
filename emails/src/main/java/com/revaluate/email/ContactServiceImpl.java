package com.revaluate.email;

import com.google.common.util.concurrent.Futures;
import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.domain.contact.ContactDTO;
import com.revaluate.domain.email.MandrillEmailStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class ContactServiceImpl implements ContactService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactServiceImpl.class);

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private SendEmailService sendEmailService;

    @Override
    public Future<MandrillEmailStatus> sendEmailFrom(ContactDTO contactDTO) throws SendEmailException {

        //-----------------------------------------------------------------
        // Do not send email for some environments
        //-----------------------------------------------------------------
        if (configProperties.isSkipSendEmail()) {
            LOGGER.info("Contact email not sent - skipped.");

            return Futures.immediateCancelledFuture();
        }

        //-----------------------------------------------------------------
        // Try to send email
        //-----------------------------------------------------------------
        try {
            MandrillEmailStatus mandrillEmailStatus = sendEmailService.sendNonAsyncContactEmail(contactDTO);

            return new AsyncResult<>(mandrillEmailStatus);
        } catch (SendEmailWrapperException ex) {
            LOGGER.error(ex.getMessage(), ex);

            return Futures.immediateFailedFuture(ex);
        }
    }
}
