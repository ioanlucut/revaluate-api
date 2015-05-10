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
import java.util.Optional;
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
            // Build the list of unmatched categories
            //-----------------------------------------------------------------
            List<ExpenseCategoryMatchingProfileDTO> expenseCategoryMatchingProfileDTOs = groupedByCategoryName
                    .entrySet()
                    .stream()
                    .map(Map.Entry::getKey)
                    .map(categoryCandidateName -> new ExpenseCategoryMatchingProfileDTOBuilder()
                            .withCategoryCandidateName(categoryCandidateName)
                            .build())
                    .collect(Collectors.toList());

            return new ExpensesImportDTOBuilder()
                    .withExpenseDTOs(expenseDTOs)
                    .withExpenseCategoryMatchingProfileDTOs(expenseCategoryMatchingProfileDTOs)
                    .build();
        } catch (ImporterException ex) {

            throw new ExpenseException(ex);
        }
    }

    @Override
    public List<ExpenseDTO> importExpenses(ExpensesImportDTO expensesImportDTO, int userId) throws ExpenseException {
        //-----------------------------------------------------------------
        // Make a map transform for faster retrieval. We map the category dto into optional,
        // due to http://stackoverflow.com/questions/24630963/java-8-nullpointerexception-in-collectors-tomap
        //-----------------------------------------------------------------
        Map<String, Optional<CategoryDTO>> categoriesMatchingMap = expensesImportDTO
                .getExpenseCategoryMatchingProfileDTOs()
                .stream()
                .collect(Collectors.toMap(ExpenseCategoryMatchingProfileDTO::getCategoryCandidateName, getCategoryCandidateName -> Optional.ofNullable(getCategoryCandidateName.getCategoryDTO())));

        List<ExpenseDTO> expenseDTOs = expensesImportDTO.getExpenseDTOs();

        //-----------------------------------------------------------------
        // Check if every category which is present on the import has a matching existing category
        //-----------------------------------------------------------------
        categoryMatchesAreNotProperlyDefined(categoriesMatchingMap, expenseDTOs);

        //-----------------------------------------------------------------
        // Make sure the matching categories are applied
        //-----------------------------------------------------------------
        List<ExpenseDTO> transformedExpenseDTOs = expenseDTOs
                .stream()
                .map(expenseDTO -> {
                    Optional<CategoryDTO> mappedCategoryDTO = categoriesMatchingMap.get(expenseDTO.getCategory().getName());
                    expenseDTO.setCategory(mappedCategoryDTO.get());

                    return expenseDTO;
                })
                .collect(Collectors.toList());

        return expenseService.bulkCreate(transformedExpenseDTOs, userId);
    }

    private void categoryMatchesAreNotProperlyDefined(Map<String, Optional<CategoryDTO>> categoriesMatchingMap, List<ExpenseDTO> expenseDTOs) throws ExpenseException {
        Map<String, List<ExpenseDTO>> categoriesGroupedPerName = expenseDTOs
                .stream()
                .collect(Collectors.groupingBy(expenseDTO -> expenseDTO.getCategory().getName()));

        boolean allHaveAMatch = categoriesGroupedPerName
                .entrySet()
                .stream()
                .allMatch(matchCategoryEntry -> categoriesMatchingMap.containsKey(matchCategoryEntry.getKey()));

        if (categoriesGroupedPerName.size() != categoriesMatchingMap.size() || !allHaveAMatch) {
            throw new ExpenseException(String.format("Only %s categories defined from total required of %s or not all categories have a match.", categoriesMatchingMap.size(), categoriesGroupedPerName.size()));
        }
    }
}