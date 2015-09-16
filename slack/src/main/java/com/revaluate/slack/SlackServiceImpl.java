package com.revaluate.slack;

import com.revaluate.domain.slack.SlackAnswerDTO;
import com.revaluate.domain.slack.SlackAnswerDTOBuilder;
import com.revaluate.domain.slack.SlackDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Service
@Validated
public class SlackServiceImpl implements SlackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SlackServiceImpl.class);

    @Override
    public SlackAnswerDTO answer(@NotNull @Valid SlackDTO slackDTO) throws SlackException {
        LOGGER.info(String.format("SlackDTO: %s :", slackDTO));

        return new SlackAnswerDTOBuilder()
                .withText("<https://revaluate.io/|Click here> for details!")
                .build();
    }
}