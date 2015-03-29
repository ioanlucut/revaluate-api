package com.revaluate.processor;

import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.domain.email.SendTo;
import com.revaluate.email.SendEmailException;
import com.revaluate.email.SendEmailService;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SendToProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendToProcessor.class);

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private SendEmailService sendEmailService;

    /**
     * Fetch all existing email tokens
     */
    public void consumeAllEmails(Exchange exchange) throws SendEmailException {
        SendTo sendTo = exchange.getIn().getBody(SendTo.class);

        LOGGER.info(String.format("Send email with details: %s", sendTo));

        sendEmailService.sendNonAsyncEmailTo(sendTo);
    }

}