package com.revaluate.insights.service;

import com.revaluate.category.persistence.Category;
import com.revaluate.domain.insights.InsightDTO;
import com.revaluate.domain.insights.InsightDTOBuilder;
import com.revaluate.expense.persistence.Expense;
import com.revaluate.expense.persistence.ExpenseRepository;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Validated
public class InsightServiceImpl implements InsightService {

    public static final int DIGITS_SCALE = 2;
    public static final String PATTERN = "0.00";

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public InsightDTO fetchInsightAfterBeforePeriod(int userId, @NotNull LocalDateTime after, @NotNull LocalDateTime before) {
        List<Expense> allExpenses = expenseRepository.findAllByUserIdAndSpentDateAfterAndSpentDateBefore(userId, after, before);

        //-----------------------------------------------------------------
        // No results, return empty insight
        //-----------------------------------------------------------------
        if (allExpenses.isEmpty()) {
            return new InsightDTOBuilder()
                    .withFrom(after)
                    .withTo(before)
                    .withInsightData(Collections.emptyList())
                    .withInsightColors(Collections.emptyList())
                    .withInsightLabels(Collections.emptyList())
                    .build();
        }

        //-----------------------------------------------------------------
        // Otherwise, return a nice insight
        //-----------------------------------------------------------------
        Map<Category, List<Expense>> groupedByCategory = allExpenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory));

        List<String> insightsLabels = groupedByCategory.entrySet()
                .stream()
                .map(categoryListEntry -> categoryListEntry.getKey().getName())
                .collect(Collectors.toList());

        List<String> insightColors = groupedByCategory.entrySet()
                .stream()
                .map(categoryListEntry -> categoryListEntry.getKey().getColor())
                .collect(Collectors.toList());

        DecimalFormat decimalFormat = new DecimalFormat(PATTERN);

        List<String> insightData = groupedByCategory.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .map(expenses -> expenses.stream()
                        .map(Expense::getValue)
                        .reduce(BigDecimal.ZERO, BigDecimal::add))
                .map(bigDecimal -> decimalFormat.format(bigDecimal.setScale(DIGITS_SCALE, BigDecimal.ROUND_DOWN)))
                .collect(Collectors.toList());

        BigDecimal totalExpenses = allExpenses
                .stream()
                .map(Expense::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new InsightDTOBuilder()
                .withFrom(after)
                .withTo(before)
                .withInsightData(insightData)
                .withInsightColors(insightColors)
                .withInsightLabels(insightsLabels)
                .withNumberOfTransactions(allExpenses.size())
                .withTotalAmountSpent(totalExpenses.doubleValue())
                .build();
    }
}
