package com.revaluate.domain.insights.daily;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.MonthDay;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@GeneratePojoBuilder
public class TotalPerDayDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotNull
    private MonthDay monthDay;

    @NotEmpty
    private double totalAmount;

    public MonthDay getMonthDay() {
        return monthDay;
    }

    public void setMonthDay(MonthDay monthDay) {
        this.monthDay = monthDay;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TotalPerDayDTO)) return false;
        TotalPerDayDTO that = (TotalPerDayDTO) o;
        return Objects.equals(totalAmount, that.totalAmount) &&
                Objects.equals(monthDay, that.monthDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(monthDay, totalAmount);
    }

    @Override
    public String toString() {
        return "TotalPerMonthDTO{" +
                "yearMonth=" + monthDay +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
