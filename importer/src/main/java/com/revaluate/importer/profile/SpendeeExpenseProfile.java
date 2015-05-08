package com.revaluate.importer.profile;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SpendeeExpenseProfile extends ExpenseProfile {

    public SpendeeExpenseProfile() {
        amountExpenseProfileEntryDTO.setImportColumnIndex(SpendeeColumns.AMOUNT.getImportColumnIndex());
        amountExpenseProfileEntryDTO.setImportColumnName(SpendeeColumns.AMOUNT.getImportColumnName());

        descriptionExpenseProfileEntryDTO.setImportColumnIndex(SpendeeColumns.DESCRIPTION.getImportColumnIndex());
        descriptionExpenseProfileEntryDTO.setImportColumnName(SpendeeColumns.DESCRIPTION.getImportColumnName());

        dateExpenseProfileEntryDTO.setImportColumnIndex(SpendeeColumns.SPENT_DATE.getImportColumnIndex());
        dateExpenseProfileEntryDTO.setImportColumnName(SpendeeColumns.SPENT_DATE.getImportColumnName());

        categoryExpenseProfileEntryDTO.setImportColumnIndex(SpendeeColumns.CATEGORY.getImportColumnIndex());
        categoryExpenseProfileEntryDTO.setImportColumnName(SpendeeColumns.CATEGORY.getImportColumnName());

        delimiter = ';';
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}