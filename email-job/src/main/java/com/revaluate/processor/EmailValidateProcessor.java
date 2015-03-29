package com.revaluate.processor;

import com.revaluate.account.persistence.Email;
import com.revaluate.account.persistence.EmailRepository;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailValidateProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailValidateProcessor.class);

    @Autowired
    private EmailRepository emailRepository;

    public void validateEmail(Exchange exchange) {
        Email email = exchange.getIn().getBody(Email.class);

        //-----------------------------------------------------------------
        // Save the email updated token email
        //-----------------------------------------------------------------
        emailRepository.save(email);

        LOGGER.info(String.format("Email token to be set as validated: %s", email));
    }
}