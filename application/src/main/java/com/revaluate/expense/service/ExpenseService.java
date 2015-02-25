package com.revaluate.expense.service;

import com.revaluate.expense.domain.ExpenseDTO;
import com.revaluate.expense.exception.ExpenseException;

public interface ExpenseService {

    ExpenseDTO create(ExpenseDTO expenseDTO, int userId) throws ExpenseException;

    ExpenseDTO update(ExpenseDTO expenseDTO, int userId) throws ExpenseException;

    void remove(int expenseId, int userId) throws ExpenseException;
}