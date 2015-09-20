package com.revaluate.slack_command;

import com.revaluate.domain.slack.SlackDTO;
import com.revaluate.slack.SlackException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface SlackCommandService {

    String answer(@NotNull @Valid SlackDTO slackDTO, int userId) throws SlackException;
}