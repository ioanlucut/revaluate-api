package com.revaluate.domain.user_subscription;

import com.revaluate.domain.subscription_plan.SubscriptionPlanDTO;
import com.revaluate.domain.subscription_plan.SubscriptionType;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.joda.time.LocalDateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@GeneratePojoBuilder
public class UserSubscriptionPlanDTO {

    @NotNull
    private int id;

    @NotNull
    @Valid
    private SubscriptionPlanDTO subscriptionPlanDTO;

    @NotNull
    private SubscriptionType subscriptionType;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SubscriptionPlanDTO getSubscriptionPlanDTO() {
        return subscriptionPlanDTO;
    }

    public void setSubscriptionPlanDTO(SubscriptionPlanDTO subscriptionPlanDTO) {
        this.subscriptionPlanDTO = subscriptionPlanDTO;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSubscriptionPlanDTO that = (UserSubscriptionPlanDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(subscriptionPlanDTO, that.subscriptionPlanDTO) &&
                Objects.equals(subscriptionType, that.subscriptionType) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(modifiedDate, that.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, subscriptionPlanDTO, subscriptionType, createdDate, modifiedDate);
    }

    @Override
    public String toString() {
        return "UserSubscriptionPlanDTO{" +
                "id=" + id +
                ", subscriptionPlanDTO=" + subscriptionPlanDTO +
                ", subscriptionType=" + subscriptionType +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }

}