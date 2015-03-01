package com.revaluate.expense.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    Optional<Expense> findOneByIdAndUserId(int expenseId, int userId);

    List<Expense> findAllByUserId(int userId);

    List<Expense> findAllByUserIdAndAddedDateAfter(int userId, Date after);

    List<Expense> findAllByUserIdAndAddedDateBefore(int userId, Date before);

    List<Expense> findAllByUserIdAndAddedDateAfterAndAddedDateBefore(int userId, Date after, Date before);

    List<Expense> findAllByUserIdAndCategoryId(int userId, int categoryId);
}