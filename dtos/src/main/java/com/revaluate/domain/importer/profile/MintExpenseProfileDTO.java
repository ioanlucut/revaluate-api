package com.revaluate.domain.importer.profile;

import com.revaluate.domain.importer.column.MintColumns;

public class MintExpenseProfileDTO extends ExpenseProfileDTO {

    public MintExpenseProfileDTO() {
        amountExpenseProfileEntryDTO.setImportColumnIndex(MintColumns.AMOUNT.getImportColumnIndex());
        amountExpenseProfileEntryDTO.setImportColumnName(MintColumns.AMOUNT.getImportColumnName());

        descriptionExpenseProfileEntryDTO.setImportColumnIndex(MintColumns.DESCRIPTION.getImportColumnIndex());
        descriptionExpenseProfileEntryDTO.setImportColumnName(MintColumns.DESCRIPTION.getImportColumnName());

        dateExpenseProfileEntryDTO.setImportColumnIndex(MintColumns.SPENT_DATE.getImportColumnIndex());
        dateExpenseProfileEntryDTO.setImportColumnName(MintColumns.SPENT_DATE.getImportColumnName());

        categoryExpenseProfileEntryDTO.setImportColumnIndex(MintColumns.CATEGORY.getImportColumnIndex());
        categoryExpenseProfileEntryDTO.setImportColumnName(MintColumns.CATEGORY.getImportColumnName());

        spentDateFormat = "dd/MM/YYY";
    }
}