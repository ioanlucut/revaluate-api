package com.revaluate.goals.service;

import com.revaluate.domain.goal.GoalStatusDTO;
import com.revaluate.goals.persistence.Goal;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public interface GoalStatusService {

    @NotNull
    GoalStatusDTO computeGoalStatusFor(int userId, @NotNull @Valid Goal goal);
}