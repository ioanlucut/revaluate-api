package com.revaluate.expense.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    Optional<Expense> findOneByIdAndUserId(int expenseId, int userId);

    List<Expense> findAllByUserId(int userId);
}