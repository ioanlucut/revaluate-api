package com.revaluate.domain.expense;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.joda.time.LocalDate;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@GeneratePojoBuilder
public class GroupedExpensesDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotNull
    private List<ExpenseDTO> expenseDTOs;

    @NotNull
    private LocalDate localDate;

    public List<ExpenseDTO> getExpenseDTOs() {
        return expenseDTOs;
    }

    public void setExpenseDTOs(List<ExpenseDTO> expenseDTOs) {
        this.expenseDTOs = expenseDTOs;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GroupedExpensesDTO)) return false;
        GroupedExpensesDTO that = (GroupedExpensesDTO) o;
        return Objects.equals(expenseDTOs, that.expenseDTOs) &&
                Objects.equals(localDate, that.localDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseDTOs, localDate);
    }

    @Override
    public String toString() {
        return "ExpenseGroupDTO{" +
                "expenseDTOs=" + expenseDTOs +
                ", localDate=" + localDate +
                '}';
    }
}