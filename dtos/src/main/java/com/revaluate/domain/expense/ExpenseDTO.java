package com.revaluate.domain.expense;

import com.fasterxml.jackson.annotation.JsonView;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.views.Views;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@GeneratePojoBuilder
public class ExpenseDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @JsonView({Views.DetailsView.class})
    private int id;

    @Digits(integer = 10, fraction = 2)
    @JsonView({Views.DetailsView.class})
    private double value;

    @JsonView({Views.DetailsView.class})
    private String description;

    @NotNull
    @JsonView({Views.DetailsView.class})
    private CategoryDTO category;

    @NotNull
    @JsonView({Views.DetailsView.class})
    private LocalDateTime spentDate;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

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

    public LocalDateTime getSpentDate() {
        return spentDate;
    }

    public void setSpentDate(LocalDateTime spentDate) {
        this.spentDate = spentDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExpenseDTO that = (ExpenseDTO) o;

        if (id != that.id) return false;
        if (Double.compare(that.value, value) != 0) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;
        if (createdDate != null ? !createdDate.equals(that.createdDate) : that.createdDate != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (modifiedDate != null ? !modifiedDate.equals(that.modifiedDate) : that.modifiedDate != null) return false;
        if (spentDate != null ? !spentDate.equals(that.spentDate) : that.spentDate != null) return false;

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
        result = 31 * result + (spentDate != null ? spentDate.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (modifiedDate != null ? modifiedDate.hashCode() : 0);
        return result;
    }
}