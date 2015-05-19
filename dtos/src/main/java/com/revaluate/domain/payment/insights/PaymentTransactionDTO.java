package com.revaluate.domain.payment.insights;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@GeneratePojoBuilder
public class PaymentTransactionDTO {

    @NotBlank
    private String id;

    private double amount;

    @NotBlank
    private String currencyCode;

    @NotBlank
    private boolean recurring;

    @NotBlank
    private String status;

    @NotNull
    private LocalDateTime createdAt;

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

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentTransactionDTO that = (PaymentTransactionDTO) o;
        return Objects.equals(amount, that.amount) &&
                Objects.equals(recurring, that.recurring) &&
                Objects.equals(id, that.id) &&
                Objects.equals(currencyCode, that.currencyCode) &&
                Objects.equals(status, that.status) &&
                Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, currencyCode, recurring, status, createdAt);
    }

    @Override
    public String toString() {
        return "PaymentTransactionDTO{" +
                "id='" + id + '\'' +
                ", amount=" + amount +
                ", currencyCode='" + currencyCode + '\'' +
                ", recurring=" + recurring +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}