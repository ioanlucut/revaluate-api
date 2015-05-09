package com.revaluate.expense.service;

import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.importer.profile.ExpenseCategoriesMatchingProfileDTO;
import com.revaluate.domain.importer.profile.ExpenseProfileDTO;
import com.revaluate.expense.exception.ExpenseException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.InputStream;
import java.util.List;

public interface ExpenseImportService {

    int MIN_SIZE_LIST = 1;
    int MAX_SIZE_LIST = 1000;

    @NotNull
    List<ExpenseDTO> parseAndAnalyse(@NotNull InputStream csv, @NotNull @Valid ExpenseProfileDTO expenseProfileDTO) throws ExpenseException;

    @NotNull
    List<ExpenseDTO> importExpenses(@Size(min = MIN_SIZE_LIST, max = MAX_SIZE_LIST) @NotNull @Valid List<ExpenseDTO> categoryDTOs, @NotNull @Valid ExpenseCategoriesMatchingProfileDTO expenseCategoriesMatchingProfileDTO, int userId) throws ExpenseException;
}