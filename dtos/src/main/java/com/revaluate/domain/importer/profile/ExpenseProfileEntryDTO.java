package com.revaluate.domain.importer.profile;

import com.revaluate.domain.importer.column.ExpenseColumn;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
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

    public ExpenseColumn getExpenseColumn() {
        return expenseColumn;
    }

    public void setExpenseColumn(ExpenseColumn expenseColumn) {
        this.expenseColumn = expenseColumn;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("expenseColumn", expenseColumn)
                .append("importColumnIndex", importColumnIndex)
                .append("importColumnName", importColumnName)
                .toString();
    }
}
