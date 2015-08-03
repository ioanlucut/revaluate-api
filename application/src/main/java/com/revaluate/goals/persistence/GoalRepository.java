package com.revaluate.goals.persistence;

import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Integer> {

    Optional<Goal> findOneByIdAndUserId(int expenseId, int userId);

    List<Goal> findAllByUserId(int userId);

    long countByUserId(int userId);

    List<Goal> findAllByUserIdAndCategoryId(int userId, int categoryId);

    List<Goal> findAllByUserIdAndStartDateAfterAndEndDateBefore(int userId, LocalDateTime after, LocalDateTime before);

    List<Goal> findAllByUserIdAndCategoryIdAndStartDateAfterAndEndDateBefore(int userId, int categoryId, LocalDateTime after, LocalDateTime before);

    @Modifying
    @Transactional
    @Query("delete from Goal u where u.user.id = ?1")
    void removeByUserId(int userId);

    @Modifying
    @Transactional
    @Query("delete from Goal u where u.category.id = ?1")
    void removeByCategoryId(int userId);
}