package com.revaluate.domain.insights;

import com.fasterxml.jackson.annotation.JsonView;
import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.views.Views;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@GeneratePojoBuilder
public class TotalPerCategoryInsightDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotNull
    @JsonView({Views.DetailsView.class})
    private CategoryDTO categoryDTO;

    @NotEmpty
    @JsonView({Views.DetailsView.class})
    private String totalAmountFormatted;

    @NotEmpty
    @JsonView({Views.DetailsView.class})
    private double totalAmount;

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    public String getTotalAmountFormatted() {
        return totalAmountFormatted;
    }

    public void setTotalAmountFormatted(String totalAmountFormatted) {
        this.totalAmountFormatted = totalAmountFormatted;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalPerCategoryInsightDTO that = (TotalPerCategoryInsightDTO) o;
        return Objects.equals(totalAmount, that.totalAmount) &&
                Objects.equals(categoryDTO, that.categoryDTO) &&
                Objects.equals(totalAmountFormatted, that.totalAmountFormatted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryDTO, totalAmountFormatted, totalAmount);
    }

    @Override
    public String toString() {
        return "TotalPerCategoryInsightDTO{" +
                "categoryDTO=" + categoryDTO +
                ", totalAmountFormatted='" + totalAmountFormatted + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}