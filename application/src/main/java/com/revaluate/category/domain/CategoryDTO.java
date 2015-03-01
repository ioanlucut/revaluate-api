package com.revaluate.category.domain;

import com.fasterxml.jackson.annotation.JsonView;
import com.revaluate.core.views.Views;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;
import java.io.Serializable;

@GeneratePojoBuilder
public class CategoryDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @JsonView({Views.DetailsView.class})
    private int id;

    @NotBlank
    @Size(min = 2)
    @JsonView({Views.DetailsView.class})
    private String name;

    @NotBlank
    @JsonView({Views.DetailsView.class})
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryDTO that = (CategoryDTO) o;

        if (id != that.id) return false;
        if (color != null ? !color.equals(that.color) : that.color != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}