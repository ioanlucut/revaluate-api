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
    private String totalAmount;

    @NotEmpty
    @JsonView({Views.DetailsView.class})
    private String label;

    @NotEmpty
    @JsonView({Views.DetailsView.class})
    private String color;

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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TotalPerCategoryInsightDTO that = (TotalPerCategoryInsightDTO) o;
        return Objects.equals(categoryDTO, that.categoryDTO) &&
                Objects.equals(totalAmount, that.totalAmount) &&
                Objects.equals(label, that.label) &&
                Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryDTO, totalAmount, label, color);
    }

    @Override
    public String toString() {
        return "TotalPerCategoryInsightDTO{" +
                "categoryDTO=" + categoryDTO +
                ", totalAmount='" + totalAmount + '\'' +
                ", label='" + label + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}