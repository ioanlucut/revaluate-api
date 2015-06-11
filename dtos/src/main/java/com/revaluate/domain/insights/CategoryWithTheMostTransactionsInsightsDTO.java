package com.revaluate.domain.insights;

import com.revaluate.domain.category.CategoryDTO;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@GeneratePojoBuilder
public class CategoryWithTheMostTransactionsInsightsDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotNull
    private CategoryDTO categoryDTO;

    private int numberOfTransactions;

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    public int getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setNumberOfTransactions(int numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryWithTheMostTransactionsInsightsDTO that = (CategoryWithTheMostTransactionsInsightsDTO) o;
        return Objects.equals(numberOfTransactions, that.numberOfTransactions) &&
                Objects.equals(categoryDTO, that.categoryDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryDTO, numberOfTransactions);
    }

    @Override
    public String toString() {
        return "CategoryWithTheMostTransactionsInsightsDTO{" +
                "categoryDTO=" + categoryDTO +
                ", numberOfTransactions=" + numberOfTransactions +
                '}';
    }
}