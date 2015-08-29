package com.revaluate.goals.service;

import com.revaluate.domain.goal.GoalStatusDTO;
import com.revaluate.domain.goal.GoalStatusDTOBuilder;
import com.revaluate.domain.insights.daily.InsightsDailyDTO;
import com.revaluate.expense.persistence.Expense;
import com.revaluate.expense.persistence.ExpenseRepository;
import com.revaluate.goals.persistence.Goal;
import com.revaluate.insights.service.DailyInsightsService;
import com.revaluate.insights.service.InsightsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class GoalStatusServiceImpl implements GoalStatusService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private DailyInsightsService dailyInsightsService;

    @Override
    public GoalStatusDTO computeGoalStatusFor(int userId, Goal goal) {
        List<Expense> concernedExpenses = expenseRepository.findAllByUserIdAndCategoryIdAndSpentDateAfterAndSpentDateBefore(userId, goal.getCategory().getId(), goal.getStartDate(), goal.getEndDate());
        InsightsDailyDTO insightsDailyOfThisGoal = dailyInsightsService.computeDailyInsightsAfterBeforePeriod(userId, concernedExpenses, goal.getStartDate(), goal.getEndDate());
        double totalSpent = InsightsUtils.totalOf(concernedExpenses).doubleValue();

        return new GoalStatusDTOBuilder()
                .withCurrentValue(totalSpent)
                .withInsightsDaily(insightsDailyOfThisGoal)
                .withGoalAccomplished(getGoalAccomplishedStatus(goal, totalSpent))
                .build();
    }

    private boolean getGoalAccomplishedStatus(Goal goal, double totalSpent) {
        switch (goal.getGoalTarget()) {
            case LESS_THAN: {
                return totalSpent < goal.getValue().doubleValue();
            }
            case MORE_THAN: {
                return totalSpent > goal.getValue().doubleValue();
            }
        }

        return Boolean.FALSE;
    }
}
