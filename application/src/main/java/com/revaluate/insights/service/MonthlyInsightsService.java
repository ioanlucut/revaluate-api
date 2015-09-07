package com.revaluate.insights.service;

import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.insights.monthly.InsightsMonthlyDTO;
import com.revaluate.expense.persistence.Expense;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface MonthlyInsightsService {

    /**
     * Fetches expenses from database for a specified period and then computes monthly insights.
     */
    @NotNull
    InsightsMonthlyDTO fetchMonthlyInsightsAfterBeforePeriod(int userId, @NotNull LocalDateTime after, @NotNull LocalDateTime before);

    /**
     * Computes monthly insights for a specified period, and a given expenses list.
     */
    @NotNull
    InsightsMonthlyDTO computeMonthlyInsightsAfterBeforePeriod(List<CategoryDTO> categories, List<Expense> allExpenses, LocalDateTime after, LocalDateTime before);
}