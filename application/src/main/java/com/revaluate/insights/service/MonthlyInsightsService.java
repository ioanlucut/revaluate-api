package com.revaluate.insights.service;

import com.revaluate.domain.insights.monthly.InsightsMonthlyDTO;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public interface MonthlyInsightsService {

    @NotNull
    InsightsMonthlyDTO fetchMonthlyInsightsAfterBeforePeriod(int userId, @NotNull LocalDateTime after, @NotNull LocalDateTime before);
}