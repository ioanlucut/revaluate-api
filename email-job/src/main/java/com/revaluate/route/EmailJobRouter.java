package com.revaluate.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailJobRouter extends RouteBuilder {

    public static final String DIRECT_FETCH_ALL = "direct:fetchAll";

    @Autowired
    private EmailProcessor emailProcessor;

    @Override
    public void configure() throws Exception {

        //-----------------------------------------------------------------
        // Timer router - initial bootstrap
        //-----------------------------------------------------------------
        from("timer://runOnce?repeatCount=1&delay=5000")
                .bean(emailProcessor)
                .to(DIRECT_FETCH_ALL);

        //-----------------------------------------------------------------
        // Fetch all directly
        //-----------------------------------------------------------------
        from(DIRECT_FETCH_ALL)
                .routeId(DIRECT_FETCH_ALL)
                .process(System.out::println);
    }

}
