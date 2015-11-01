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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
        List<MandrillMessage.MergeVar> globalMergeVars = new ArrayList<>();

        if (sendTo.getEmailType() == EmailType.RESET_PASSWORD) {
            String resetPasswordLink = String.format(configProperties.getResetPasswordURLFormat(), configProperties.getWebsiteHost(), sendTo.getEmail(), sendTo.getEmailToken());

            MandrillMessage.MergeVar mergeVar = new MandrillMessage.MergeVar();
            mergeVar.setName(RESET_PASSWORD_LINK);
            mergeVar.setContent(resetPasswordLink);
            globalMergeVars.add(mergeVar);
        } else if (sendTo.getEmailType() == EmailType.CREATED_ACCOUNT) {
            String confirmEmailLink = String.format(configProperties.getConfirmEmailURLFormat(), configProperties.getWebsiteHost(), sendTo.getEmail(), sendTo.getEmailToken());

            MandrillMessage.MergeVar mergeVar = new MandrillMessage.MergeVar();
            mergeVar.setName(CONFIRM_EMAIL_LINK);
            mergeVar.setContent(confirmEmailLink);
            globalMergeVars.add(mergeVar);
        }

        message.setGlobalMergeVars(globalMergeVars);
        String emailTemplateName = sendTo.getEmailType().getEmailTemplateName();
        MandrillMessageStatus[] messageStatusReports = mandrillService.getApi().sendTemplate(configProperties.isProduction() ? emailTemplateName : emailTemplateName + "-dev", new HashMap<>(), message, sendAsync);

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
        if (messageStatusReports == null) {
            throw new SendEmailException(String.format("Message status reports is null for %s.", sendTo));
        }

        if (messageStatusReports.length == 0) {
            throw new SendEmailException(String.format("Message status reports is empty for %s.", sendTo));
        }

        //-----------------------------------------------------------------
        // Log the status
        //-----------------------------------------------------------------
        Arrays
                .asList(messageStatusReports)
                .stream()
                .forEach(mandrillMessageStatus -> {
                    LOGGER.info(String.format("Message status report: status %s, reject_reason %s for %s.", mandrillMessageStatus.getStatus(), mandrillMessageStatus.getRejectReason(), sendTo));
                });

        //-----------------------------------------------------------------
        // Interpret the message
        //-----------------------------------------------------------------
        MandrillMessageStatus messageStatusReport = messageStatusReports[0];
        MandrillEmailStatus mandrillEmailStatus = MandrillEmailStatus.from(messageStatusReport.getStatus());

        //-----------------------------------------------------------------
        // If email is required
        //-----------------------------------------------------------------
        if (MandrillEmailStatus.REJECTED == mandrillEmailStatus || MandrillEmailStatus.INVALID == mandrillEmailStatus) {

            throw new SendEmailException(String.format("Email rejected %s. Cause %s", sendTo, messageStatusReport));
        }

        if (sendAsync) {

            //-----------------------------------------------------------------
            // Check if email is queued
            //-----------------------------------------------------------------
            if (MandrillEmailStatus.QUEUED != mandrillEmailStatus && MandrillEmailStatus.SCHEDULED != mandrillEmailStatus) {

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