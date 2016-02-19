package com.revaluate.insights.service;

import org.joda.time.LocalDateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InsightsUtilsTest {

    @Test
    public void testYearMonthsBetween() throws Exception {
        LocalDateTime after = ISODateTimeFormat.dateTimeNoMillis().parseLocalDateTime("2015-05-01T00:00:00Z");
        LocalDateTime before = ISODateTimeFormat.dateTimeNoMillis().parseLocalDateTime("2015-08-01T00:00:00Z");

        assertThat(InsightsUtils.yearMonthsBetween(after, before).size()).isEqualTo((3));
    }

    @Test
    public void testMonthDaysBetween() throws Exception {
        LocalDateTime after = ISODateTimeFormat.dateTimeNoMillis().parseLocalDateTime("2015-07-01T00:00:00Z");
        LocalDateTime before = ISODateTimeFormat.dateTimeNoMillis().parseLocalDateTime("2015-08-01T00:00:00Z");

        assertThat(InsightsUtils.monthDaysBetween(after, before).size()).isEqualTo((31));
    }
}