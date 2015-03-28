package com.revaluate.email;

import com.revaluate.domain.email.EmailStatus;
import com.revaluate.domain.email.SendTo;

public interface SendEmailService {

    EmailStatus sendAsyncEmailTo(SendTo sendTo) throws SendEmailException;

    EmailStatus sendNonAsyncEmailTo(SendTo sendTo) throws SendEmailException;
}