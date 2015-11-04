package com.revaluate.statistics;

import com.revaluate.domain.insights.statistics.MonthsPerYearsDTO;
import net.bull.javamelody.MonitoredWithSpring;

import javax.validation.constraints.NotNull;

@MonitoredWithSpring
public interface MonthsPerYearStatisticsService {

    @NotNull
    MonthsPerYearsDTO getExistingDaysPerYearsWithExpensesDefined(int userId);

    MonthsPerYearsDTO getExistingDaysPerYearsWithGoalsDefined(int userId);
}