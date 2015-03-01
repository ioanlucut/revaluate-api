package com.revaluate.category.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findOneByNameAndUserId(String name, int userId);

    Optional<Category> findOneByIdAndUserId(int categoryId, int userId);

    List<Category> findAllByUserId(int userId);
}