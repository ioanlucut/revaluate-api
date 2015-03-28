package com.revaluate.account.service;

import com.revaluate.account.persistence.EmailToken;

import javax.validation.constraints.NotNull;

public interface EmailAsyncSender {

    public void tryToSendEmail(@NotNull EmailToken savedCreateEmailToken);
}