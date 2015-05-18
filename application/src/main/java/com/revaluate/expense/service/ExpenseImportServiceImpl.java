package com.revaluate.expense.service;

import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.importer.profile.*;
import com.revaluate.expense.exception.ExpenseException;
import com.revaluate.importer.ImporterException;
import com.revaluate.importer.ImporterParserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Validated
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
                    .withTotalCategoriesFound(groupedByCategoryName.size())
                    .build();
        } catch (ImporterException ex) {

            throw new ExpenseException(ex);
        }
    }

    @Override
    public List<ExpenseDTO> importExpenses(ExpensesImportDTO expensesImportDTO, int userId) throws ExpenseException {
        //-----------------------------------------------------------------
        // Check if every category which is present on the import has a matching existing category
        //-----------------------------------------------------------------
        makeSureAllExpenseCategoriesAreDefined(expensesImportDTO);

        //-----------------------------------------------------------------
        // Make a map transform for faster retrieval. We map the category dto into optional,
        // due to http://stackoverflow.com/questions/24630963/java-8-nullpointerexception-in-collectors-tomap
        //-----------------------------------------------------------------
        Map<String, Optional<CategoryDTO>> categoriesMatchingMap = expensesImportDTO
                .getExpenseCategoryMatchingProfileDTOs()
                .stream()
                .filter(ExpenseCategoryMatchingProfileDTO::isSelected)
                .collect(Collectors.toMap(
                        ExpenseCategoryMatchingProfileDTO::getCategoryCandidateName,
                        expenseCategoryMatchingProfileDTO -> Optional.ofNullable(expenseCategoryMatchingProfileDTO.getCategoryDTO())));

        List<ExpenseDTO> expenseDTOs = expensesImportDTO.getExpenseDTOs();

        //-----------------------------------------------------------------
        // Make sure the matching categories are applied
        //-----------------------------------------------------------------
        List<ExpenseDTO> transformedExpenseDTOs = expenseDTOs
                .stream()
                .filter(expenseDTO -> {
                    Optional<CategoryDTO> categoryDTO = categoriesMatchingMap.get(expenseDTO.getCategory().getName());

                    return categoryDTO != null && categoryDTO.isPresent();
                })
                .map(expenseDTO -> {
                    expenseDTO.setCategory(categoriesMatchingMap.get(expenseDTO.getCategory().getName()).get());

                    return expenseDTO;
                })
                .collect(Collectors.toList());

        return expenseService.bulkCreate(transformedExpenseDTOs, userId);
    }

    private void makeSureAllExpenseCategoriesAreDefined(ExpensesImportDTO expensesImportDTO) throws ExpenseException {
        List<ExpenseDTO> expenseDTOs = expensesImportDTO.getExpenseDTOs();
        List<ExpenseCategoryMatchingProfileDTO> expenseCategoryMatchingProfileDTOs = expensesImportDTO.getExpenseCategoryMatchingProfileDTOs();

        //-----------------------------------------------------------------
        // Any match (even non isSelected)
        //-----------------------------------------------------------------
        if (!expenseDTOs
                .stream()
                .allMatch(expenseDTO -> expenseCategoryMatchingProfileDTOs
                        .stream()
                        .anyMatch(expenseCategoryMatchingProfileDTO -> expenseCategoryMatchingProfileDTO.getCategoryCandidateName().equals(expenseDTO.getCategory().getName())))) {

            throw new ExpenseException("Not all categories have a match.");
        }
    }
}