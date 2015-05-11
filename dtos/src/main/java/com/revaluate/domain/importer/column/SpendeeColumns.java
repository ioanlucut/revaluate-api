package com.revaluate.domain.importer.column;

public enum SpendeeColumns {

    AMOUNT(ExpenseColumn.AMOUNT, "Amount"),
    DESCRIPTION(ExpenseColumn.DESCRIPTION, "Notes"),
    SPENT_DATE(ExpenseColumn.SPENT_DATE, "Date & Time"),
    CATEGORY(ExpenseColumn.CATEGORY, "Localized Category");

    private ExpenseColumn expenseColumn;
    private String importColumnName;

    SpendeeColumns(ExpenseColumn expenseColumn, String importColumnName) {
        this.expenseColumn = expenseColumn;
        this.importColumnName = importColumnName;
    }

    public ExpenseColumn getExpenseColumn() {
        return expenseColumn;
    }

    public String getImportColumnName() {
        return importColumnName;
    }
}
