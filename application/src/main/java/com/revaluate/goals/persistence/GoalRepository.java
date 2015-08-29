package com.revaluate.goals.persistence;

import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Integer> {

    @Query("SELECT e.endDate FROM Goal e WHERE e.user.id = ?1")
    List<LocalDateTime> selectExistingEndDates(int userId);

    Optional<Goal> findOneByIdAndUserId(int expenseId, int userId);

    List<Goal> findAllByUserId(int userId);

    List<Goal> findAllByUserIdAndCategoryId(int userId, int categoryId);

    List<Goal> findAllByUserIdAndStartDateAfterAndEndDateBefore(int userId, LocalDateTime after, LocalDateTime before);

    List<Goal> findAllByUserIdAndCategoryIdAndStartDateAfterAndEndDateBefore(int userId, int categoryId, LocalDateTime after, LocalDateTime before);
}