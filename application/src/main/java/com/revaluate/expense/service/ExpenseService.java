package com.revaluate.expense.service;

import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.expense.exception.ExpenseException;
import org.joda.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public interface ExpenseService {

    int MIN_SIZE_LIST = 1;
    int MAX_SIZE_LIST = 1000;

    @NotNull
    ExpenseDTO create(@Valid ExpenseDTO expenseDTO, int userId) throws ExpenseException;

    @NotNull
    ExpenseDTO update(@Valid ExpenseDTO expenseDTO, int userId) throws ExpenseException;

    @NotNull
    List<ExpenseDTO> findAllExpensesFor(int userId);

    @NotNull
    List<ExpenseDTO> findAllExpensesAfter(int userId, LocalDateTime after);

    @NotNull
    List<ExpenseDTO> findAllExpensesBefore(int userId, LocalDateTime before);

    @NotNull
    List<ExpenseDTO> findAllExpensesAfterBefore(int userId, LocalDateTime after, LocalDateTime before);

    @NotNull
    List<ExpenseDTO> findAllExpensesWithCategoryIdFor(int categoryId, int userId);

    @NotNull
    List<ExpenseDTO> bulkCreate(@Size(min = MIN_SIZE_LIST, max = MAX_SIZE_LIST) @NotNull @Valid List<ExpenseDTO> categoryDTOs, int userId) throws ExpenseException;

    void bulkDelete(@Size(min = MIN_SIZE_LIST, max = MAX_SIZE_LIST) @NotNull @Valid List<ExpenseDTO> expenseDTOs, int userId) throws ExpenseException;

    void remove(@Min(1) int expenseId, int userId) throws ExpenseException;
}