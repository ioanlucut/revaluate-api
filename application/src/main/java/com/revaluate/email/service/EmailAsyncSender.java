package com.revaluate.email.service;

import com.revaluate.domain.email.EmailStatus;
import com.revaluate.email.persistence.Email;
import org.springframework.scheduling.annotation.Async;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.concurrent.Future;

public interface EmailAsyncSender<T extends Email> {

    @Async
    Future<EmailStatus> tryToSendEmail(@NotNull @Valid T email);
}