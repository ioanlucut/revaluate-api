package com.revaluate.goals.service;

import com.revaluate.account.persistence.UserRepository;
import com.revaluate.category.persistence.Category;
import com.revaluate.category.persistence.CategoryRepository;
import com.revaluate.domain.goal.GoalDTO;
import com.revaluate.goals.exception.GoalException;
import com.revaluate.goals.persistence.Goal;
import com.revaluate.goals.persistence.GoalRepository;
import org.dozer.DozerBeanMapper;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
public class GoalServiceImpl implements GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GoalStatusService goalStatusService;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Override
    public boolean isUniqueGoalWithCategoryBetween(int userId, int categoryId, LocalDateTime after, LocalDateTime before) {
        return goalRepository.findAllByUserIdAndCategoryIdAndStartDateAfterAndEndDateBefore(userId, categoryId, after, before).isEmpty();
    }

    @Override
    public GoalDTO create(GoalDTO goalDTO, int userId) throws GoalException {
        Goal goal = dozerBeanMapper.map(goalDTO, Goal.class);
        Optional<Category> categoryByName = categoryRepository.findOneByIdAndUserId(goal.getCategory().getId(), userId);
        Category categoryFound = categoryByName.orElseThrow(() -> new GoalException("Category doesn't exist"));

        goal.setUser(userRepository.findOne(userId));
        goal.setCategory(categoryFound);
        Goal savedGoal = goalRepository.save(goal);

        return computeGoalDTO(savedGoal, userId);
    }

    private GoalDTO computeGoalDTO(Goal goal, int userId) {
        GoalDTO goalDTO = dozerBeanMapper.map(goal, GoalDTO.class);
        goalDTO.setGoalStatusDTO(goalStatusService.computeGoalStatusFor(userId, goal));

        return goalDTO;
    }

    @Override
    public GoalDTO update(GoalDTO goalDTO, int userId) throws GoalException {
        Optional<Goal> goalById = goalRepository.findOneByIdAndUserId(goalDTO.getId(), userId);
        Goal foundGoal = goalById.orElseThrow(() -> new GoalException("Goal doesn't exist"));

        if (!categoryRepository.findOneByIdAndUserId(goalDTO.getCategory().getId(), userId).isPresent()) {
            throw new GoalException("This category does not exist");
        }

        //-----------------------------------------------------------------
        // Update the goal with given goal DTO
        //-----------------------------------------------------------------
        dozerBeanMapper.map(goalDTO, foundGoal);

        Goal savedGoal = goalRepository.save(foundGoal);
        return computeGoalDTO(savedGoal, userId);
    }

    @Override
    public List<GoalDTO> findAllGoalsFor(int userId) {
        List<Goal> goals = goalRepository.findAllByUserId(userId);

        return collectAndGet(goals, userId);
    }

    @Override
    public List<GoalDTO> findAllGoalsAfterBefore(int userId, LocalDateTime after, LocalDateTime before) {
        List<Goal> goals = goalRepository.findAllByUserIdAndStartDateAfterAndEndDateBefore(userId, after, before);

        return collectAndGet(goals, userId);
    }

    @Override
    public void bulkDelete(List<GoalDTO> goalDTOs, int userId) throws GoalException {
        //-----------------------------------------------------------------
        // Goals have to exist for this user.
        //-----------------------------------------------------------------
        if (!goalDTOs.stream().allMatch(goalDTO -> goalRepository.findOneByIdAndUserId(goalDTO.getId(), userId).isPresent())) {
            throw new GoalException("One or more goal is invalid.");
        }

        List<Goal> goals = goalDTOs.stream()
                .map(goalDTO -> goalRepository.findOneByIdAndUserId(goalDTO.getId(), userId).get())
                .collect(Collectors.toList());

        goalRepository.delete(goals);
    }

    @Override
    public void remove(int goalId, int userId) throws GoalException {
        Optional<Goal> goalById = goalRepository.findOneByIdAndUserId(goalId, userId);
        goalById.orElseThrow(() -> new GoalException("Goal doesn't exist"));

        goalRepository.delete(goalId);
    }

    private List<GoalDTO> collectAndGet(List<Goal> goals, int userId) {
        return goals
                .stream()
                .map(goal -> this.computeGoalDTO(goal, userId))
                .collect(Collectors.toList());
    }
}