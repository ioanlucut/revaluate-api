package com.revaluate.email;

import com.microtripit.mandrillapp.lutung.model.MandrillApiError;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.revaluate.core.bootstrap.ConfigProperties;
import com.revaluate.domain.contact.ContactDTO;
import com.revaluate.domain.email.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.IOException;
import java.util.*;

@Service
@Validated
public class SendEmailServiceImpl implements SendEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailServiceImpl.class);

    private static final String RESET_PASSWORD_LINK = "RESET_PASSWORD_LINK";
    private static final String CONFIRM_EMAIL_LINK = "CONFIRM_EMAIL_LINK";
    private static final String REVALUATE_TEAM_NAME = "Revaluate team";
    public static final int CONTACT_MESSAGE_TRUNCATE = 20;

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private MandrillService mandrillService;

    @Override
    public MandrillEmailStatus sendNonAsyncEmailTo(SendTo sendTo) throws SendEmailException {
        LOGGER.info(String.format("Send non async email with details: %s", sendTo));

        return sendEmailTo(sendTo, Boolean.FALSE);
    }

    private MandrillEmailStatus sendEmailTo(SendTo sendTo, boolean sendAsync) throws SendEmailException {
        //-----------------------------------------------------------------
        // Build the mandrill message
        //-----------------------------------------------------------------
        MandrillMessage message = new MandrillMessage();
        if (sendTo.getEmailType().getEmailReply() == EmailReply.REPLY) {
            message.setFromEmail(configProperties.getReplyEmailRecipient());
        } else if (sendTo.getEmailType().getEmailReply() == EmailReply.NO_REPLY) {
            message.setFromEmail(configProperties.getNoReplyEmailRecipient());
        }
        message.setFromName(REVALUATE_TEAM_NAME);

        //-----------------------------------------------------------------
        // Build mandrill recipients
        //-----------------------------------------------------------------
        List<MandrillMessage.Recipient> recipients = new ArrayList<>();
        MandrillMessage.Recipient recipient = new MandrillMessage.Recipient();
        recipient.setEmail(configProperties.isProduction() ? sendTo.getEmail() : configProperties.getCommonEmailsRecipient());
        recipient.setName(String.format("%s %s", sendTo.getFirstName(), sendTo.getLastName()));
        recipients.add(recipient);
        message.setTo(recipients);

        //-----------------------------------------------------------------
        // Send email
        //-----------------------------------------------------------------
        try {
            return tryToSendEmail(sendTo, message, sendAsync);
        } catch (MandrillApiError | IOException ex) {
            throw new SendEmailException(ex);
        }
    }

    private MandrillEmailStatus tryToSendEmail(SendTo sendTo, MandrillMessage message, boolean sendAsync) throws SendEmailException, IOException, MandrillApiError {
        Map<String, String> templateContent = new HashMap<>();
        if (sendTo.getEmailType() == EmailType.RESET_PASSWORD) {
            String resetPasswordLink = String.format(configProperties.getResetPasswordURLFormat(), configProperties.getWebsiteHost(), sendTo.getEmail(), sendTo.getEmailToken());
            templateContent.put(RESET_PASSWORD_LINK, resetPasswordLink);
        } else if (sendTo.getEmailType() == EmailType.CREATED_ACCOUNT) {
            String confirmEmailLink = String.format(configProperties.getConfirmEmailURLFormat(), configProperties.getWebsiteHost(), sendTo.getEmail(), sendTo.getEmailToken());
            templateContent.put(CONFIRM_EMAIL_LINK, confirmEmailLink);
        }
        MandrillMessageStatus[] messageStatusReports = mandrillService.getApi().sendTemplate(sendTo.getEmailType().getEmailTemplateName(), templateContent, message, sendAsync);

        return interpretEmailStatus(sendTo, sendAsync, messageStatusReports);
    }

    @Override
    public MandrillEmailStatus sendNonAsyncFeedbackEmailTo(SendFeedbackTo sendFeedbackTo) throws SendEmailException {
        //-----------------------------------------------------------------
        // Build the mandrill message
        //-----------------------------------------------------------------
        MandrillMessage message = new MandrillMessage();

        message.setFromEmail(sendFeedbackTo.getEmail());
        message.setFromName(String.format("%s %s", sendFeedbackTo.getFirstName(), sendFeedbackTo.getLastName()));
        message.setAutoText(Boolean.TRUE);
        message.setSubject(sendFeedbackTo.getSubject());
        message.setText(sendFeedbackTo.getMessage());

        //-----------------------------------------------------------------
        // Build mandrill recipients
        //-----------------------------------------------------------------
        List<MandrillMessage.Recipient> recipients = new ArrayList<>();
        MandrillMessage.Recipient recipient = new MandrillMessage.Recipient();
        recipient.setEmail(configProperties.getReplyEmailRecipient());
        recipient.setName(REVALUATE_TEAM_NAME);
        recipients.add(recipient);
        message.setTo(recipients);

        try {
            MandrillMessageStatus[] messageStatusReports = mandrillService.getApi().send(message, Boolean.FALSE);
            return interpretEmailStatus(sendFeedbackTo, Boolean.FALSE, messageStatusReports);
        } catch (MandrillApiError | IOException ex) {
            throw new SendEmailException(ex);
        }
    }

    @Override
    public MandrillEmailStatus sendNonAsyncContactEmail(ContactDTO contactDTO) throws SendEmailException {
        //-----------------------------------------------------------------
        // Build the mandrill message
        //-----------------------------------------------------------------
        MandrillMessage message = new MandrillMessage();

        message.setFromEmail(contactDTO.getEmail());
        message.setFromName(contactDTO.getName());
        message.setAutoText(Boolean.TRUE);
        message.setSubject(contactDTO.getMessage().length() > CONTACT_MESSAGE_TRUNCATE ? contactDTO.getMessage().substring(0, CONTACT_MESSAGE_TRUNCATE) + "..." : contactDTO.getMessage());
        message.setText(contactDTO.getMessage());

        //-----------------------------------------------------------------
        // Build mandrill recipients
        //-----------------------------------------------------------------
        List<MandrillMessage.Recipient> recipients = new ArrayList<>();
        MandrillMessage.Recipient recipient = new MandrillMessage.Recipient();
        recipient.setEmail(configProperties.getReplyEmailRecipient());
        recipient.setName(REVALUATE_TEAM_NAME);
        recipients.add(recipient);
        message.setTo(recipients);

        try {
            MandrillMessageStatus[] messageStatusReports = mandrillService.getApi().send(message, Boolean.FALSE);
            return interpretEmailStatus(contactDTO, Boolean.FALSE, messageStatusReports);
        } catch (MandrillApiError | IOException ex) {
            throw new SendEmailException(ex);
        }
    }

    private MandrillEmailStatus interpretEmailStatus(Object sendTo, boolean sendAsync, MandrillMessageStatus[] messageStatusReports) throws SendEmailException {
        MandrillMessageStatus messageStatusReport = messageStatusReports[0];
        LOGGER.info(Arrays.toString(messageStatusReports));

        MandrillEmailStatus mandrillEmailStatus = MandrillEmailStatus.from(messageStatusReport.getStatus());

        //-----------------------------------------------------------------
        // If email is required
        //-----------------------------------------------------------------
        if (MandrillEmailStatus.REJECTED == mandrillEmailStatus) {

            throw new SendEmailException(String.format("Email rejected %s. Cause %s", sendTo, messageStatusReport));
        }

        if (sendAsync) {

            //-----------------------------------------------------------------
            // Check if email is queued
            //-----------------------------------------------------------------
            if (MandrillEmailStatus.QUEUED != mandrillEmailStatus) {

                throw new SendEmailException(String.format("Email not queued %s. Cause %s", sendTo, messageStatusReport));
            }
        } else {

            //-----------------------------------------------------------------
            // Check if email is sent
            //-----------------------------------------------------------------
            if (MandrillEmailStatus.SENT != mandrillEmailStatus) {

                throw new SendEmailException(String.format("Email not sent %s. Cause %s", sendTo, messageStatusReport));
            }
        }

        return mandrillEmailStatus;
    }

}