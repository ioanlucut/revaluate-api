package com.revaluate.insights.service;

import com.revaluate.domain.insights.daily.InsightsDailyDTO;
import com.revaluate.expense.persistence.Expense;
import net.bull.javamelody.MonitoredWithSpring;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import java.util.List;

@MonitoredWithSpring
public interface DailyInsightsService {

    /**
     * Fetches expenses from database for a specified period and then computes daily insights.
     */
    @NotNull
    InsightsDailyDTO fetchDailyInsightsAfterBeforePeriod(int userId, @NotNull LocalDateTime after, @NotNull LocalDateTime before);

    /**
     * Computes daily insights for a specified period, and a given expenses list.
     */
    @NotNull
    InsightsDailyDTO computeDailyInsightsAfterBeforePeriod(int userId, @NotNull List<Expense> allExpenses, @NotNull LocalDateTime after, @NotNull LocalDateTime before);
}