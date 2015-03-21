package com.revaluate.route;

import org.apache.camel.spring.Main;

public class EmailJobBootstraper {

    public static final String APPLICATION_CONTEXT_URI = "applicationContext__emailJob.xml";

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.setApplicationContextUri(APPLICATION_CONTEXT_URI);
        main.enableHangupSupport();
        main.run();
    }
}