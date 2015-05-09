package com.revaluate.domain.importer;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Objects;

@GeneratePojoBuilder
public class ImportExpenseDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    private String value;
    private String description;
    private String category;
    private String spentDate;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSpentDate() {
        return spentDate;
    }

    public void setSpentDate(String spentDate) {
        this.spentDate = spentDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImportExpenseDTO that = (ImportExpenseDTO) o;
        return Objects.equals(value, that.value) &&
                Objects.equals(description, that.description) &&
                Objects.equals(category, that.category) &&
                Objects.equals(spentDate, that.spentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, description, category, spentDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", value)
                .append("description", description)
                .append("category", category)
                .append("spentDate", spentDate)
                .toString();
    }
}