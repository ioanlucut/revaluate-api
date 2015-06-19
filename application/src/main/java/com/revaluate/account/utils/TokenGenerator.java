package com.revaluate.account.utils;

import com.revaluate.account.persistence.User;
import com.revaluate.domain.account.FeedbackDTO;
import com.revaluate.domain.email.EmailStatus;
import com.revaluate.domain.email.EmailType;
import com.revaluate.email.persistence.EmailFeedback;
import com.revaluate.email.persistence.EmailToken;
import org.apache.commons.lang3.RandomStringUtils;

public class TokenGenerator {

    public static final int COUNT = 50;

    public static String getGeneratedToken() {

        return RandomStringUtils.randomAlphanumeric(COUNT);
    }

    public static EmailToken buildEmail(User user, EmailType emailType) {
        EmailToken emailToken = new EmailToken();
        emailToken.setToken(TokenGenerator.getGeneratedToken());
        emailToken.setEmailType(emailType);
        emailToken.setUser(user);
        emailToken.setEmailStatus(EmailStatus.QUEUED);

        return emailToken;
    }

    public static EmailFeedback buildFeedbackEmail(User user, EmailType emailType, FeedbackDTO feedbackDTO) {
        EmailFeedback emailFeedback = new EmailFeedback();
        emailFeedback.setEmailType(emailType);
        emailFeedback.setUser(user);
        emailFeedback.setSubject(feedbackDTO.getSubject());
        emailFeedback.setMessage(feedbackDTO.getMessage());
        emailFeedback.setEmailStatus(EmailStatus.QUEUED);

        return emailFeedback;
    }
}
