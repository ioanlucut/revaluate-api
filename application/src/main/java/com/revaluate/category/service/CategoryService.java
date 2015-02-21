package com.revaluate.category.service;

import com.revaluate.category.domain.CategoryDTO;
import com.revaluate.category.exception.CategoryException;

public interface CategoryService {

    boolean isUnique(String name);

    CategoryDTO create(CategoryDTO categoryDTO, int userId) throws CategoryException;

    void remove(int categoryId, int userId) throws CategoryException;
}