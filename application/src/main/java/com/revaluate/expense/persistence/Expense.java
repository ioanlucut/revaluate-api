package com.revaluate.expense.persistence;

import com.revaluate.account.persistence.User;
import com.revaluate.category.persistence.Category;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "expenses",
        indexes = {
                @Index(name = Expense.IX_EXPENSE_USER_ID_CATEGORY_ID_SPENT_DATE, columnList = "user_id,category_id,spentDate")
        }
)
@EntityListeners({AuditingEntityListener.class})
public class Expense implements Serializable {

    public static final String IX_EXPENSE_USER_ID_CATEGORY_ID_SPENT_DATE = "IX_EXPENSE_USER_ID_CATEGORY_ID_SPENT_DATE";
    public static final String USER_ID = "user_id";
    public static final String CATEGORY_ID = "category_id";
    protected static final String SEQ_NAME = "expenses_id_seq";
    protected static final String SEQ_GENERATOR_NAME = "expenses_seq_generator";
    protected static final int ALLOCATION_SIZE = 1;
    private static final long serialVersionUID = -1799428438852023627L;

    @Id
    @Column(name = "expense_id", updatable = false)
    @SequenceGenerator(name = Expense.SEQ_GENERATOR_NAME,
            sequenceName = Expense.SEQ_NAME,
            allocationSize = Expense.ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    private Integer id;

    @Column(nullable = false, precision = 20, scale = 2)
    @Digits(integer = 20, fraction = 2)
    private BigDecimal value;

    private String description;

    @ManyToOne(optional = false)
    @JoinColumn(name = USER_ID, nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = CATEGORY_ID, nullable = false)
    private Category category;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime spentDate;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdDate;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime modifiedDate;

    @PrePersist
    void createdAt() {
        this.createdDate = this.modifiedDate = LocalDateTime.now();
    }

    @PreUpdate
    void updatedAt() {
        this.modifiedDate = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public LocalDateTime getSpentDate() {
        return spentDate;
    }

    public void setSpentDate(LocalDateTime spentDate) {
        this.spentDate = spentDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", value=" + value +
                ", description='" + description + '\'' +
                ", user=" + user +
                ", category=" + category +
                ", spentDate=" + spentDate +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}