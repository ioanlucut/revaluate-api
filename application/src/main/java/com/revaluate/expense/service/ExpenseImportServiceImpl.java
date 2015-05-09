package com.revaluate.expense.service;

import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.importer.profile.ExpenseCategoriesMatchingProfileDTO;
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
import java.util.Map;
import java.util.stream.Collectors;

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

            //-----------------------------------------------------------------
            // Check if every category which is present on the import has a matching existing category
            //-----------------------------------------------------------------
            categoryMatchesAreNotProperlyDefined(expenseProfileDTO, expenseDTOs);

            //-----------------------------------------------------------------
            // Make sure the matching categories are applied
            //-----------------------------------------------------------------
            List<ExpenseDTO> transformedExpenseDTOs = expenseDTOs
                    .stream()
                    .map(expenseDTO -> {
                        Map<String, CategoryDTO> categoriesMatchingMap = expenseProfileDTO.getExpenseCategoriesMatchingProfileDTO().getCategoriesMatchingMap();
                        CategoryDTO mappedCategoryDTO = categoriesMatchingMap.get(expenseDTO.getCategory().getName());
                        expenseDTO.setCategory(mappedCategoryDTO);

                        return expenseDTO;
                    })
                    .collect(Collectors.toList());

            return expenseService.bulkCreate(transformedExpenseDTOs, userId);
        } catch (ImporterException ex) {

            throw new ExpenseException(ex);
        }
    }

    private void categoryMatchesAreNotProperlyDefined(ExpenseProfileDTO expenseProfileDTO, List<ExpenseDTO> expenseDTOs) throws ExpenseException {
        Map<String, List<ExpenseDTO>> categoriesGroupedPerName = expenseDTOs
                .stream()
                .collect(Collectors.groupingBy(expenseDTO -> expenseDTO.getCategory().getName()));
        ExpenseCategoriesMatchingProfileDTO expenseCategoriesMatchingProfileDTO = expenseProfileDTO.getExpenseCategoriesMatchingProfileDTO();
        int size = expenseCategoriesMatchingProfileDTO.getCategoriesMatchingMap().size();

        boolean allHaveAMatch = categoriesGroupedPerName
                .entrySet()
                .stream()
                .allMatch(stringListEntry ->
                        expenseCategoriesMatchingProfileDTO
                                .getCategoriesMatchingMap()
                                .entrySet()
                                .stream()
                                .anyMatch(stringCategoryDTOEntry -> stringCategoryDTOEntry.getKey().equals(stringListEntry.getKey())));

        if (categoriesGroupedPerName.size() != size || !allHaveAMatch) {
            throw new ExpenseException(String.format("Only %s categories defined from total of %s", size, categoriesGroupedPerName.size()));
        }
    }
}