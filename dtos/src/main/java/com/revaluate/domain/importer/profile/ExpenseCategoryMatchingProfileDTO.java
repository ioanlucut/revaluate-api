package com.revaluate.domain.importer.profile;

import com.revaluate.domain.category.CategoryDTO;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@GeneratePojoBuilder
public class ExpenseCategoryMatchingProfileDTO {

    @NotEmpty
    private String categoryCandidateName;

    @NotNull
    @Valid
    private CategoryDTO categoryDTO;

    public String getCategoryCandidateName() {
        return categoryCandidateName;
    }

    public void setCategoryCandidateName(String categoryCandidateName) {
        this.categoryCandidateName = categoryCandidateName;
    }

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseCategoryMatchingProfileDTO that = (ExpenseCategoryMatchingProfileDTO) o;
        return Objects.equals(categoryCandidateName, that.categoryCandidateName) &&
                Objects.equals(categoryDTO, that.categoryDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryCandidateName, categoryDTO);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExpenseCategoryMatchingProfileDTO{");
        sb.append("categoryCandidateName='").append(categoryCandidateName).append('\'');
        sb.append(", categoryDTO=").append(categoryDTO);
        sb.append('}');
        return sb.toString();
    }
}
