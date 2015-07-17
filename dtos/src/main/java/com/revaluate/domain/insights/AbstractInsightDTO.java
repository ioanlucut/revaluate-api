package com.revaluate.domain.insights;

import org.joda.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public abstract class AbstractInsightDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotNull
    protected LocalDateTime from;

    @NotNull
    protected LocalDateTime to;

    protected double totalAmountSpent;
    protected long numberOfTransactions;

    public LocalDateTime getFrom() {
        return from;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public double getTotalAmountSpent() {
        return totalAmountSpent;
    }

    public void setTotalAmountSpent(double totalAmountSpent) {
        this.totalAmountSpent = totalAmountSpent;
    }

    public long getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setNumberOfTransactions(long numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

    @Override
    public String toString() {
        return "AbstractInsightDTO{" +
                "from=" + from +
                ", to=" + to +
                ", totalAmountSpent=" + totalAmountSpent +
                ", numberOfTransactions=" + numberOfTransactions +
                '}';
    }
}