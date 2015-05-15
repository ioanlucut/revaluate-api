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

    /**
     * If this category should be ignored.
     */
    private boolean selected = Boolean.TRUE;

    @NotNull
    @Valid
    private CategoryDTO categoryDTO;

    public String getCategoryCandidateName() {
        return categoryCandidateName;
    }

    public void setCategoryCandidateName(String categoryCandidateName) {
        this.categoryCandidateName = categoryCandidateName;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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
        return Objects.equals(selected, that.selected) &&
                Objects.equals(categoryCandidateName, that.categoryCandidateName) &&
                Objects.equals(categoryDTO, that.categoryDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryCandidateName, selected, categoryDTO);
    }

    @Override
    public String toString() {
        return "ExpenseCategoryMatchingProfileDTO{" +
                "categoryCandidateName='" + categoryCandidateName + '\'' +
                ", selected=" + selected +
                ", categoryDTO=" + categoryDTO +
                '}';
    }

}
