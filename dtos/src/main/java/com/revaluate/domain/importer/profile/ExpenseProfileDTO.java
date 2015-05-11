package com.revaluate.domain.importer.profile;

import com.revaluate.domain.importer.column.ExpenseColumn;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@GeneratePojoBuilder
public class ExpenseProfileDTO {

    @Valid
    @NotNull
    protected Map<ExpenseColumn, String> expenseColumnMatchingMap = new HashMap<>();

    protected char delimiter = ',';
    protected String spentDateFormat = "";

    public Map<ExpenseColumn, String> getExpenseColumnMatchingMap() {
        return expenseColumnMatchingMap;
    }

    public void setExpenseColumnMatchingMap(Map<ExpenseColumn, String> expenseColumnMatchingMap) {
        this.expenseColumnMatchingMap = expenseColumnMatchingMap;
    }

    public void setDelimiter(char delimiter) {
        this.delimiter = delimiter;
    }

    public void setSpentDateFormat(String spentDateFormat) {
        this.spentDateFormat = spentDateFormat;
    }

    public char getDelimiter() {
        return delimiter;
    }

    public String getSpentDateFormat() {
        return spentDateFormat;
    }

    public String[] getFields() {

        return getEntriesStream()
                .map(Map.Entry::getValue)
                .toArray(String[]::new);
    }

    Stream<Map.Entry<ExpenseColumn, String>> getEntriesStream() {
        return expenseColumnMatchingMap
                .entrySet()
                .stream()
                .sorted((o1, o2) -> o1.getKey().compareTo(o2.getKey()));
    }

    public String getIndexOf(ExpenseColumn expenseColumn) throws Exception {

        return expenseColumnMatchingMap.get(expenseColumn);
    }
}