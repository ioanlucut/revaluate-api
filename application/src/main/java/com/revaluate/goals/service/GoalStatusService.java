package com.revaluate.goals.service;

import com.revaluate.domain.goal.GoalStatusDTO;
import com.revaluate.goals.persistence.Goal;
import net.bull.javamelody.MonitoredWithSpring;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@MonitoredWithSpring
public interface GoalStatusService {

    @NotNull
    GoalStatusDTO computeGoalStatusFor(int userId, @NotNull @Valid Goal goal);
}