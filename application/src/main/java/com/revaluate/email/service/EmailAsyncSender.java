package com.revaluate.email.service;

import com.revaluate.domain.email.MandrillEmailStatus;
import com.revaluate.email.persistence.Email;
import net.bull.javamelody.MonitoredWithSpring;
import org.springframework.scheduling.annotation.Async;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.concurrent.Future;

@MonitoredWithSpring
public interface EmailAsyncSender<T extends Email> {

    @Async
    Future<MandrillEmailStatus> tryToSendEmail(@NotNull @Valid T email);
}