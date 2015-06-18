package com.revaluate.category.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findOneByNameIgnoreCaseAndUserId(String name, int userId);

    Optional<Category> findOneByIdAndUserId(int categoryId, int userId);

    List<Category> findAllByUserId(int userId);

    @Modifying
    @Transactional
    @Query("delete from Category u where u.user.id = ?1")
    void removeByUserId(int userId);
}