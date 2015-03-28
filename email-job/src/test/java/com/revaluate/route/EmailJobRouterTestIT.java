package com.revaluate.route;

import com.revaluate.AbstractBatchJobIntegrationTests;
import com.revaluate.account.persistence.EmailToken;
import com.revaluate.domain.email.SendTo;
import org.apache.camel.*;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

@Ignore
@MockEndpoints("direct:*")
public class EmailJobRouterTestIT extends AbstractBatchJobIntegrationTests {

    @Autowired
    protected CamelContext camelContext;

    @EndpointInject(uri = "mock:direct:sendToProcessor")
    protected MockEndpoint directSendToProcessorEndpoint;

    @EndpointInject(uri = "mock:direct:validSentEmailToken")
    protected MockEndpoint directValidSentEmailToken;

    @Produce(uri = "direct:fetchAllEmailTokens")
    protected ProducerTemplate producerTemplate;

    @Test
    public void routeWorks() throws Exception {

        //-----------------------------------------------------------------
        // Expected to have a SendTo object
        //-----------------------------------------------------------------
        directSendToProcessorEndpoint.expectedMessageCount(1);
        directValidSentEmailToken.expectedMessageCount(1);

        //-----------------------------------------------------------------
        // Create a user and generate an email token
        //-----------------------------------------------------------------
        createUserDTO();
        List<EmailToken> allByValidatedFalse = emailTokenRepository.findAllByValidatedFalse();
        assertThat(allByValidatedFalse.size(), is(1));
        EmailToken generatedEmailToken = allByValidatedFalse.get(0);
        assertThat(generatedEmailToken.isValidated(), is(false));

        //-----------------------------------------------------------------
        // Send body with this email dto
        //-----------------------------------------------------------------
        producerTemplate.sendBody(allByValidatedFalse);

        MockEndpoint.assertIsSatisfied(camelContext);

        //-----------------------------------------------------------------
        // Assert endpoints consistency
        //-----------------------------------------------------------------

        //-----------------------------------------------------------------
        // First endpoint is proper
        //-----------------------------------------------------------------
        List<Exchange> directSendToProcessorEndpointExchangeList = directSendToProcessorEndpoint.getReceivedExchanges();
        assertThat(directSendToProcessorEndpointExchangeList.size(), is(1));
        SendTo sendTo = directSendToProcessorEndpointExchangeList.get(0).getIn().getBody(SendTo.class);
        assertThat(sendTo, notNullValue());
        assertThat(sendTo.getId(), is(generatedEmailToken.getUser().getId()));
        assertThat(sendTo.getFirstName(), is(generatedEmailToken.getUser().getFirstName()));
        assertThat(sendTo.getLastName(), is(generatedEmailToken.getUser().getLastName()));
        assertThat(sendTo.getEmail(), is(generatedEmailToken.getUser().getEmail()));
        assertThat(sendTo.getEmailToken(), is(generatedEmailToken.getToken()));
        assertThat(sendTo.getEmailType(), is(generatedEmailToken.getEmailType()));

        //-----------------------------------------------------------------
        // Second endpoint is proper
        //-----------------------------------------------------------------
        List<Exchange> directValidSentEmailTokenEndpointExchangeList = directValidSentEmailToken.getReceivedExchanges();
        assertThat(directValidSentEmailTokenEndpointExchangeList.size(), is(1));
        EmailToken emailToken = directValidSentEmailTokenEndpointExchangeList.get(0).getIn().getBody(EmailToken.class);
        assertThat(emailToken, notNullValue());
        assertThat(emailToken.isValidated(), is(true));
    }
}