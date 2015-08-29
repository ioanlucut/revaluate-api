package com.revaluate.insights.service;

import com.revaluate.domain.insights.daily.InsightsDailyDTO;
import com.revaluate.domain.insights.daily.InsightsDailyDTOBuilder;
import com.revaluate.domain.insights.daily.TotalPerDayDTO;
import com.revaluate.domain.insights.daily.TotalPerDayDTOBuilder;
import com.revaluate.expense.persistence.Expense;
import com.revaluate.expense.persistence.ExpenseRepository;
import org.joda.time.LocalDateTime;
import org.joda.time.MonthDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Validated
public class DailyInsightsServiceImpl implements DailyInsightsService {

    public static final int DIGITS_SCALE = 2;
    public static final double NO_AMOUNT_VALUE = BigDecimal.ZERO.doubleValue();

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public InsightsDailyDTO fetchDailyInsightsAfterBeforePeriod(int userId, LocalDateTime after, LocalDateTime before) {
        List<Expense> allExpenses = expenseRepository.findAllByUserIdAndSpentDateAfterAndSpentDateBefore(userId, after, before);

        return this.computeDailyInsightsAfterBeforePeriod(userId, allExpenses, after, before);
    }

    @Override
    public InsightsDailyDTO computeDailyInsightsAfterBeforePeriod(int userId, List<Expense> allExpenses, LocalDateTime after, LocalDateTime before) {
        //-----------------------------------------------------------------
        // No results, return empty insight overview
        //-----------------------------------------------------------------
        if (allExpenses.isEmpty()) {

            return new InsightsDailyDTOBuilder()
                    .withTotalPerDayDTOs(Collections.emptyList())
                    .withFrom(after)
                    .withTo(before)
                    .build();
        }

        //-----------------------------------------------------------------
        // First of all, we get all expenses grouped
        //-----------------------------------------------------------------
        Map<MonthDay, Optional<BigDecimal>> groupedPerMonthDay = allExpenses
                .stream()
                .collect(Collectors
                        .groupingBy(expense -> new MonthDay(expense.getSpentDate().getMonthOfYear(), expense.getSpentDate().getDayOfMonth()),
                                Collectors.mapping(Expense::getValue, Collectors.reducing(BigDecimal::add))));

        //-----------------------------------------------------------------
        // Then, we build our list
        //-----------------------------------------------------------------
        List<TotalPerDayDTO> totalPerDayDTOs = groupedPerMonthDay
                .entrySet()
                .stream()
                .filter(stringOptionalEntry -> stringOptionalEntry.getValue().isPresent())
                .map(entry -> {
                    BigDecimal totalExpenseAsBigDecimal = entry.getValue().get();
                    BigDecimal totalExpenseAsBigDecimalScaled = totalExpenseAsBigDecimal.setScale(DIGITS_SCALE, BigDecimal.ROUND_DOWN);

                    return new TotalPerDayDTOBuilder()
                            .withTotalAmount(totalExpenseAsBigDecimalScaled.doubleValue())
                            .withMonthDay(entry.getKey())
                            .build();
                })
                .collect(Collectors.toList());

        List<MonthDay> allMonthDays = InsightsUtils
                .monthDaysBetween(after, before);

        Stream<TotalPerDayDTO> emptyMonths = allMonthDays
                .stream()
                .filter(monthDayCandidate -> totalPerDayDTOs
                        .stream()
                        .noneMatch(TotalPerDayDTO -> TotalPerDayDTO.getMonthDay().equals(monthDayCandidate)))
                .map(monthDay -> new TotalPerDayDTOBuilder()
                        .withTotalAmount(NO_AMOUNT_VALUE)
                        .withMonthDay(monthDay)
                        .build());

        //-----------------------------------------------------------------
        // Total per month comparator
        //-----------------------------------------------------------------
        Comparator<TotalPerDayDTO> TotalPerDayDTOComparator = (o1, o2) -> o1.getMonthDay().compareTo(o2.getMonthDay());

        List<TotalPerDayDTO> allCombined = Stream
                .concat(totalPerDayDTOs.stream(), emptyMonths)
                .sorted(TotalPerDayDTOComparator)
                .collect(Collectors.toList());

        return new InsightsDailyDTOBuilder()
                .withTotalPerDayDTOs(allCombined)
                .withNumberOfTransactions(allExpenses.size())
                .withTotalAmountSpent(InsightsUtils.totalOf(allExpenses).doubleValue())
                .withFrom(after)
                .withTo(before)
                .build();
    }

}
