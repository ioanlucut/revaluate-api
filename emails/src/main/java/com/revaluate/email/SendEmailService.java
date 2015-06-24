package com.revaluate.email;

import com.revaluate.domain.contact.ContactDTO;
import com.revaluate.domain.email.MandrillEmailStatus;
import com.revaluate.domain.email.SendFeedbackTo;
import com.revaluate.domain.email.SendTo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface SendEmailService {

    MandrillEmailStatus sendNonAsyncEmailTo(@NotNull @Valid SendTo sendTo) throws SendEmailException;

    MandrillEmailStatus sendNonAsyncFeedbackEmailTo(@NotNull @Valid SendFeedbackTo sendFeedbackTo) throws SendEmailException;

    MandrillEmailStatus sendNonAsyncContactEmail(@NotNull @Valid ContactDTO contactDTO) throws SendEmailException;
}