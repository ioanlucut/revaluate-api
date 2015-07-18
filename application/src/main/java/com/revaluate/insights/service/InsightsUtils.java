package com.revaluate.insights.service;

import com.revaluate.expense.persistence.Expense;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.Months;
import org.joda.time.YearMonth;

import java.math.BigDecimal;
import java.util.List;
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
        DateTime afterAsDateTime = after.toDateTime().withTimeAtStartOfDay();
        DateTime beforeAsDateTime = before.toDateTime().withTimeAtStartOfDay();
        Months months = Months.monthsBetween(afterAsDateTime, beforeAsDateTime);
        AtomicReference<DateTime> afterReference = new AtomicReference<>(afterAsDateTime);

        return IntStream
                .range(0, months.getMonths())
                .mapToObj(monthIndex -> {
                    DateTime andUpdate = afterReference.updateAndGet(dateTime -> dateTime.plusMonths(1));

                    return new YearMonth(andUpdate.getYear(), andUpdate.getMonthOfYear());
                })
                .collect(Collectors.toList());
    }
}
