package com.revaluate.domain.payment.insights;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@GeneratePojoBuilder
public class PaymentSubscriptionDTO {

    @NotBlank
    private String id;

    private double amount;

    @NotBlank
    private String currencyCode;

    private String trialDuration;
    private String trialDurationUnit;

    @NotBlank
    private String status;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime billingPeriodEndDate;

    @NotNull
    private LocalDateTime billingPeriodStartDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getTrialDuration() {
        return trialDuration;
    }

    public void setTrialDuration(String trialDuration) {
        this.trialDuration = trialDuration;
    }

    public String getTrialDurationUnit() {
        return trialDurationUnit;
    }

    public void setTrialDurationUnit(String trialDurationUnit) {
        this.trialDurationUnit = trialDurationUnit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getBillingPeriodEndDate() {
        return billingPeriodEndDate;
    }

    public void setBillingPeriodEndDate(LocalDateTime billingPeriodEndDate) {
        this.billingPeriodEndDate = billingPeriodEndDate;
    }

    public LocalDateTime getBillingPeriodStartDate() {
        return billingPeriodStartDate;
    }

    public void setBillingPeriodStartDate(LocalDateTime billingPeriodStartDate) {
        this.billingPeriodStartDate = billingPeriodStartDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentSubscriptionDTO that = (PaymentSubscriptionDTO) o;
        return Objects.equals(amount, that.amount) &&
                Objects.equals(id, that.id) &&
                Objects.equals(currencyCode, that.currencyCode) &&
                Objects.equals(trialDuration, that.trialDuration) &&
                Objects.equals(trialDurationUnit, that.trialDurationUnit) &&
                Objects.equals(status, that.status) &&
                Objects.equals(createdAt, that.createdAt) &&
                Objects.equals(billingPeriodEndDate, that.billingPeriodEndDate) &&
                Objects.equals(billingPeriodStartDate, that.billingPeriodStartDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, currencyCode, trialDuration, trialDurationUnit, status, createdAt, billingPeriodEndDate, billingPeriodStartDate);
    }

    @Override
    public String toString() {
        return "PaymentSubscriptionDTO{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", currencyCode='" + currencyCode + '\'' +
                ", trialDuration='" + trialDuration + '\'' +
                ", trialDurationUnit='" + trialDurationUnit + '\'' +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", billingPeriodEndDate=" + billingPeriodEndDate +
                ", billingPeriodStartDate=" + billingPeriodStartDate +
                '}';
    }
}