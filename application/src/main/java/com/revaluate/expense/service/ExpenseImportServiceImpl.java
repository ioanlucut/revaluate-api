package com.revaluate.expense.service;

import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.importer.profile.*;
import com.revaluate.expense.exception.ExpenseException;
import com.revaluate.importer.ImporterException;
import com.revaluate.importer.ImporterParserService;
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
    private ImporterParserService importerParserService;

    @Autowired
    private ExpenseService expenseService;

    @Override
    public ExpensesImportDTO parseAndAnalyse(InputStream csv, ExpenseProfileDTO expenseProfileDTO) throws ExpenseException {
        try {
            List<ExpenseDTO> expenseDTOs = importerParserService.parseFrom(new BufferedReader(new InputStreamReader(csv)), expenseProfileDTO);

            //-----------------------------------------------------------------
            // We group the unmatched categories
            //-----------------------------------------------------------------
            Map<String, List<ExpenseDTO>> groupedByCategoryName = expenseDTOs.stream()
                    .collect(Collectors.groupingBy(expenseDTO -> expenseDTO.getCategory().getName()));

            //-----------------------------------------------------------------
            // We define a matching profile category
            //-----------------------------------------------------------------
            ExpenseCategoriesMatchingProfileDTO expenseCategoriesMatchingProfileDTO = new ExpenseCategoriesMatchingProfileDTOBuilder().build();

            //-----------------------------------------------------------------
            // Populate with unmatched keys
            //-----------------------------------------------------------------
            groupedByCategoryName
                    .entrySet()
                    .stream()
                    .map(Map.Entry::getKey)
                    .forEach(unmatchedCategoryName -> expenseCategoriesMatchingProfileDTO.getCategoriesMatchingMap().put(unmatchedCategoryName, null));

            return new ExpensesImportDTOBuilder()
                    .withExpenseDTOs(expenseDTOs)
                    .withExpenseCategoriesMatchingProfileDTO(expenseCategoriesMatchingProfileDTO)
                    .build();
        } catch (ImporterException ex) {

            throw new ExpenseException(ex);
        }
    }

    @Override
    public List<ExpenseDTO> importExpenses(ExpensesImportDTO expensesImportDTO, int userId) throws ExpenseException {
        ExpenseCategoriesMatchingProfileDTO expenseCategoriesMatchingProfileDTO = expensesImportDTO.getExpenseCategoriesMatchingProfileDTO();
        List<ExpenseDTO> expenseDTOs = expensesImportDTO.getExpenseDTOs();
        //-----------------------------------------------------------------
        // Check if every category which is present on the import has a matching existing category
        //-----------------------------------------------------------------
        categoryMatchesAreNotProperlyDefined(expenseCategoriesMatchingProfileDTO, expenseDTOs);

        //-----------------------------------------------------------------
        // Make sure the matching categories are applied
        //-----------------------------------------------------------------
        List<ExpenseDTO> transformedExpenseDTOs = expenseDTOs
                .stream()
                .map(expenseDTO -> {
                    Map<String, CategoryDTO> categoriesMatchingMap = expenseCategoriesMatchingProfileDTO.getCategoriesMatchingMap();
                    CategoryDTO mappedCategoryDTO = categoriesMatchingMap.get(expenseDTO.getCategory().getName());
                    expenseDTO.setCategory(mappedCategoryDTO);

                    return expenseDTO;
                })
                .collect(Collectors.toList());

        return expenseService.bulkCreate(transformedExpenseDTOs, userId);
    }

    private void categoryMatchesAreNotProperlyDefined(ExpenseCategoriesMatchingProfileDTO expenseCategoriesMatchingProfileDTO, List<ExpenseDTO> expenseDTOs) throws ExpenseException {
        Map<String, List<ExpenseDTO>> categoriesGroupedPerName = expenseDTOs
                .stream()
                .collect(Collectors.groupingBy(expenseDTO -> expenseDTO.getCategory().getName()));
        int size = expenseCategoriesMatchingProfileDTO.getCategoriesMatchingMap().size();

        boolean allHaveAMatch = categoriesGroupedPerName
                .entrySet()
                .stream()
                .allMatch(matchCategoryEntry -> expenseCategoriesMatchingProfileDTO.getCategoriesMatchingMap().containsKey(matchCategoryEntry.getKey()));

        if (categoriesGroupedPerName.size() != size || !allHaveAMatch) {
            throw new ExpenseException(String.format("Only %s categories defined from total of %s", size, categoriesGroupedPerName.size()));
        }
    }
}