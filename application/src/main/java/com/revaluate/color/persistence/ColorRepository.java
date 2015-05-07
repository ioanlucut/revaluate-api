package com.revaluate.color.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<Color, Integer> {

    Optional<Color> findOneByColor(String color);

    Optional<Color> findOneById(int colorId);
}