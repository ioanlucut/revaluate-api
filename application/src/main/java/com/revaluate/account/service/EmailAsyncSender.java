package com.revaluate.account.service;

import com.revaluate.account.persistence.Email;
import com.revaluate.domain.email.EmailStatus;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.concurrent.Future;

public interface EmailAsyncSender {

    Future<EmailStatus> tryToSendEmail(@NotNull @Valid Email email);
}