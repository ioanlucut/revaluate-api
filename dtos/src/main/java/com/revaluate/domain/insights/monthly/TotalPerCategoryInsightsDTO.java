package com.revaluate.domain.insights.monthly;

import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.expense.ExpenseDTO;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@GeneratePojoBuilder
public class TotalPerCategoryInsightsDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotNull
    private CategoryDTO categoryDTO;

    @NotNull
    private ExpenseDTO biggestExpense;

    @NotNull
    private int numberOfTransactions;

    @NotEmpty
    private String totalAmountFormatted;

    @NotEmpty
    private double totalAmount;

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    public ExpenseDTO getBiggestExpense() {
        return biggestExpense;
    }

    public void setBiggestExpense(ExpenseDTO biggestExpense) {
        this.biggestExpense = biggestExpense;
    }

    public int getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setNumberOfTransactions(int numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
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
        TotalPerCategoryInsightsDTO that = (TotalPerCategoryInsightsDTO) o;
        return Objects.equals(numberOfTransactions, that.numberOfTransactions) &&
                Objects.equals(totalAmount, that.totalAmount) &&
                Objects.equals(categoryDTO, that.categoryDTO) &&
                Objects.equals(biggestExpense, that.biggestExpense) &&
                Objects.equals(totalAmountFormatted, that.totalAmountFormatted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryDTO, biggestExpense, numberOfTransactions, totalAmountFormatted, totalAmount);
    }

    @Override
    public String toString() {
        return "TotalPerCategoryInsightsDTO{" +
                "categoryDTO=" + categoryDTO +
                ", biggestExpense=" + biggestExpense +
                ", numberOfTransactions=" + numberOfTransactions +
                ", totalAmountFormatted='" + totalAmountFormatted + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}