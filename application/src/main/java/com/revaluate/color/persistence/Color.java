package com.revaluate.color.persistence;

import com.revaluate.validators.HexColor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "color")
public class Color implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    protected static final String SEQ_NAME = "colors_id_seq";
    protected static final String SEQ_GENERATOR_NAME = "colors_seq_generator";
    protected static final int SEQ_INITIAL_VALUE = 1;

    @Id
    @SequenceGenerator(name = Color.SEQ_GENERATOR_NAME,
            sequenceName = Color.SEQ_NAME,
            initialValue = Color.SEQ_INITIAL_VALUE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    private Integer id;

    public Integer getId() {
        return id;
    }

    @NotNull
    @HexColor
    @Column(nullable = false, unique = true)
    private String color;

    private String colorName;

    @NotNull
    @Column(nullable = false, unique = true)
    private Integer priority;

    public void setId(Integer id) {
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Color{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", colorName='" + colorName + '\'' +
                ", priority=" + priority +
                '}';
    }
}