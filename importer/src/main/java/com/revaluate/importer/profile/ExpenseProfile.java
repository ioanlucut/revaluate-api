package com.revaluate.importer.profile;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.List;

public class ExpenseProfile {

    protected final ExpenseProfileEntryDTO descriptionExpenseProfileEntryDTO = new ExpenseProfileEntryDTO(ExpenseColumn.DESCRIPTION);
    protected final ExpenseProfileEntryDTO categoryExpenseProfileEntryDTO = new ExpenseProfileEntryDTO(ExpenseColumn.CATEGORY);
    protected final ExpenseProfileEntryDTO dateExpenseProfileEntryDTO = new ExpenseProfileEntryDTO(ExpenseColumn.SPENT_DATE);
    protected final ExpenseProfileEntryDTO amountExpenseProfileEntryDTO = new ExpenseProfileEntryDTO(ExpenseColumn.AMOUNT);

    protected char delimiter = ',';

    public ExpenseProfileEntryDTO getDescriptionExpenseProfileEntryDTO() {
        return descriptionExpenseProfileEntryDTO;
    }

    public ExpenseProfileEntryDTO getCategoryExpenseProfileEntryDTO() {
        return categoryExpenseProfileEntryDTO;
    }

    public ExpenseProfileEntryDTO getDateExpenseProfileEntryDTO() {
        return dateExpenseProfileEntryDTO;
    }

    public ExpenseProfileEntryDTO getAmountExpenseProfileEntryDTO() {
        return amountExpenseProfileEntryDTO;
    }

    public char getDelimiter() {
        return delimiter;
    }

    public Integer[] getIndexes() {
        return getProfileEntries()
                .stream()
                .map(ExpenseProfileEntryDTO::getImportColumnIndex)
                .toArray(Integer[]::new);
    }

    public Integer getIndexOf(ExpenseColumn expenseColumn) {
        return getProfileEntries().indexOf(
                getProfileEntries()
                        .stream()
                        .filter(expenseProfileEntryDTO -> expenseProfileEntryDTO.getExpenseColumn() == expenseColumn)
                        .findFirst()
                        .get());
    }

    public String[] getFields() {
        return getProfileEntries().stream().map(ExpenseProfileEntryDTO::getImportColumnName).toArray(String[]::new);
    }

    public List<ExpenseProfileEntryDTO> getProfileEntries() {
        return Arrays.asList(
                amountExpenseProfileEntryDTO,
                descriptionExpenseProfileEntryDTO,
                categoryExpenseProfileEntryDTO,
                dateExpenseProfileEntryDTO
        );
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("descriptionExpenseProfileEntryDTO", descriptionExpenseProfileEntryDTO)
                .append("categoryExpenseProfileEntryDTO", categoryExpenseProfileEntryDTO)
                .append("dateExpenseProfileEntryDTO", dateExpenseProfileEntryDTO)
                .append("amountExpenseProfileEntryDTO", amountExpenseProfileEntryDTO)
                .append("delimiter", delimiter)
                .toString();
    }
}