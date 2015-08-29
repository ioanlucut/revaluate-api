package com.revaluate.statistics;

import com.revaluate.domain.insights.statistics.MonthsPerYearsDTO;

import javax.validation.constraints.NotNull;

public interface MonthsPerYearStatisticsService {

    @NotNull
    MonthsPerYearsDTO getExistingDaysPerYearsWithExpensesDefined(int userId);

    MonthsPerYearsDTO getExistingDaysPerYearsWithGoalsDefined(int userId);
}