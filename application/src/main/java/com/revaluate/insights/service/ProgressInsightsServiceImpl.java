package com.revaluate.insights.service;

import com.revaluate.domain.insights.monthly.InsightsMonthlyDTO;
import com.revaluate.domain.insights.monthly.InsightsMonthlyDTOBuilder;
import com.revaluate.domain.insights.overview.TotalPerMonthDTO;
import com.revaluate.domain.insights.progress.ProgressInsightsDTO;
import com.revaluate.domain.insights.progress.ProgressInsightsDTOBuilder;
import com.revaluate.expense.persistence.Expense;
import com.revaluate.expense.persistence.ExpenseRepository;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.YearMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Validated
public class ProgressInsightsServiceImpl implements ProgressInsightsService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private MonthlyInsightsService monthlyInsightsService;

    @Override
    public ProgressInsightsDTO fetchProgressInsightsBetween(int userId, LocalDateTime after, LocalDateTime before) {
        List<Expense> allExpenses = expenseRepository.findAllByUserIdAndSpentDateAfterAndSpentDateBefore(userId, after, before);

        //-----------------------------------------------------------------
        // No results, return empty insight progress
        //-----------------------------------------------------------------
        if (allExpenses.isEmpty()) {

            return new ProgressInsightsDTOBuilder()
                    .withInsightsMonthlyDTO(Collections.emptyList())
                    .withFrom(after)
                    .withTo(before)
                    .build();
        }

        //-----------------------------------------------------------------
        // First of all, we get all expenses groupedYearPerMonth by months
        //-----------------------------------------------------------------
        Map<YearMonth, List<Expense>> groupedYearPerMonth = allExpenses
                .stream()
                .collect(Collectors
                        .groupingBy(expense -> YearMonth.fromDateFields(expense.getSpentDate().toDate())));


        //-----------------------------------------------------------------
        // Then, we build our list
        //-----------------------------------------------------------------
        List<InsightsMonthlyDTO> insightMonthlyDTOs = groupedYearPerMonth
                .entrySet()
                .stream()
                .map(entry -> {
                    YearMonth yearMonth = entry.getKey();
                    DateTime from = new DateTime().withYear(yearMonth.getYear()).withMonthOfYear(yearMonth.getMonthOfYear()).withTimeAtStartOfDay();
                    DateTime to = from.plusDays(1).withTimeAtStartOfDay();

                    InsightsMonthlyDTO insightsMonthlyDTO = monthlyInsightsService
                            .computeMonthlyInsightsAfterBeforePeriod(entry.getValue(), LocalDateTime.fromDateFields(from.toDate()), LocalDateTime.fromDateFields(to.toDate()));
                    insightsMonthlyDTO.setYearMonth(yearMonth);

                    return insightsMonthlyDTO;
                })
                .collect(Collectors.toList());

        List<YearMonth> allYearMonths = InsightsUtils
                .yearMonthsBetween(after, before);

        Stream<InsightsMonthlyDTO> emptyMonths = allYearMonths
                .stream()
                .filter(emptyYearMonth -> insightMonthlyDTOs
                        .stream()
                        .noneMatch(totalPerMonthDTO -> totalPerMonthDTO.getYearMonth().equals(emptyYearMonth)))
                .map(emptyMonthEntry -> new InsightsMonthlyDTOBuilder()
                        .withFrom(after)
                        .withTo(before)
                        .withTotalPerCategoryInsightsDTOs(Collections.emptyList())
                        .withYearMonth(emptyMonthEntry)
                        .build());

        //-----------------------------------------------------------------
        // Total per month comparator
        //-----------------------------------------------------------------
        Comparator<InsightsMonthlyDTO> insightsMonthlyDTOComparator = (o1, o2) -> o1.getYearMonth().compareTo(o2.getYearMonth());

        List<InsightsMonthlyDTO> allCombined = Stream
                .concat(insightMonthlyDTOs.stream(), emptyMonths)
                .sorted(insightsMonthlyDTOComparator)
                .collect(Collectors.toList());

        return new ProgressInsightsDTOBuilder()
                .withInsightsMonthlyDTO(allCombined)
                .withNumberOfTransactions(allExpenses.size())
                .withTotalAmountSpent(InsightsUtils.totalOf(allExpenses).doubleValue())
                .withFrom(after)
                .withTo(before)
                .build();
    }
}