package com.revaluate.domain.color;

import com.fasterxml.jackson.annotation.JsonView;
import com.revaluate.validators.HexColor;
import com.revaluate.views.Views;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@GeneratePojoBuilder
public class ColorDTO {

    @JsonView({Views.StrictView.class})
    private int id;

    @HexColor
    @NotBlank
    @JsonView({Views.StrictView.class})
    private String color;

    @JsonView({Views.StrictView.class})
    private String colorName;

    @NotNull
    @JsonView({Views.StrictView.class})
    private int priority;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorDTO colorDTO = (ColorDTO) o;
        return Objects.equals(id, colorDTO.id) &&
                Objects.equals(priority, colorDTO.priority) &&
                Objects.equals(color, colorDTO.color) &&
                Objects.equals(colorName, colorDTO.colorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, color, colorName, priority);
    }

    @Override
    public String toString() {
        return "ColorDTO{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", colorName='" + colorName + '\'' +
                ", priority=" + priority +
                '}';
    }
}