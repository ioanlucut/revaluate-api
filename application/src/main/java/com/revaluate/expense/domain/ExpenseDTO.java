package com.revaluate.expense.domain;

import com.revaluate.category.domain.CategoryDTO;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@GeneratePojoBuilder
public class ExpenseDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    private int id;

    @Digits(integer = 9, fraction = 2)
    private double value;

    private String description;

    @NotNull
    private CategoryDTO category;

    private Date addedDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpenseDTO that = (ExpenseDTO) o;

        if (id != that.id) return false;
        if (Double.compare(that.value, value) != 0) return false;
        if (addedDate != null ? !addedDate.equals(that.addedDate) : that.addedDate != null) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(value);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (addedDate != null ? addedDate.hashCode() : 0);
        return result;
    }
}