package com.revaluate.domain.importer.profile;

import com.revaluate.domain.importer.column.ExpenseColumn;
import com.revaluate.domain.importer.column.MintColumns;

public class MintExpenseProfileDTO extends ExpenseProfileDTO {

    public static final String MINT_SPENT_DATE_FORMAT = "dd/MM/YYY";

    public MintExpenseProfileDTO() {
        expenseColumnMatchingMap.put(ExpenseColumn.AMOUNT, MintColumns.AMOUNT.getImportColumnName());
        expenseColumnMatchingMap.put(ExpenseColumn.DESCRIPTION, MintColumns.DESCRIPTION.getImportColumnName());
        expenseColumnMatchingMap.put(ExpenseColumn.SPENT_DATE, MintColumns.SPENT_DATE.getImportColumnName());
        expenseColumnMatchingMap.put(ExpenseColumn.CATEGORY, MintColumns.CATEGORY.getImportColumnName());
        spentDateFormat = MINT_SPENT_DATE_FORMAT;
    }
}