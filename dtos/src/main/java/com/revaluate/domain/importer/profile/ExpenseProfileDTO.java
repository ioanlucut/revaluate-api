package com.revaluate.domain.importer.profile;

import com.revaluate.domain.importer.column.ExpenseColumn;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@GeneratePojoBuilder
public class ExpenseProfileDTO {

    @Valid
    @NotNull
    protected ExpenseProfileEntryDTO descriptionExpenseProfileEntryDTO;

    @Valid
    @NotNull
    protected ExpenseProfileEntryDTO categoryExpenseProfileEntryDTO;

    @Valid
    @NotNull
    protected ExpenseProfileEntryDTO dateExpenseProfileEntryDTO;

    @Valid
    @NotNull
    protected ExpenseProfileEntryDTO amountExpenseProfileEntryDTO;

    protected char delimiter = ',';
    protected String spentDateFormat = "";

    public ExpenseProfileDTO() {
        //-----------------------------------------------------------------
        // Columns profiles
        //-----------------------------------------------------------------
        descriptionExpenseProfileEntryDTO = new ExpenseProfileEntryDTOBuilder().withExpenseColumn(ExpenseColumn.DESCRIPTION).build();
        categoryExpenseProfileEntryDTO = new ExpenseProfileEntryDTOBuilder().withExpenseColumn(ExpenseColumn.CATEGORY).build();
        dateExpenseProfileEntryDTO = new ExpenseProfileEntryDTOBuilder().withExpenseColumn(ExpenseColumn.SPENT_DATE).build();
        amountExpenseProfileEntryDTO = new ExpenseProfileEntryDTOBuilder().withExpenseColumn(ExpenseColumn.AMOUNT).build();
    }

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

    public String getSpentDateFormat() {
        return spentDateFormat;
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
                .append("spentDateFormat", spentDateFormat)
                .toString();
    }
}