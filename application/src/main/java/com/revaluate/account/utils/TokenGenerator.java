package com.revaluate.account.utils;

import com.revaluate.account.persistence.EmailToken;
import com.revaluate.account.persistence.EmailType;
import com.revaluate.account.persistence.User;
import org.apache.commons.lang3.RandomStringUtils;

public class TokenGenerator {

    public static final int COUNT = 50;

    public static String getGeneratedToken() {

        return RandomStringUtils.randomAlphanumeric(COUNT);
    }

    public static EmailToken generateTokenFor(User user, EmailType emailType) {
        EmailToken resetEmailToken = new EmailToken();
        resetEmailToken.setToken(TokenGenerator.getGeneratedToken());
        resetEmailToken.setEmailType(emailType);
        resetEmailToken.setUser(user);

        return resetEmailToken;
    }
}
