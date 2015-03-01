package com.revaluate.category.service;

import com.revaluate.category.domain.CategoryDTO;
import com.revaluate.category.exception.CategoryException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface CategoryService {

    boolean isUnique(String name, int userId);

    @NotNull
    CategoryDTO create(@Valid CategoryDTO categoryDTO, int userId) throws CategoryException;

    @NotNull
    CategoryDTO update(@Valid CategoryDTO categoryDTO, int userId) throws CategoryException;

    List<CategoryDTO> findAllCategoriesFor(int userId) throws CategoryException;

    void remove(int categoryId, int userId) throws CategoryException;
}