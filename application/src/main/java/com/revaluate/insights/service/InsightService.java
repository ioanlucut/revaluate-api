package com.revaluate.insights.service;

import com.revaluate.domain.insights.InsightDTO;
import com.revaluate.domain.insights.InsightsMonthsPerYearsDTO;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public interface InsightService {

    @NotNull
    InsightDTO fetchInsightAfterBeforePeriod(int userId, @NotNull LocalDateTime after, @NotNull LocalDateTime before);

    @NotNull
    InsightsMonthsPerYearsDTO getExistingDaysPerYearsWithExpensesDefined(int userId);
}