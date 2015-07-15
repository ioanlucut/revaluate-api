package com.revaluate.insights.service;

import com.revaluate.domain.insights.overview.InsightsOverviewDTO;
import com.revaluate.domain.insights.overview.InsightsOverviewDTOBuilder;
import com.revaluate.domain.insights.overview.TotalPerMonthDTO;
import com.revaluate.domain.insights.overview.TotalPerMonthDTOBuilder;
import com.revaluate.expense.persistence.Expense;
import com.revaluate.expense.persistence.ExpenseRepository;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Validated
public class InsightOverviewServiceImpl implements InsightOverviewService {

    public static final int DIGITS_SCALE = 2;
    public static final String PATTERN = "0.00";
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat(PATTERN);

    public static final String INSIGHT_OVERVIEW_MONTH_FORMAT_DATE_PATTERN = "yyyy-MM";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern(INSIGHT_OVERVIEW_MONTH_FORMAT_DATE_PATTERN);

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public InsightsOverviewDTO getInsightsOverviewBetween(int userId, LocalDateTime after, LocalDateTime before) {
        List<Expense> allExpenses = expenseRepository.findAllByUserIdAndSpentDateAfterAndSpentDateBefore(userId, after, before);

        //-----------------------------------------------------------------
        // No results, return empty insight overview
        //-----------------------------------------------------------------
        if (allExpenses.isEmpty()) {

            return new InsightsOverviewDTOBuilder()
                    .withInsightsOverview(Collections.emptyMap())
                    .build();
        }

        Map<String, TotalPerMonthDTO> insightsOverview = allExpenses
                .stream()
                .collect(Collectors
                        .groupingBy(expense -> DATE_TIME_FORMATTER.print(expense.getSpentDate()),
                                Collectors.collectingAndThen(Collectors.mapping(Expense::getValue, Collectors.reducing(BigDecimal::add)), bigDecimal -> {
                                    BigDecimal totalExpenseAsBigDecimal = bigDecimal.isPresent() ? bigDecimal.get() : BigDecimal.ZERO;
                                    BigDecimal totalExpenseAsBigDecimalScaled = totalExpenseAsBigDecimal.setScale(DIGITS_SCALE, BigDecimal.ROUND_DOWN);

                                    return new TotalPerMonthDTOBuilder()
                                            .withTotalAmount(totalExpenseAsBigDecimalScaled.doubleValue())
                                            .withTotalAmountFormatted(DECIMAL_FORMAT.format(totalExpenseAsBigDecimalScaled))
                                            .build();
                                })));

        return new InsightsOverviewDTOBuilder()
                .withInsightsOverview(insightsOverview)
                .build();
    }
}