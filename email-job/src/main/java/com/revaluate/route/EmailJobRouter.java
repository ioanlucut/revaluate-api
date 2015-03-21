package com.revaluate.route;

import com.revaluate.domain.SendTo;
import com.revaluate.processor.EmailTokenValidateProcessor;
import com.revaluate.processor.EmailTokensRetrieverProcessor;
import com.revaluate.processor.SendToProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailJobRouter extends RouteBuilder {

    public static final String TIME_ROUTE_RUN_ONCE = "timer://runOnce?repeatCount=1&delay=100";
    public static final String TIME_ROUTE_MANY = "timer://runOnce?fixedRate=true&period=60000";

    public static final String DIRECT_ROUTE_FETCH_ALL_EMAIL_TOKENS = "direct:fetchAllEmailTokens";

    @Autowired
    private EmailTokensRetrieverProcessor emailTokensRetrieverProcessor;

    @Autowired
    private SendToProcessor sendToProcessor;

    @Autowired
    private EmailTokenValidateProcessor emailTokenValidateProcessor;

    @Override
    public void configure() throws Exception {

        //-----------------------------------------------------------------
        // Timer router - initial bootstrap
        //-----------------------------------------------------------------
        from(TIME_ROUTE_MANY)
                .bean(emailTokensRetrieverProcessor);

        //-----------------------------------------------------------------
        // Fetch all directly, send emails and update them in the database.
        //-----------------------------------------------------------------
        from(DIRECT_ROUTE_FETCH_ALL_EMAIL_TOKENS)
                .split()
                .body()
                .streaming()
                .convertBodyTo(SendTo.class)
                .bean(sendToProcessor)
                .bean(emailTokenValidateProcessor)
                .end();
    }

}
