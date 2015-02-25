package com.revaluate.expense.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    Expense findOneByIdAndUserId(int expenseId, int userId);
}