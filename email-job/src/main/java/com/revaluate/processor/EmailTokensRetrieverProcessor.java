package com.revaluate.processor;

import com.revaluate.account.persistence.EmailToken;
import com.revaluate.account.persistence.EmailTokenRepository;
import com.revaluate.route.EmailJobRouter;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailTokensRetrieverProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailTokensRetrieverProcessor.class);

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private EmailTokenRepository emailTokenRepository;

    /**
     * Fetch all existing email tokens
     */
    public void fetchAllEmailTokens() {
        List<EmailToken> all = emailTokenRepository.findAllByValidatedFalse();
        LOGGER.info(String.format("Fetched %s email tokens", all));

        producerTemplate.sendBody(EmailJobRouter.DIRECT_ROUTE_FETCH_ALL_EMAIL_TOKENS, all);
    }
}