package com.revaluate.category.domain;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

@GeneratePojoBuilder
public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private String color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}