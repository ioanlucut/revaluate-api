package com.revaluate.goals.persistence;

import com.revaluate.account.persistence.User;
import com.revaluate.category.persistence.Category;
import com.revaluate.domain.goal.GoalTarget;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "goals",
        indexes = {
                @Index(name = Goal.IX_GOAL_MULTI_COLUMN_INDEX, columnList = "user_id,category_id,startDate,endDate")
        }
)
@EntityListeners({AuditingEntityListener.class})
public class Goal implements Serializable {

    public static final String IX_GOAL_MULTI_COLUMN_INDEX = "IX_GOAL_MULTI_COLUMN_INDEX";
    public static final String USER_ID = "user_id";
    public static final String CATEGORY_ID = "category_id";
    protected static final String SEQ_NAME = "goals_id_seq";
    protected static final String SEQ_GENERATOR_NAME = "goals_seq_generator";
    protected static final int ALLOCATION_SIZE = 1;
    private static final long serialVersionUID = -1799428438852023627L;

    @Id
    @Column(name = "goal_id", updatable = false)
    @SequenceGenerator(name = Goal.SEQ_GENERATOR_NAME,
            sequenceName = Goal.SEQ_NAME,
            allocationSize = Goal.ALLOCATION_SIZE)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    private Integer id;

    @Column(nullable = false, precision = 20, scale = 2)
    @Digits(integer = 20, fraction = 2)
    private BigDecimal value;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GoalTarget goalTarget;

    @ManyToOne(optional = false)
    @JoinColumn(name = USER_ID, nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = CATEGORY_ID, nullable = false)
    private Category category;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime startDate;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime endDate;

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

    public GoalTarget getGoalTarget() {
        return goalTarget;
    }

    public void setGoalTarget(GoalTarget goalTarget) {
        this.goalTarget = goalTarget;
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
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
        return "Goal{" +
                "id=" + id +
                ", value=" + value +
                ", goalTarget=" + goalTarget +
                ", user=" + user +
                ", category=" + category +
                ", fromDate=" + startDate +
                ", toDate=" + endDate +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}