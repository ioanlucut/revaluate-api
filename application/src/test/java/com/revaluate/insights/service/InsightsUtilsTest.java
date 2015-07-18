package com.revaluate.insights.service;

import org.joda.time.LocalDateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class InsightsUtilsTest {

    @Test
    public void testYearMonthsBetween() throws Exception {
        LocalDateTime after = ISODateTimeFormat.dateTimeNoMillis().parseLocalDateTime("2015-05-01T00:00:00Z");
        LocalDateTime before = ISODateTimeFormat.dateTimeNoMillis().parseLocalDateTime("2015-08-01T00:00:00Z");

        assertThat(InsightsUtils.yearMonthsBetween(after, before).size(), is(3));
    }
}