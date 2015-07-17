package com.revaluate.insights.service;

import com.revaluate.category.persistence.Category;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.insights.monthly.*;
import com.revaluate.expense.persistence.Expense;
import org.joda.time.LocalDateTime;
import org.joda.time.Months;
import org.joda.time.YearMonth;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface InsightsUtils {

    static BigDecimal totalOf(List<Expense> expenseList) {

        return expenseList
                .stream()
                .map(Expense::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    static List<YearMonth> yearMonthsBetween(LocalDateTime after, LocalDateTime before) {
        Months months = Months.monthsBetween(after, before);
        AtomicReference<LocalDateTime> afterReference = new AtomicReference<>(after);

        return IntStream
                .range(0, months.getMonths())
                .mapToObj(monthIndex -> {
                    LocalDateTime andUpdate = afterReference.updateAndGet(dateTime -> dateTime.plusMonths(1));

                    return new YearMonth(andUpdate.getYear(), andUpdate.getMonthOfYear());
                })
                .collect(Collectors.toList());
    }
}
