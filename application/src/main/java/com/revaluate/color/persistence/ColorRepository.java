package com.revaluate.color.persistence;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<Color, Integer> {

    String COLORS_CACHE = "colors";

    @Cacheable(COLORS_CACHE)
    Optional<Color> findOneById(int colorId);
}