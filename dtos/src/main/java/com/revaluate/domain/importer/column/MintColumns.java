package com.revaluate.domain.importer.column;

public enum MintColumns {

    AMOUNT(ExpenseColumn.AMOUNT, "Amount"),
    DESCRIPTION(ExpenseColumn.DESCRIPTION, "Description"),
    SPENT_DATE(ExpenseColumn.SPENT_DATE, "Date"),
    CATEGORY(ExpenseColumn.CATEGORY, "Category");

    private ExpenseColumn expenseColumn;
    private String importColumnName;

    MintColumns(ExpenseColumn expenseColumn, String importColumnName) {
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
