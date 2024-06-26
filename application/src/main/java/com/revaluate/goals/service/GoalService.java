package com.revaluate.goals.service;

import com.revaluate.domain.goal.GoalDTO;
import com.revaluate.goals.exception.GoalException;
import net.bull.javamelody.MonitoredWithSpring;
import org.joda.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@MonitoredWithSpring
public interface GoalService {

    int MIN_SIZE_LIST = 1;
    int MAX_SIZE_LIST = 1000;

    boolean isUniqueGoalWithCategoryBetween(int userId, int categoryId, LocalDateTime after, LocalDateTime before);

    @NotNull
    GoalDTO create(@Valid GoalDTO GoalDTO, int userId) throws GoalException;

    @NotNull
    GoalDTO update(@Valid GoalDTO GoalDTO, int userId) throws GoalException;

    @NotNull
    List<GoalDTO> findAllGoalsFor(int userId);

    @NotNull
    List<GoalDTO> findAllGoalsAfterBefore(int userId, LocalDateTime after, LocalDateTime before);

    void bulkDelete(@Size(min = MIN_SIZE_LIST, max = MAX_SIZE_LIST) @NotNull @Valid List<GoalDTO> GoalDTOs, int userId) throws GoalException;

    void remove(@Min(1) int expenseId, int userId) throws GoalException;
}