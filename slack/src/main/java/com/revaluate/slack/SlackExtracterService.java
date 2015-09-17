package com.revaluate.slack;

import com.revaluate.domain.slack.SlackAnswerDTO;
import com.revaluate.domain.slack.SlackDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface SlackExtracterService {

    SlackAnswerDTO answer(@NotNull @Valid SlackDTO slackDTO) throws SlackException;
}