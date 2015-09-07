package com.revaluate.statistics;

import com.revaluate.domain.insights.statistics.MonthsPerYearsDTO;
import com.revaluate.domain.insights.statistics.MonthsPerYearsDTOBuilder;
import com.revaluate.expense.persistence.ExpenseRepository;
import com.revaluate.goals.persistence.GoalRepository;
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

    @Autowired
    private GoalRepository goalRepository;

    @Override
    public MonthsPerYearsDTO getExistingDaysPerYearsWithExpensesDefined(int userId) {
        List<LocalDateTime> existingSpentDates = expenseRepository.selectExistingSpentDates(userId);

        return new MonthsPerYearsDTOBuilder()
                .withMonthsPerYears(
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

    @Override
    public MonthsPerYearsDTO getExistingDaysPerYearsWithGoalsDefined(int userId) {
        List<LocalDateTime> existingEndDates = goalRepository.selectExistingEndDates(userId);

        return new MonthsPerYearsDTOBuilder()
                .withMonthsPerYears(
                        existingEndDates
                                .stream()
                                .collect(Collectors.groupingBy(LocalDateTime::getYear))
                                .entrySet()
                                .stream()
                                .collect(Collectors
                                        .toMap(Map.Entry::getKey,
                                                goalsPerYearEntry -> goalsPerYearEntry
                                                        .getValue()
                                                        .stream()
                                                        .map(LocalDateTime::getMonthOfYear)
                                                        .collect(Collectors.toSet())
                                        )))
                .build();

    }
}
