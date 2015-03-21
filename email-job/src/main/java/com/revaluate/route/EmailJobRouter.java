package com.revaluate.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailJobRouter extends RouteBuilder {

    public static final String DIRECT_ROUTE_FETCH_ALL_EMAIL_TOKENS = "direct:fetchAllEmailTokens";

    @Autowired
    private EmailTokensProcessor emailTokensProcessor;

    @Override
    public void configure() throws Exception {

        //-----------------------------------------------------------------
        // Timer router - initial bootstrap
        //-----------------------------------------------------------------
        from("timer://runOnce?repeatCount=1&delay=5000")
                .bean(emailTokensProcessor)
                .to(DIRECT_ROUTE_FETCH_ALL_EMAIL_TOKENS);

        //-----------------------------------------------------------------
        // Fetch all directly
        //-----------------------------------------------------------------
        from(DIRECT_ROUTE_FETCH_ALL_EMAIL_TOKENS)
                .process(System.out::println);
    }

}
