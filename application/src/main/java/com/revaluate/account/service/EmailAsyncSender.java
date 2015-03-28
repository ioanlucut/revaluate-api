package com.revaluate.account.service;

import com.revaluate.account.persistence.EmailToken;
import com.revaluate.domain.email.EmailStatus;

import javax.validation.constraints.NotNull;
import java.util.concurrent.Future;

public interface EmailAsyncSender {

    public Future<EmailStatus> tryToSendEmail(@NotNull EmailToken emailToken);
}