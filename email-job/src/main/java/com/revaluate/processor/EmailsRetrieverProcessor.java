package com.revaluate.processor;

import com.revaluate.account.persistence.Email;
import com.revaluate.account.persistence.EmailRepository;
import com.revaluate.route.EmailJobRouter;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailsRetrieverProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailsRetrieverProcessor.class);

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private EmailRepository emailRepository;

    /**
     * Fetch all existing email tokens
     */
    public void fetchAllEmails() {
        List<Email> all = emailRepository.findAllByTokenValidatedFalse();
        LOGGER.info(String.format("Fetched %s email tokens", all));

        producerTemplate.sendBody(EmailJobRouter.DIRECT_ROUTE_FETCH_ALL_EMAIL_TOKENS, all);
    }
}