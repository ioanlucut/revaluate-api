package com.revaluate.expense.persistence;

import org.joda.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    Optional<Expense> findOneByIdAndUserId(int expenseId, int userId);

    List<Expense> findAllByUserId(int userId);

    Page<Expense> findAllByUserId(int userId, Pageable pageable);

    @Query("SELECT e.spentDate FROM Expense e WHERE e.user.id = ?1")
    List<LocalDateTime> selectExistingSpentDates(int userId);

    long countByUserId(int userId);

    List<Expense> findAllByUserIdAndSpentDateAfter(int userId, LocalDateTime after);

    List<Expense> findAllByUserIdAndSpentDateBefore(int userId, LocalDateTime before);

    List<Expense> findAllByUserIdAndSpentDateAfterAndSpentDateBefore(int userId, LocalDateTime after, LocalDateTime before);

    List<Expense> findAllByUserIdAndCategoryIdAndSpentDateAfterAndSpentDateBefore(int userId, int categoryId, LocalDateTime after, LocalDateTime before);

    List<Expense> findAllByUserIdAndCategoryId(int userId, int categoryId);

    Page<Expense> findAllByUserIdAndCategoryId(int userId, int categoryId, Pageable pageable);

    Optional<Expense> findFirstByUserIdOrderBySpentDateDesc(int userId);

    Optional<Expense> findFirstByUserIdOrderBySpentDateAsc(int userId);

    @Modifying
    @Transactional
    @Query("delete from Expense u where u.user.id = ?1")
    void removeByUserId(int userId);

    @Modifying
    @Transactional
    @Query("delete from Expense u where u.category.id = ?1")
    void removeByCategoryId(int userId);
}