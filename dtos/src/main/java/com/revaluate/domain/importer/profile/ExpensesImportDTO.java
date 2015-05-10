package com.revaluate.domain.importer.profile;

import com.revaluate.domain.expense.ExpenseDTO;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@GeneratePojoBuilder
public class ExpensesImportDTO {

    public static final int MIN_SIZE_LIST = 1;
    public static final int MAX_SIZE_LIST = 1000;

    @Valid
    @NotNull
    @Size(min = MIN_SIZE_LIST, max = MAX_SIZE_LIST)
    private List<ExpenseDTO> expenseDTOs;

    @Valid
    @NotNull
    @Size(min = MIN_SIZE_LIST, max = MAX_SIZE_LIST)
    private List<ExpenseCategoryMatchingProfileDTO> expenseCategoryMatchingProfileDTOs;

    public List<ExpenseDTO> getExpenseDTOs() {
        return expenseDTOs;
    }

    public void setExpenseDTOs(List<ExpenseDTO> expenseDTOs) {
        this.expenseDTOs = expenseDTOs;
    }

    public List<ExpenseCategoryMatchingProfileDTO> getExpenseCategoryMatchingProfileDTOs() {
        return expenseCategoryMatchingProfileDTOs;
    }

    public void setExpenseCategoryMatchingProfileDTOs(List<ExpenseCategoryMatchingProfileDTO> expenseCategoryMatchingProfileDTOs) {
        this.expenseCategoryMatchingProfileDTOs = expenseCategoryMatchingProfileDTOs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpensesImportDTO that = (ExpensesImportDTO) o;
        return Objects.equals(expenseDTOs, that.expenseDTOs) &&
                Objects.equals(expenseCategoryMatchingProfileDTOs, that.expenseCategoryMatchingProfileDTOs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenseDTOs, expenseCategoryMatchingProfileDTOs);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ExpensesImportDTO{");
        sb.append("expenseDTOs=").append(expenseDTOs);
        sb.append(", expenseCategoryMatchingProfileDTOs=").append(expenseCategoryMatchingProfileDTOs);
        sb.append('}');
        return sb.toString();
    }
}
