package com.revaluate;

import java.net.URL;
import java.util.Optional;

public class DebugRevaluateApplicationStarter {

    public static void main(String[] args) throws Exception {
        Optional<URL> urlOptional = Optional.ofNullable(DebugRevaluateApplicationStarter.class.getClassLoader().getResource("config.yaml"));

        new RevaluateApplication().run("server", urlOptional.orElseThrow(Exception::new).getPath());
    }
}