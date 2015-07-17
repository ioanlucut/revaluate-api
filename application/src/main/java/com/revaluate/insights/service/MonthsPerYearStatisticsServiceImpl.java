package com.revaluate.insights.service;

import com.revaluate.domain.insights.statistics.InsightsMonthsPerYearsDTO;
import com.revaluate.domain.insights.statistics.InsightsMonthsPerYearsDTOBuilder;
import com.revaluate.expense.persistence.ExpenseRepository;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Validated
public class MonthsPerYearStatisticsServiceImpl implements MonthsPerYearStatisticsService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public InsightsMonthsPerYearsDTO getExistingDaysPerYearsWithExpensesDefined(int userId) {
        List<LocalDateTime> existingSpentDates = expenseRepository.selectExistingSpentDates(userId);

        return new InsightsMonthsPerYearsDTOBuilder()
                .withInsightsMonthsPerYears(
                        existingSpentDates
                                .stream()
                                .collect(Collectors.groupingBy(LocalDateTime::getYear))
                                .entrySet()
                                .stream()
                                .collect(Collectors
                                        .toMap(Map.Entry::getKey,
                                                expensesPerYearEntry -> expensesPerYearEntry
                                                        .getValue()
                                                        .stream()
                                                        .map(LocalDateTime::getMonthOfYear)
                                                        .collect(Collectors.toSet())
                                        )))
                .build();

    }
}
