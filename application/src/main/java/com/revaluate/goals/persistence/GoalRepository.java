package com.revaluate.goals.persistence;

import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Integer> {

    Optional<Goal> findOneByIdAndUserId(int expenseId, int userId);

    List<Goal> findAllByUserId(int userId);

    List<Goal> findAllByUserIdAndCategoryId(int userId, int categoryId);

    List<Goal> findAllByUserIdAndStartDateAfterAndEndDateBefore(int userId, LocalDateTime after, LocalDateTime before);

    List<Goal> findAllByUserIdAndCategoryIdAndStartDateAfterAndEndDateBefore(int userId, int categoryId, LocalDateTime after, LocalDateTime before);
}