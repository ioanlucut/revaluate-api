package com.revaluate.insights.service;

import com.revaluate.expense.persistence.Expense;

import java.math.BigDecimal;
import java.util.List;

public interface InsightsUtils {

    static BigDecimal totalOf(List<Expense> expenseList) {

        return expenseList
                .stream()
                .map(Expense::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
