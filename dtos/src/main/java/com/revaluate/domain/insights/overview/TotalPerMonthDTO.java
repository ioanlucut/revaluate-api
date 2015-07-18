package com.revaluate.domain.insights.overview;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.YearMonth;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@GeneratePojoBuilder
public class TotalPerMonthDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotNull
    private YearMonth yearMonth;

    @NotEmpty
    private String totalAmountFormatted;

    @NotEmpty
    private double totalAmount;

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
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
        if (!(o instanceof TotalPerMonthDTO)) return false;
        TotalPerMonthDTO that = (TotalPerMonthDTO) o;
        return Objects.equals(totalAmount, that.totalAmount) &&
                Objects.equals(yearMonth, that.yearMonth) &&
                Objects.equals(totalAmountFormatted, that.totalAmountFormatted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(yearMonth, totalAmountFormatted, totalAmount);
    }

    @Override
    public String toString() {
        return "TotalPerMonthDTO{" +
                "yearMonth=" + yearMonth +
                ", totalAmountFormatted='" + totalAmountFormatted + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
