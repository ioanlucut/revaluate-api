package com.revaluate.core;

import org.glassfish.jersey.moxy.json.MoxyJsonConfig;

import javax.ws.rs.ext.ContextResolver;

public class JsonConfiguration implements ContextResolver<MoxyJsonConfig> {

    @Override
    public MoxyJsonConfig getContext(final Class<?> type) {
        final MoxyJsonConfig config = new MoxyJsonConfig();
        config.setFormattedOutput(true);
        return config;
    }
}