package com.revaluate.core.token;

import org.apache.commons.lang3.RandomStringUtils;

public class TokenGenerator {

    public static final int COUNT = 50;

    public static String getGeneratedToken() {

        return RandomStringUtils.randomAlphanumeric(COUNT);
    }
}
