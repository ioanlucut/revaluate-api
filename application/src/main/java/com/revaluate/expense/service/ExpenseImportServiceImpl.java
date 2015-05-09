package com.revaluate.expense.service;

import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.importer.profile.ExpenseProfileDTO;
import com.revaluate.expense.exception.ExpenseException;
import com.revaluate.importer.ImporterException;
import com.revaluate.importer.ImporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class ExpenseImportServiceImpl implements ExpenseImportService {

    @Autowired
    private ImporterService importerService;

    @Autowired
    private ExpenseService expenseService;

    @Override
    public List<ExpenseDTO> importExpenses(InputStream csv, ExpenseProfileDTO expenseProfileDTO, int userId) throws ExpenseException {
        try {
            List<ExpenseDTO> expenseDTOs = importerService.importFrom(new BufferedReader(new InputStreamReader(csv)), expenseProfileDTO);
            return expenseService.bulkCreate(expenseDTOs, userId);
        } catch (ImporterException ex) {

            throw new ExpenseException(ex);
        }
    }
}