package com.revaluate.importer;

import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.importer.profile.ExpenseProfile;

import javax.validation.constraints.NotNull;
import java.io.Reader;
import java.util.List;

public interface ImporterService {

    List<ExpenseDTO> importFrom(Reader reader, @NotNull ExpenseProfile expenseProfile);
}