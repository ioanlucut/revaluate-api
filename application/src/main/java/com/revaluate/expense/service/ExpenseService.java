package com.revaluate.expense.service;

import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.expense.ExpensesQueryResponseDTO;
import com.revaluate.expense.exception.ExpenseException;
import net.bull.javamelody.MonitoredWithSpring;
import org.joda.time.LocalDateTime;
import org.springframework.data.domain.PageRequest;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Optional;

@MonitoredWithSpring
public interface ExpenseService {

    int MIN_SIZE_LIST = 1;
    int MAX_SIZE_LIST = 1000;

    @NotNull
    ExpenseDTO create(@Valid ExpenseDTO expenseDTO, int userId) throws ExpenseException;

    @NotNull
    ExpenseDTO update(@Valid ExpenseDTO expenseDTO, int userId) throws ExpenseException;

    @NotNull
    List<ExpenseDTO> findAllExpensesFor(int userId, Optional<PageRequest> pageRequest);

    @NotNull
    List<ExpenseDTO> findAllExpensesOfCategoryFor(int userId, int categoryId, Optional<PageRequest> pageRequest);

    @NotNull
    ExpensesQueryResponseDTO findExpensesOfCategoryGroupBySpentDateFor(int userId, int categoryId, PageRequest pageRequest);

    @NotNull
    List<ExpenseDTO> findAllExpensesAfter(int userId, LocalDateTime after);

    @NotNull
    List<ExpenseDTO> findAllExpensesBefore(int userId, LocalDateTime before);

    @NotNull
    List<ExpenseDTO> findAllExpensesAfterBefore(int userId, LocalDateTime after, LocalDateTime before);

    @NotNull
    ExpensesQueryResponseDTO findExpensesGroupBySpentDate(int userId, PageRequest pageRequest);

    @NotNull
    List<ExpenseDTO> findAllExpensesWithCategoryIdAfterBefore(int userId, int categoryId, LocalDateTime after, LocalDateTime before);

    @NotNull
    List<ExpenseDTO> findAllExpensesWithCategoryIdFor(int categoryId, int userId);

    @NotNull
    List<ExpenseDTO> bulkCreate(@Size(min = MIN_SIZE_LIST, max = MAX_SIZE_LIST) @NotNull @Valid List<ExpenseDTO> categoryDTOs, int userId) throws ExpenseException;

    void bulkDelete(@Size(min = MIN_SIZE_LIST, max = MAX_SIZE_LIST) @NotNull @Valid List<ExpenseDTO> expenseDTOs, int userId);

    void remove(@Min(1) int expenseId, int userId) throws ExpenseException;
}