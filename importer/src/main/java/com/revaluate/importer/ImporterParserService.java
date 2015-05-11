package com.revaluate.importer;

import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.importer.profile.ExpenseProfileDTO;

import javax.validation.constraints.NotNull;
import java.io.Reader;
import java.util.List;

public interface ImporterParserService {

    List<ExpenseDTO> parseFrom(Reader reader, @NotNull ExpenseProfileDTO expenseProfileDTO) throws ImporterException;

    boolean isValidInput(Reader reader, @NotNull ExpenseProfileDTO expenseProfileDTO) throws ImporterException;
}