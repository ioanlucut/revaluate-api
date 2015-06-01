package com.revaluate.email;

import com.revaluate.domain.email.EmailStatus;
import com.revaluate.domain.email.SendFeedbackTo;
import com.revaluate.domain.email.SendTo;

public interface SendEmailService {

    EmailStatus sendNonAsyncEmailTo(SendTo sendTo) throws SendEmailException;

    EmailStatus sendNonAsyncFeedbackEmailTo(SendFeedbackTo sendFeedbackTo) throws SendEmailException;
}