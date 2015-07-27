package com.revaluate.insights.service;

import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.insights.monthly.InsightsMonthlyDTO;
import com.revaluate.expense.persistence.Expense;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface MonthlyInsightsService {

    @NotNull
    InsightsMonthlyDTO fetchMonthlyInsightsAfterBeforePeriod(int userId, @NotNull LocalDateTime after, @NotNull LocalDateTime before);

    @NotNull
    InsightsMonthlyDTO computeMonthlyInsightsAfterBeforePeriod(List<CategoryDTO> categories, List<Expense> allExpenses, LocalDateTime after, LocalDateTime before);
}