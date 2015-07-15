package com.revaluate.insights.service;

import com.revaluate.domain.insights.InsightsMonthsPerYearsDTO;

import javax.validation.constraints.NotNull;

public interface InsightMonthsPerYearService {

    @NotNull
    InsightsMonthsPerYearsDTO getExistingDaysPerYearsWithExpensesDefined(int userId);
}