package com.revaluate.domain.insights;

import com.fasterxml.jackson.annotation.JsonView;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.views.Views;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@GeneratePojoBuilder
public class TotalPerCategoryInsightDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotNull
    @JsonView({Views.DetailsView.class})
    private CategoryDTO categoryDTO;

    @NotNull
    @JsonView({Views.DetailsView.class})
    private String totalAmount;

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalPerCategoryInsightDTO that = (TotalPerCategoryInsightDTO) o;
        return Objects.equals(categoryDTO, that.categoryDTO) &&
                Objects.equals(totalAmount, that.totalAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryDTO, totalAmount);
    }

    @Override
    public String toString() {
        return "TotalPerCategoryInsightDTO{" +
                "categoryDTO=" + categoryDTO +
                ", totalAmount='" + totalAmount + '\'' +
                '}';
    }
}