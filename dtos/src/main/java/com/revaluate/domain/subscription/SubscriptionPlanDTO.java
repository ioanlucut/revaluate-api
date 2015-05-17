package com.revaluate.domain.subscription;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@GeneratePojoBuilder
public class SubscriptionPlanDTO {

    @NotNull
    private int id;

    @Digits(integer = 10, fraction = 2)
    private double value;

    private String description;

    @NotNull
    private SubscriptionType subscriptionType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionPlanDTO that = (SubscriptionPlanDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(value, that.value) &&
                Objects.equals(description, that.description) &&
                Objects.equals(subscriptionType, that.subscriptionType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, description, subscriptionType);
    }

    @Override
    public String toString() {
        return "SubscriptionPlanDTO{" +
                "id=" + id +
                ", value=" + value +
                ", description='" + description + '\'' +
                ", subscriptionType=" + subscriptionType +
                '}';
    }
}