package com.revaluate.subscription_plan.persistence;

import com.revaluate.domain.subscription.SubscriptionType;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@SequenceGenerator(name = SubscriptionPlan.SEQ_GENERATOR_NAME, sequenceName = SubscriptionPlan.SEQ_NAME, initialValue = SubscriptionPlan.SEQ_INITIAL_VALUE, allocationSize = SubscriptionPlan.ALLOCATION_SIZE)
@Table(name = "subscription_plan")
public class SubscriptionPlan implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    protected static final String SEQ_NAME = "subscription_plan_id_seq";
    protected static final String SEQ_GENERATOR_NAME = "subscription_plan_seq_generator";
    protected static final int SEQ_INITIAL_VALUE = 1;
    protected static final int ALLOCATION_SIZE = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_GENERATOR_NAME)
    private Integer id;

    @Column(nullable = false, precision = 10, scale = 2)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal value;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private SubscriptionType subscriptionType;

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

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    @Override
    public String toString() {
        return "SubscriptionPlan{" +
                "id=" + id +
                ", value=" + value +
                ", description='" + description + '\'' +
                ", subscriptionType=" + subscriptionType +
                '}';
    }
}