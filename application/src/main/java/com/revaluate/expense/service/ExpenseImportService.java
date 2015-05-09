package com.revaluate.expense.service;

import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.importer.profile.ExpenseProfileDTO;
import com.revaluate.domain.importer.profile.ExpensesImportDTO;
import com.revaluate.expense.exception.ExpenseException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.List;

public interface ExpenseImportService {

    @NotNull
    List<ExpenseDTO> parseAndAnalyse(@NotNull InputStream csv, @NotNull @Valid ExpenseProfileDTO expenseProfileDTO) throws ExpenseException;

    @NotNull
    List<ExpenseDTO> importExpenses(@NotNull @Valid ExpensesImportDTO expensesImportDTO, int userId) throws ExpenseException;
}