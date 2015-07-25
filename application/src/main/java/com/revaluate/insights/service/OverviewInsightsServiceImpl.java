package com.revaluate.insights.service;

import com.revaluate.domain.insights.overview.InsightsOverviewDTO;
import com.revaluate.domain.insights.overview.InsightsOverviewDTOBuilder;
import com.revaluate.domain.insights.overview.TotalPerMonthDTO;
import com.revaluate.domain.insights.overview.TotalPerMonthDTOBuilder;
import com.revaluate.expense.persistence.Expense;
import com.revaluate.expense.persistence.ExpenseRepository;
import org.joda.time.LocalDateTime;
import org.joda.time.YearMonth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Validated
public class OverviewInsightsServiceImpl implements OverviewInsightsService {

    public static final int DIGITS_SCALE = 2;
    public static final String PATTERN = "0.00";
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(PATTERN);

    public static final double NO_AMOUNT_VALUE = BigDecimal.ZERO.doubleValue();
    public static final String NO_AMOUNT_VALUE_FORMATTED = DECIMAL_FORMAT.format(BigDecimal.ZERO);

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public InsightsOverviewDTO getOverviewInsightsBetween(int userId, LocalDateTime after, LocalDateTime before) {
        List<Expense> allExpenses = expenseRepository.findAllByUserIdAndSpentDateAfterAndSpentDateBefore(userId, after, before);

        //-----------------------------------------------------------------
        // No results, return empty insight overview
        //-----------------------------------------------------------------
        if (allExpenses.isEmpty()) {

            return new InsightsOverviewDTOBuilder()
                    .withInsightsOverview(Collections.emptyList())
                    .withFrom(after)
                    .withTo(before)
                    .build();
        }

        //-----------------------------------------------------------------
        // First of all, we get all expenses grouped
        //-----------------------------------------------------------------
        Map<YearMonth, Optional<BigDecimal>> insightsOverview = allExpenses
                .stream()
                .collect(Collectors
                        .groupingBy(expense -> new YearMonth(expense.getSpentDate().getYear(), expense.getSpentDate().getMonthOfYear()),
                                Collectors.mapping(Expense::getValue, Collectors.reducing(BigDecimal::add))));

        //-----------------------------------------------------------------
        // Then, we build our list
        //-----------------------------------------------------------------
        List<TotalPerMonthDTO> totalPerMonthDTOs = insightsOverview
                .entrySet()
                .stream()
                .filter(stringOptionalEntry -> stringOptionalEntry.getValue().isPresent())
                .map(entry -> {
                    BigDecimal totalExpenseAsBigDecimal = entry.getValue().get();
                    BigDecimal totalExpenseAsBigDecimalScaled = totalExpenseAsBigDecimal.setScale(DIGITS_SCALE, BigDecimal.ROUND_DOWN);

                    return new TotalPerMonthDTOBuilder()
                            .withTotalAmount(totalExpenseAsBigDecimalScaled.doubleValue())
                            .withTotalAmountFormatted(DECIMAL_FORMAT.format(totalExpenseAsBigDecimalScaled))
                            .withYearMonth(entry.getKey())
                            .build();
                })
                .collect(Collectors.toList());

        List<YearMonth> allYearMonths = InsightsUtils
                .yearMonthsBetween(after, before);

        Stream<TotalPerMonthDTO> emptyMonths = allYearMonths
                .stream()
                .filter(yearMonthCandidate -> totalPerMonthDTOs
                        .stream()
                        .noneMatch(totalPerMonthDTO -> totalPerMonthDTO.getYearMonth().equals(yearMonthCandidate)))
                .map(yearMonth -> new TotalPerMonthDTOBuilder()
                        .withTotalAmount(NO_AMOUNT_VALUE)
                        .withTotalAmountFormatted(NO_AMOUNT_VALUE_FORMATTED)
                        .withYearMonth(yearMonth)
                        .build());

        //-----------------------------------------------------------------
        // Total per month comparator
        //-----------------------------------------------------------------
        Comparator<TotalPerMonthDTO> totalPerMonthDTOComparator = (o1, o2) -> o1.getYearMonth().compareTo(o2.getYearMonth());

        List<TotalPerMonthDTO> allCombined = Stream
                .concat(totalPerMonthDTOs.stream(), emptyMonths)
                .sorted(totalPerMonthDTOComparator)
                .collect(Collectors.toList());

        return new InsightsOverviewDTOBuilder()
                .withInsightsOverview(allCombined)
                .withNumberOfTransactions(allExpenses.size())
                .withTotalAmountSpent(InsightsUtils.totalOf(allExpenses).doubleValue())
                .withFrom(after)
                .withTo(before)
                .build();
    }
}