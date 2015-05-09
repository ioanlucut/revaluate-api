package com.revaluate.domain.importer.column;

public enum MintColumns {

    AMOUNT(ExpenseColumn.AMOUNT, 3, "Amount"),
    DESCRIPTION(ExpenseColumn.DESCRIPTION, 1, "Description"),
    SPENT_DATE(ExpenseColumn.SPENT_DATE, 0, "Date"),
    CATEGORY(ExpenseColumn.CATEGORY, 5, "Category");

    private ExpenseColumn expenseColumn;
    private int importColumnIndex;
    private String importColumnName;

    MintColumns(ExpenseColumn expenseColumn, int importColumnIndex, String importColumnName) {
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
