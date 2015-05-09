package com.revaluate.expense.service;

import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.importer.profile.ExpenseProfileDTO;
import com.revaluate.expense.exception.ExpenseException;

import javax.validation.constraints.NotNull;
import java.io.InputStream;
import java.util.List;

public interface ExpenseImportService {

    @NotNull
    List<ExpenseDTO> importExpenses(@NotNull InputStream csv, @NotNull ExpenseProfileDTO expenseProfileDTO, int userId) throws ExpenseException;
}