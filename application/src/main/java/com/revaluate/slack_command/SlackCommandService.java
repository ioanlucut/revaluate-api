package com.revaluate.slack_command;

import com.revaluate.domain.slack.SlackDTO;
import com.revaluate.slack.SlackException;
import net.bull.javamelody.MonitoredWithSpring;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@MonitoredWithSpring
public interface SlackCommandService {

    String answer(@NotNull @Valid SlackDTO slackDTO, int userId) throws SlackException;
}