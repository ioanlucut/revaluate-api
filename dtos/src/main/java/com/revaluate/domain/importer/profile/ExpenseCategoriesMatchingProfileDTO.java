package com.revaluate.domain.importer.profile;

import com.revaluate.domain.category.CategoryDTO;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@GeneratePojoBuilder
public class ExpenseCategoriesMatchingProfileDTO {

    @NotNull
    private Map<String, CategoryDTO> categoriesMatchingMap = new HashMap<>();

    public Map<String, CategoryDTO> getCategoriesMatchingMap() {
        return categoriesMatchingMap;
    }

    public void setCategoriesMatchingMap(Map<String, CategoryDTO> categoriesMatchingMap) {
        this.categoriesMatchingMap = categoriesMatchingMap;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("categoriesMatching", categoriesMatchingMap)
                .toString();
    }
}
