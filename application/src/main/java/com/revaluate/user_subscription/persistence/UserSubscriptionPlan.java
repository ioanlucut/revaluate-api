package com.revaluate.user_subscription.persistence;

import com.revaluate.account.persistence.User;
import com.revaluate.subscription_plan.persistence.SubscriptionPlan;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSubscriptionPlan that = (UserSubscriptionPlan) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(user, that.user) &&
                Objects.equals(subscriptionPlan, that.subscriptionPlan);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, subscriptionPlan);
    }

    @Override
    public String toString() {
        return "UserSubscriptionPlan{" +
                "id=" + id +
                ", user=" + user +
                ", subscriptionPlan=" + subscriptionPlan +
                '}';
    }
}