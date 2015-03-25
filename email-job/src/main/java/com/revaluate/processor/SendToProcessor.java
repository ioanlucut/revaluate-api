package com.revaluate.processor;

import com.microtripit.mandrillapp.lutung.MandrillApi;
import com.microtripit.mandrillapp.lutung.model.MandrillApiError;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.revaluate.account.persistence.EmailType;
import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.domain.SendTo;
import com.revaluate.exceptions.SendEmailException;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class SendToProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendToProcessor.class);
    private static final boolean NO_ASYNC_EMAIL = false;
    private static final String EMAIL_SENT = "sent";
    private static final String RESET_PASSWORD_LINK = "RESET_PASSWORD_LINK";

    @Autowired
    private ConfigProperties configProperties;

    /**
     * Fetch all existing email tokens
     */
    public void consumeAllEmailTokens(Exchange exchange) throws SendEmailException {
        SendTo sendTo = exchange.getIn().getBody(SendTo.class);

        LOGGER.info(String.format("Send email with details: %s", sendTo));

        sendEmailTo(sendTo);
    }

    private void sendEmailTo(SendTo sendTo) throws SendEmailException {
        MandrillApi mandrillApi = new MandrillApi(configProperties.getMandrillAppKey());

        //-----------------------------------------------------------------
        // Build the mandrill message
        //-----------------------------------------------------------------
        MandrillMessage message = new MandrillMessage();
        message.setSubject("Hello");
        message.setFromEmail("info@revaluate.io");
        message.setFromName("Revaluate team");

        //-----------------------------------------------------------------
        // Build mandrill recipients
        //-----------------------------------------------------------------
        List<MandrillMessage.Recipient> recipients = new ArrayList<>();
        MandrillMessage.Recipient recipient = new MandrillMessage.Recipient();
        recipient.setEmail(configProperties.isProduction() ? sendTo.getEmail() : configProperties.getSupportEmailRecipient());
        recipient.setName(String.format("%s %s", sendTo.getFirstName(), sendTo.getLastName()));
        recipients.add(recipient);
        message.setTo(recipients);

        //-----------------------------------------------------------------
        // Send email
        //-----------------------------------------------------------------
        tryToSendEmail(sendTo, mandrillApi, message);
    }

    private void tryToSendEmail(SendTo sendTo, MandrillApi mandrillApi, MandrillMessage message) throws SendEmailException {
        try {
            Map<String, String> templateContent = new HashMap<>();
            if (sendTo.getEmailType() == EmailType.RESET_PASSWORD) {
                templateContent.put(RESET_PASSWORD_LINK, "http://dev.revaluate.io/reset_password/" + sendTo.getEmailToken());
            }
            MandrillMessageStatus[] messageStatusReports = mandrillApi.messages().sendTemplate(sendTo.getEmailType().getEmailTemplateName(), templateContent, message, NO_ASYNC_EMAIL);
            MandrillMessageStatus messageStatusReport = messageStatusReports[0];
            if (!EMAIL_SENT.equals(messageStatusReport.getStatus())) {
                throw new SendEmailException(String.format("Email not sent %s. Cause %s", sendTo, messageStatusReport));
            }
            LOGGER.info(Arrays.toString(messageStatusReports));
        } catch (MandrillApiError | IOException ex) {
            throw new SendEmailException(ex);
        }
    }
}