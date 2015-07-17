package com.revaluate.insights.service;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class test {

    public static final String INSIGHT_OVERVIEW_MONTH_FORMAT_DATE_PATTERN = "yyyy-MM";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern(INSIGHT_OVERVIEW_MONTH_FORMAT_DATE_PATTERN);

    public static void main(String[] args) {
        LocalDateTime after = LocalDateTime.now().minusMonths(12);
        LocalDateTime before = LocalDateTime.now();

        InsightsUtils
                .yearMonthsBetween(after, before)
                .stream()
                .forEach(yearMonth -> {
                    System.out.println(DATE_TIME_FORMATTER.print(yearMonth));
                });
    }
}
