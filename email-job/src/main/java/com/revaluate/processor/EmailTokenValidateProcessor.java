package com.revaluate.processor;

import com.revaluate.account.persistence.EmailToken;
import com.revaluate.account.persistence.EmailTokenRepository;
import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailTokenValidateProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailTokenValidateProcessor.class);

    @Autowired
    private EmailTokenRepository emailTokenRepository;

    public void validateEmailToken(Exchange exchange) {
        EmailToken emailToken = exchange.getIn().getBody(EmailToken.class);

        //-----------------------------------------------------------------
        // Save the email updated token email
        //-----------------------------------------------------------------
        emailTokenRepository.save(emailToken);

        LOGGER.info(String.format("Email token to be set as validated: %s", emailToken));
    }
}