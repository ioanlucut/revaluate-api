package com.revaluate;

import com.revaluate.core.bootstrap.ConfigProperties;

import java.net.URL;
import java.util.Optional;

public class DebugRevaluateApplicationStarter {

    public static void main(String[] args) throws Exception {
        Optional<URL> urlOptional = Optional.ofNullable(DebugRevaluateApplicationStarter.class.getClassLoader().getResource("config.yaml"));

        // PROD env
        System.setProperty(ConfigProperties.ENVIRONMENT, "dev");

        new RevaluateApplication().run("server", urlOptional.orElseThrow(Exception::new).getPath());
    }
}