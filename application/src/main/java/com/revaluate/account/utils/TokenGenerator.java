package com.revaluate.account.utils;

import com.revaluate.account.persistence.Email;
import com.revaluate.domain.email.EmailType;
import com.revaluate.account.persistence.User;
import org.apache.commons.lang3.RandomStringUtils;

public class TokenGenerator {

    public static final int COUNT = 50;

    public static String getGeneratedToken() {

        return RandomStringUtils.randomAlphanumeric(COUNT);
    }

    public static Email buildEmail(User user, EmailType emailType) {
        Email resetEmail = new Email();
        resetEmail.setToken(TokenGenerator.getGeneratedToken());
        resetEmail.setEmailType(emailType);
        resetEmail.setUser(user);

        return resetEmail;
    }
}
