package com.revaluate.intercom;

import com.revaluate.AbstractIntegrationTests;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class IntercomTrackerTestIT extends AbstractIntegrationTests {

    @Autowired
    private IntercomTracker intercomTracker;

    @Test
    @Ignore(value = "As this test goes externally, we will use it only to debug it manually.")
    public void add_expenseHappyFlow_ok() throws Exception {
        intercomTracker.trackEventAsync(IntercomTracker.EventType.SLACK_COMMAND, "2204");
    }
}