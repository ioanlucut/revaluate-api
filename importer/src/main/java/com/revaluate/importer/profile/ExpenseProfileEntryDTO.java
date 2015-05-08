package com.revaluate.importer.profile;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@GeneratePojoBuilder
public class ExpenseProfileEntryDTO {

    @NotNull
    private ExpenseColumn expenseColumn;

    @NotNull
    private int importColumnIndex;

    @NotEmpty
    private String importColumnName;

    public ExpenseProfileEntryDTO() {
    }

    public ExpenseProfileEntryDTO(ExpenseColumn expenseColumn) {
        this.expenseColumn = expenseColumn;
    }

    public ExpenseColumn getExpenseColumn() {
        return expenseColumn;
    }

    public int getImportColumnIndex() {
        return importColumnIndex;
    }

    public void setImportColumnIndex(int importColumnIndex) {
        this.importColumnIndex = importColumnIndex;
    }

    public String getImportColumnName() {
        return importColumnName;
    }

    public void setImportColumnName(String importColumnName) {
        this.importColumnName = importColumnName;
    }
}
