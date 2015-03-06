package com.revaluate.expense.service;

import com.revaluate.expense.domain.ExpenseDTO;
import com.revaluate.expense.exception.ExpenseException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.joda.time.LocalDateTime;
import java.util.List;

public interface ExpenseService {

    @NotNull
    ExpenseDTO create(@Valid ExpenseDTO expenseDTO, int userId) throws ExpenseException;

    @NotNull
    ExpenseDTO update(@Valid ExpenseDTO expenseDTO, int userId) throws ExpenseException;

    List<ExpenseDTO> findAllExpensesFor(int userId);

    List<ExpenseDTO> findAllExpensesAfter(int userId, LocalDateTime after);

    List<ExpenseDTO> findAllExpensesBefore(int userId, LocalDateTime before);

    List<ExpenseDTO> findAllExpensesAfterBefore(int userId, LocalDateTime after, LocalDateTime before);

    List<ExpenseDTO> findAllExpensesWithCategoryIdFor(int userId, int categoryId);

    void remove(@Min(1) int expenseId, int userId) throws ExpenseException;
}