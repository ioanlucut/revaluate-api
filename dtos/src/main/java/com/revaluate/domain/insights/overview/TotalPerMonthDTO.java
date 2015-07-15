package com.revaluate.domain.insights.overview;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Objects;

@GeneratePojoBuilder
public class TotalPerMonthDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotEmpty
    private String monthYearFormattedDate;

    @NotEmpty
    private String totalAmountFormatted;

    @NotEmpty
    private double totalAmount;

    public String getMonthYearFormattedDate() {
        return monthYearFormattedDate;
    }

    public void setMonthYearFormattedDate(String monthYearFormattedDate) {
        this.monthYearFormattedDate = monthYearFormattedDate;
    }

    public String getTotalAmountFormatted() {
        return totalAmountFormatted;
    }

    public void setTotalAmountFormatted(String totalAmountFormatted) {
        this.totalAmountFormatted = totalAmountFormatted;
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
        if (o == null || getClass() != o.getClass()) return false;
        TotalPerMonthDTO that = (TotalPerMonthDTO) o;
        return Objects.equals(totalAmount, that.totalAmount) &&
                Objects.equals(monthYearFormattedDate, that.monthYearFormattedDate) &&
                Objects.equals(totalAmountFormatted, that.totalAmountFormatted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(monthYearFormattedDate, totalAmountFormatted, totalAmount);
    }

    @Override
    public String toString() {
        return "TotalPerMonthDTO{" +
                "monthYearFormattedDate='" + monthYearFormattedDate + '\'' +
                ", totalAmountFormatted='" + totalAmountFormatted + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
