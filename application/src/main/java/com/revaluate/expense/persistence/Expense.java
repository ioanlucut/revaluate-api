package com.revaluate.expense.persistence;

import com.revaluate.account.persistence.User;
import com.revaluate.category.persistence.Category;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "expenses")
public class Expense implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;
    public static final String USER_ID = "user_id";
    public static final String CATEGORY_ID = "category_id";

    protected static final String SEQ_NAME = "expenses_id_seq";
    protected static final String SEQ_GENERATOR_NAME = "expenses_seq_generator";
    protected static final int ALLOCATION_SIZE = 1;

    @Id
    @Column(name = "expense_id", updatable = false)
    @SequenceGenerator(name = Expense.SEQ_GENERATOR_NAME,
            sequenceName = Expense.SEQ_NAME,
            allocationSize = Expense.ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    private Integer id;

    @Column(nullable = false, precision = 7, scale = 2)
    @Digits(integer = 9, fraction = 2)
    private BigDecimal value;

    @NotNull
    @Column(nullable = false)
    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = USER_ID, nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = CATEGORY_ID)
    private Category category;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date addedDate;

    @PrePersist
    void createdAt() {
        this.addedDate = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getAddedDate() {
        return addedDate != null ? new Date(addedDate.getTime()) : null;
    }

    public void setAddedDate(Date addedDate) {
        if (addedDate != null) {
            this.addedDate = new Date(addedDate.getTime());
        }
    }
}