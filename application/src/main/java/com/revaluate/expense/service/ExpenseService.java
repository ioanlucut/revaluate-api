package com.revaluate.expense.service;

import com.revaluate.expense.domain.ExpenseDTO;
import com.revaluate.expense.exception.ExpenseException;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public interface ExpenseService {

    @NotNull
    ExpenseDTO create(@Valid ExpenseDTO expenseDTO, int userId) throws ExpenseException;

    @NotNull
    ExpenseDTO update(@Valid ExpenseDTO expenseDTO, int userId) throws ExpenseException;

    void remove(@Min(1) int expenseId, int userId) throws ExpenseException;
}