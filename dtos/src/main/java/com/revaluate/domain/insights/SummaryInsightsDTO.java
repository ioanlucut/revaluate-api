package com.revaluate.domain.insights;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@GeneratePojoBuilder
public class SummaryInsightsDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotNull
    private LocalDateTime firstExistingExpenseDate;

    @NotNull
    private LocalDateTime lastExistingExpenseDate;

    public LocalDateTime getFirstExistingExpenseDate() {
        return firstExistingExpenseDate;
    }

    public void setFirstExistingExpenseDate(LocalDateTime firstExistingExpenseDate) {
        this.firstExistingExpenseDate = firstExistingExpenseDate;
    }

    public LocalDateTime getLastExistingExpenseDate() {
        return lastExistingExpenseDate;
    }

    public void setLastExistingExpenseDate(LocalDateTime lastExistingExpenseDate) {
        this.lastExistingExpenseDate = lastExistingExpenseDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SummaryInsightsDTO that = (SummaryInsightsDTO) o;
        return Objects.equals(firstExistingExpenseDate, that.firstExistingExpenseDate) &&
                Objects.equals(lastExistingExpenseDate, that.lastExistingExpenseDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstExistingExpenseDate, lastExistingExpenseDate);
    }

    @Override
    public String toString() {
        return "SummaryInsightsDTO{" +
                "firstExistingExpenseDate=" + firstExistingExpenseDate +
                ", lastExistingExpenseDate=" + lastExistingExpenseDate +
                '}';
    }
}
