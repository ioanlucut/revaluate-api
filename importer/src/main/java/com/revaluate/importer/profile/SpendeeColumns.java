package com.revaluate.importer.profile;

public enum SpendeeColumns {

    AMOUNT(ExpenseColumn.AMOUNT, 3, "Amount"),
    DESCRIPTION(ExpenseColumn.DESCRIPTION, 4, "Notes"),
    SPENT_DATE(ExpenseColumn.SPENT_DATE, 2, "Date & Time"),
    CATEGORY(ExpenseColumn.CATEGORY, 1, "Localized Category");

    private ExpenseColumn expenseColumn;
    private int importColumnIndex;
    private String importColumnName;

    SpendeeColumns(ExpenseColumn expenseColumn, int importColumnIndex, String importColumnName) {
        this.expenseColumn = expenseColumn;
        this.importColumnIndex = importColumnIndex;
        this.importColumnName = importColumnName;
    }

    public ExpenseColumn getExpenseColumn() {
        return expenseColumn;
    }

    public int getImportColumnIndex() {
        return importColumnIndex;
    }

    public String getImportColumnName() {
        return importColumnName;
    }
}
