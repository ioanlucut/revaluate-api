package com.revaluate.email;

import com.microtripit.mandrillapp.lutung.model.MandrillApiError;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.domain.email.EmailStatus;
import com.revaluate.domain.email.EmailType;
import com.revaluate.domain.email.SendTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class SendEmailServiceImpl implements SendEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailServiceImpl.class);

    private static final String RESET_PASSWORD_LINK = "RESET_PASSWORD_LINK";

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private MandrillService mandrillService;

    @Override
    public EmailStatus sendAsyncEmailTo(SendTo sendTo) throws SendEmailException {
        LOGGER.info(String.format("Send async email with details: %s", sendTo));

        return sendEmailTo(sendTo, Boolean.TRUE);
    }

    @Override
    public EmailStatus sendNonAsyncEmailTo(SendTo sendTo) throws SendEmailException {
        LOGGER.info(String.format("Send non async email with details: %s", sendTo));

        return sendEmailTo(sendTo, Boolean.FALSE);
    }

    private EmailStatus sendEmailTo(SendTo sendTo, boolean sendAsync) throws SendEmailException {
        //-----------------------------------------------------------------
        // Build the mandrill message
        //-----------------------------------------------------------------
        MandrillMessage message = new MandrillMessage();
        message.setFromEmail(configProperties.getSupportEmailRecipient());
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
        return tryToSendEmail(sendTo, message, sendAsync);
    }

    private EmailStatus tryToSendEmail(SendTo sendTo, MandrillMessage message, boolean sendAsync) throws SendEmailException {
        try {
            Map<String, String> templateContent = new HashMap<>();
            if (sendTo.getEmailType() == EmailType.RESET_PASSWORD) {
                templateContent.put(RESET_PASSWORD_LINK, "http://revaluate.io/reset_password/" + sendTo.getEmailToken());
            }
            MandrillMessageStatus[] messageStatusReports = mandrillService.getApi().sendTemplate(sendTo.getEmailType().getEmailTemplateName(), templateContent, message, sendAsync);
            MandrillMessageStatus messageStatusReport = messageStatusReports[0];
            LOGGER.info(Arrays.toString(messageStatusReports));

            EmailStatus emailStatus = EmailStatus.from(messageStatusReport.getStatus());

            //-----------------------------------------------------------------
            // If email is required
            //-----------------------------------------------------------------
            if (EmailStatus.REJECTED == emailStatus) {

                throw new SendEmailException(String.format("Email rejected %s. Cause %s", sendTo, messageStatusReport));
            }

            if (sendAsync) {

                //-----------------------------------------------------------------
                // Check if email is queued
                //-----------------------------------------------------------------
                if (EmailStatus.QUEUED != emailStatus) {

                    throw new SendEmailException(String.format("Email not queued %s. Cause %s", sendTo, messageStatusReport));
                }
            } else {

                //-----------------------------------------------------------------
                // Check if email is sent
                //-----------------------------------------------------------------
                if (EmailStatus.SENT != emailStatus) {

                    throw new SendEmailException(String.format("Email not sent %s. Cause %s", sendTo, messageStatusReport));
                }
            }

            return emailStatus;
        } catch (MandrillApiError | IOException ex) {
            throw new SendEmailException(ex);
        }
    }

}