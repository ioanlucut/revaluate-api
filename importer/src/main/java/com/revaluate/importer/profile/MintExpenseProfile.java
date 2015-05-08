package com.revaluate.importer.profile;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class MintExpenseProfile extends ExpenseProfile {

    public MintExpenseProfile() {
        amountExpenseProfileEntryDTO.setImportColumnIndex(MintColumns.AMOUNT.getImportColumnIndex());
        amountExpenseProfileEntryDTO.setImportColumnName(MintColumns.AMOUNT.getImportColumnName());

        descriptionExpenseProfileEntryDTO.setImportColumnIndex(MintColumns.DESCRIPTION.getImportColumnIndex());
        descriptionExpenseProfileEntryDTO.setImportColumnName(MintColumns.DESCRIPTION.getImportColumnName());

        dateExpenseProfileEntryDTO.setImportColumnIndex(MintColumns.SPENT_DATE.getImportColumnIndex());
        dateExpenseProfileEntryDTO.setImportColumnName(MintColumns.SPENT_DATE.getImportColumnName());

        categoryExpenseProfileEntryDTO.setImportColumnIndex(MintColumns.CATEGORY.getImportColumnIndex());
        categoryExpenseProfileEntryDTO.setImportColumnName(MintColumns.CATEGORY.getImportColumnName());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}