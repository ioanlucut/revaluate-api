package com.revaluate.route;

import com.revaluate.domain.email.SendTo;
import com.revaluate.email.SendEmailException;
import com.revaluate.processor.EmailTokenValidateProcessor;
import com.revaluate.processor.EmailTokensRetrieverProcessor;
import com.revaluate.processor.SendToProcessor;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailJobRouter extends RouteBuilder {

    public static final String TIME_ROUTE_RUN_ONCE = "timer://runOnce?repeatCount=1&delay=100";
    public static final String TIME_ROUTE_MANY = "timer://runOnce?fixedRate=true&period=30000";
    public static final String DIRECT_ROUTE_FETCH_ALL_EMAIL_TOKENS = "direct:fetchAllEmailTokens";

    public static final String DIRECT_SEND_TO_PROCESSOR = "direct:sendToProcessor";
    public static final String DIRECT_VALID_SENT_EMAIL_TOKEN = "direct:validSentEmailToken";

    @Autowired
    private EmailTokensRetrieverProcessor emailTokensRetrieverProcessor;

    @Autowired
    private SendToProcessor sendToProcessor;

    @Autowired
    private EmailTokenValidateProcessor emailTokenValidateProcessor;

    @Override
    public void configure() throws Exception {

        //-----------------------------------------------------------------
        // Error handling
        //-----------------------------------------------------------------
        onException(SendEmailException.class)
                .maximumRedeliveries(1)
                .handled(true)
                .log(LoggingLevel.ERROR, "Error: ${exception.message}")
                .end();

        //-----------------------------------------------------------------
        // Timer router - initial bootstrap
        //-----------------------------------------------------------------
        from(TIME_ROUTE_MANY)
                .bean(emailTokensRetrieverProcessor);

        //-----------------------------------------------------------------
        // Fetch all directly, send emails and update them in the database.
        //-----------------------------------------------------------------
        from(DIRECT_ROUTE_FETCH_ALL_EMAIL_TOKENS)
                .routeId(DIRECT_ROUTE_FETCH_ALL_EMAIL_TOKENS)
                .split()
                .body()
                .streaming()
                .convertBodyTo(SendTo.class)
                .to(DIRECT_SEND_TO_PROCESSOR);

        from(DIRECT_SEND_TO_PROCESSOR)
                .routeId(DIRECT_SEND_TO_PROCESSOR)
                .bean(sendToProcessor)
                .to(DIRECT_VALID_SENT_EMAIL_TOKEN);

        from(DIRECT_VALID_SENT_EMAIL_TOKEN)
                .routeId(DIRECT_VALID_SENT_EMAIL_TOKEN)
                .bean(emailTokenValidateProcessor)
                .end();
    }

}
