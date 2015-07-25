package com.revaluate.insights.service;

import com.revaluate.domain.insights.statistics.InsightsMonthsPerYearsDTO;

import javax.validation.constraints.NotNull;

public interface MonthsPerYearStatisticsService {

    @NotNull
    InsightsMonthsPerYearsDTO getExistingDaysPerYearsWithExpensesDefined(int userId);
}