package com.revaluate.intercom;

import com.google.common.collect.Maps;
import com.revaluate.core.bootstrap.ConfigProperties;
import io.intercom.api.Event;
import io.intercom.api.Intercom;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Map;


@Service
@Validated
public class IntercomTracker {

    //-----------------------------------------------------------------
    // QUERY params
    //-----------------------------------------------------------------
    public static final String USER_ID = "user_id";
    private static final Logger LOGGER = LoggerFactory.getLogger(IntercomTracker.class);
    @Autowired
    private ConfigProperties configProperties;

    @Async
    public void trackEventAsync(@NotNull EventType eventType, @NotEmpty String userId) {
        Intercom.setAppID(configProperties.getIntercomAppId());
        Intercom.setApiKey(configProperties.getIntercomAppKey());

        Map<String, String> params = Maps.newHashMap();
        params.put(USER_ID, userId);
        try {
            Event event = new Event()
                    .setEventName(eventType.name())
                    .setUserID(userId);
            Event.create(event);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public enum EventType {
        SLACK_COMMAND
    }

}