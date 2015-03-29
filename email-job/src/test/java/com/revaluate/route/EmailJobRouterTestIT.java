package com.revaluate.route;

import com.revaluate.AbstractBatchJobIntegrationTests;
import com.revaluate.account.persistence.Email;
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

    @Produce(uri = "direct:fetchAllEmails")
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
        List<Email> allByValidatedFalse = emailRepository.findAllByTokenValidatedFalse();
        assertThat(allByValidatedFalse.size(), is(1));
        Email generatedEmail = allByValidatedFalse.get(0);
        assertThat(generatedEmail.isTokenValidated(), is(false));

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
        assertThat(sendTo.getId(), is(generatedEmail.getUser().getId()));
        assertThat(sendTo.getFirstName(), is(generatedEmail.getUser().getFirstName()));
        assertThat(sendTo.getLastName(), is(generatedEmail.getUser().getLastName()));
        assertThat(sendTo.getEmail(), is(generatedEmail.getUser().getEmail()));
        assertThat(sendTo.getEmailToken(), is(generatedEmail.getToken()));
        assertThat(sendTo.getEmailType(), is(generatedEmail.getEmailType()));

        //-----------------------------------------------------------------
        // Second endpoint is proper
        //-----------------------------------------------------------------
        List<Exchange> directValidSentEmailTokenEndpointExchangeList = directValidSentEmailToken.getReceivedExchanges();
        assertThat(directValidSentEmailTokenEndpointExchangeList.size(), is(1));
        Email email = directValidSentEmailTokenEndpointExchangeList.get(0).getIn().getBody(Email.class);
        assertThat(email, notNullValue());
        assertThat(email.isTokenValidated(), is(true));
    }
}