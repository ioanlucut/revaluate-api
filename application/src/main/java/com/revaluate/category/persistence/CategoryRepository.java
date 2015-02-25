package com.revaluate.category.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Category findOneByNameAndUserId(String name, int userId);

    Category findOneByIdAndUserId(int categoryId, int userId);
}