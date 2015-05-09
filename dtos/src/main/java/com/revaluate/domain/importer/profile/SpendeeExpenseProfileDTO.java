package com.revaluate.domain.importer.profile;

import com.revaluate.domain.importer.column.SpendeeColumns;

public class SpendeeExpenseProfileDTO extends ExpenseProfileDTO {

    public SpendeeExpenseProfileDTO() {
        amountExpenseProfileEntryDTO.setImportColumnIndex(SpendeeColumns.AMOUNT.getImportColumnIndex());
        amountExpenseProfileEntryDTO.setImportColumnName(SpendeeColumns.AMOUNT.getImportColumnName());

        descriptionExpenseProfileEntryDTO.setImportColumnIndex(SpendeeColumns.DESCRIPTION.getImportColumnIndex());
        descriptionExpenseProfileEntryDTO.setImportColumnName(SpendeeColumns.DESCRIPTION.getImportColumnName());

        dateExpenseProfileEntryDTO.setImportColumnIndex(SpendeeColumns.SPENT_DATE.getImportColumnIndex());
        dateExpenseProfileEntryDTO.setImportColumnName(SpendeeColumns.SPENT_DATE.getImportColumnName());

        categoryExpenseProfileEntryDTO.setImportColumnIndex(SpendeeColumns.CATEGORY.getImportColumnIndex());
        categoryExpenseProfileEntryDTO.setImportColumnName(SpendeeColumns.CATEGORY.getImportColumnName());

        delimiter = ';';
        spentDateFormat = "yyyy-MM-dd'T'HH:mm:ss'GMT'Z";
    }

}