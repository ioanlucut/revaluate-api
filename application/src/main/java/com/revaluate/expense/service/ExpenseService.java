package com.revaluate.expense.service;

import com.revaluate.expense.domain.ExpenseDTO;
import com.revaluate.expense.exception.ExpenseException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

public interface ExpenseService {

    @NotNull
    ExpenseDTO create(@Valid ExpenseDTO expenseDTO, int userId) throws ExpenseException;

    @NotNull
    ExpenseDTO update(@Valid ExpenseDTO expenseDTO, int userId) throws ExpenseException;

    List<ExpenseDTO> findAllExpensesFor(int userId);

    List<ExpenseDTO> findAllExpensesAfter(int userId, Date after);

    List<ExpenseDTO> findAllExpensesBefore(int userId, Date before);

    List<ExpenseDTO> findAllExpensesAfterBefore(int userId, Date after, Date before);

    List<ExpenseDTO> findAllExpensesWithCategoryIdFor(int userId, int categoryId);

    void remove(@Min(1) int expenseId, int userId) throws ExpenseException;
}