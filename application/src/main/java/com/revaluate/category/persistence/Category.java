package com.revaluate.category.persistence;

import com.revaluate.account.persistence.User;
import com.revaluate.color.persistence.Color;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "categories",
        uniqueConstraints = @UniqueConstraint(columnNames = {Category.USER_ID, Category.CATEGORY_NAME}),
        indexes = {
                @Index(name = Category.IX_CATEGORY_NAME_USER_ID, columnList = "user_id,name")
        }
)
public class Category implements Serializable {

    public static final String IX_CATEGORY_NAME_USER_ID = "IX_CATEGORY_NAME_USER_ID";
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "name";
    public static final String USER_ID = "user_id";
    public static final String COLOR_ID = "color_id";
    protected static final String SEQ_NAME = "categories_id_seq";
    protected static final String SEQ_GENERATOR_NAME = "categories_seq_generator";
    protected static final int ALLOCATION_SIZE = 1;
    private static final long serialVersionUID = -1799428438852023627L;

    @Id
    @Column(name = CATEGORY_ID, updatable = false)
    @SequenceGenerator(name = Category.SEQ_GENERATOR_NAME,
            sequenceName = Category.SEQ_NAME,
            allocationSize = Category.ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    private Integer id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = COLOR_ID, nullable = false)
    private Color color;

    @ManyToOne(optional = false)
    @JoinColumn(name = USER_ID, nullable = false)
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color=" + color +
                ", user=" + user +
                '}';
    }
}