package com.revaluate.route;

import com.revaluate.account.persistence.EmailToken;
import com.revaluate.account.persistence.EmailTokenRepository;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmailTokensProcessor {

    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private EmailTokenRepository emailTokenRepository;

    /**
     * Fetch all existing email tokens
     */
    public void fetchAllEmailTokens() {
        List<EmailToken> all = emailTokenRepository.findAll();
        producerTemplate.sendBody(EmailJobRouter.DIRECT_ROUTE_FETCH_ALL_EMAIL_TOKENS, all);
    }
}