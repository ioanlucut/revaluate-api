package com.revaluate.user_subscription.persistence;

import com.revaluate.account.persistence.User;
import com.revaluate.subscription_plan.persistence.SubscriptionPlan;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@SequenceGenerator(name = UserSubscriptionPlan.SEQ_GENERATOR_NAME, sequenceName = UserSubscriptionPlan.SEQ_NAME, initialValue = UserSubscriptionPlan.SEQ_INITIAL_VALUE, allocationSize = UserSubscriptionPlan.ALLOCATION_SIZE)
@Table(name = "user_subscription_plan")
public class UserSubscriptionPlan implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    public static final String USER_ID = "user_id";
    public static final String SUBSCRIPTION_PLAN_ID = "subscription_plan_id";
    protected static final String SEQ_NAME = "user_subscription_plan_id_seq";
    protected static final String SEQ_GENERATOR_NAME = "user_subscription_plan_seq_generator";
    protected static final int SEQ_INITIAL_VALUE = 1;
    protected static final int ALLOCATION_SIZE = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    private Integer id;

    @OneToOne(optional = false)
    @JoinColumn(name = USER_ID, nullable = false, unique = true)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = SUBSCRIPTION_PLAN_ID, nullable = false)
    private SubscriptionPlan subscriptionPlan;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SubscriptionPlan getSubscriptionPlan() {
        return subscriptionPlan;
    }

    public void setSubscriptionPlan(SubscriptionPlan subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
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
        return "UserSubscriptionPlan{" +
                "id=" + id +
                ", user=" + user +
                ", subscriptionPlan=" + subscriptionPlan +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}