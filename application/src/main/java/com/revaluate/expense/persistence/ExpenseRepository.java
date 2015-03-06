package com.revaluate.expense.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import org.joda.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    Optional<Expense> findOneByIdAndUserId(int expenseId, int userId);

    List<Expense> findAllByUserId(int userId);

    List<Expense> findAllByUserIdAndSpentDateAfter(int userId, LocalDateTime after);

    List<Expense> findAllByUserIdAndSpentDateBefore(int userId, LocalDateTime before);

    List<Expense> findAllByUserIdAndSpentDateAfterAndSpentDateBefore(int userId, LocalDateTime after, LocalDateTime before);

    List<Expense> findAllByUserIdAndCategoryId(int userId, int categoryId);
}