package com.revaluate.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConfigProperties {

    public static final String ENVIRONMENT = "ENVIRONMENT";

    private boolean isProduction;

    public boolean isProduction() {
        return isProduction;
    }

    @Value("${isProduction}")
    public void setProduction(boolean isProduction) {
        this.isProduction = isProduction;
    }
}
