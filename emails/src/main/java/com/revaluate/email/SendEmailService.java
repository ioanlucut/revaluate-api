package com.revaluate.email;

import com.revaluate.domain.email.MandrillEmailStatus;
import com.revaluate.domain.email.SendFeedbackTo;
import com.revaluate.domain.email.SendTo;

public interface SendEmailService {

    MandrillEmailStatus sendNonAsyncEmailTo(SendTo sendTo) throws SendEmailException;

    MandrillEmailStatus sendNonAsyncFeedbackEmailTo(SendFeedbackTo sendFeedbackTo) throws SendEmailException;
}