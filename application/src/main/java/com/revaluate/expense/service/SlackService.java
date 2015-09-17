package com.revaluate.expense.service;

import com.revaluate.domain.slack.SlackDTO;
import com.revaluate.slack.SlackException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface SlackService {

    String answer(@NotNull @Valid SlackDTO slackDTO, int userId) throws SlackException;
}