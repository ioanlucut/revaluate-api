package com.revaluate.category.service;

import com.revaluate.category.exception.CategoryException;
import com.revaluate.domain.category.CategoryDTO;
import net.bull.javamelody.MonitoredWithSpring;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@MonitoredWithSpring
public interface CategoryService {
    int MIN_SIZE_LIST = 1;
    int MAX_SIZE_LIST = 1000;

    boolean isUnique(String name, int userId);

    @NotNull
    CategoryDTO create(@Valid CategoryDTO categoryDTO, int userId) throws CategoryException;

    @NotNull
    List<CategoryDTO> bulkCreate(@Size(min = MIN_SIZE_LIST, max = MAX_SIZE_LIST) @NotNull @Valid List<CategoryDTO> categoryDTOs, int userId) throws CategoryException;

    @NotNull
    List<CategoryDTO> setupBulkCreateCategories(@Size(min = MIN_SIZE_LIST, max = MAX_SIZE_LIST) @NotNull @Valid List<CategoryDTO> categoryDTOs, int userId) throws CategoryException;

    void bulkDelete(@Size(min = MIN_SIZE_LIST, max = MAX_SIZE_LIST) @NotNull @Valid List<CategoryDTO> categoryDTOs, int userId) throws CategoryException;

    @NotNull
    CategoryDTO update(@Valid CategoryDTO categoryDTO, int userId) throws CategoryException;

    List<CategoryDTO> findAllCategoriesFor(int userId);

    void remove(int categoryId, int userId) throws CategoryException;
}