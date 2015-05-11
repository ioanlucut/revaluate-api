package com.revaluate.domain.importer.profile;

import com.revaluate.domain.importer.column.ExpenseColumn;
import com.revaluate.domain.importer.column.SpendeeColumns;

public class SpendeeExpenseProfileDTO extends ExpenseProfileDTO {

    public static final String SPENDEE_SPENT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'GMT'Z";
    public static final char SPENDEE_DELIMITER = ';';

    public SpendeeExpenseProfileDTO() {
        expenseColumnMatchingMap.put(ExpenseColumn.AMOUNT, SpendeeColumns.AMOUNT.getImportColumnName());
        expenseColumnMatchingMap.put(ExpenseColumn.DESCRIPTION, SpendeeColumns.DESCRIPTION.getImportColumnName());
        expenseColumnMatchingMap.put(ExpenseColumn.SPENT_DATE, SpendeeColumns.SPENT_DATE.getImportColumnName());
        expenseColumnMatchingMap.put(ExpenseColumn.CATEGORY, SpendeeColumns.CATEGORY.getImportColumnName());

        delimiter = SPENDEE_DELIMITER;
        spentDateFormat = SPENDEE_SPENT_DATE_FORMAT;
    }

}