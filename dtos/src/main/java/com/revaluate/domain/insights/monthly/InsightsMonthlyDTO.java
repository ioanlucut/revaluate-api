package com.revaluate.domain.insights.monthly;

import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.expense.ExpenseDTO;
import com.revaluate.domain.insights.AbstractInsightDTO;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@GeneratePojoBuilder
public class InsightsMonthlyDTO extends AbstractInsightDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotNull
    private List<TotalPerCategoryInsightsDTO> totalPerCategoryInsightsDTOs;

    @NotNull
    private CategoryDTO highestAmountCategory;

    @NotNull
    private CategoryWithTheMostTransactionsInsightsDTO categoryWithTheMostTransactionsInsightsDTO;

    @NotNull
    private ExpenseDTO biggestExpense;

    private double differenceBetweenLastMonth;
    private double differencePercentageBetweenLastMonth;

    public List<TotalPerCategoryInsightsDTO> getTotalPerCategoryInsightsDTOs() {
        return totalPerCategoryInsightsDTOs;
    }

    public void setTotalPerCategoryInsightsDTOs(List<TotalPerCategoryInsightsDTO> totalPerCategoryInsightsDTOs) {
        this.totalPerCategoryInsightsDTOs = totalPerCategoryInsightsDTOs;
    }

    public CategoryDTO getHighestAmountCategory() {
        return highestAmountCategory;
    }

    public void setHighestAmountCategory(CategoryDTO highestAmountCategory) {
        this.highestAmountCategory = highestAmountCategory;
    }

    public CategoryWithTheMostTransactionsInsightsDTO getCategoryWithTheMostTransactionsInsightsDTO() {
        return categoryWithTheMostTransactionsInsightsDTO;
    }

    public void setCategoryWithTheMostTransactionsInsightsDTO(CategoryWithTheMostTransactionsInsightsDTO categoryWithTheMostTransactionsInsightsDTO) {
        this.categoryWithTheMostTransactionsInsightsDTO = categoryWithTheMostTransactionsInsightsDTO;
    }

    public ExpenseDTO getBiggestExpense() {
        return biggestExpense;
    }

    public void setBiggestExpense(ExpenseDTO biggestExpense) {
        this.biggestExpense = biggestExpense;
    }

    public double getDifferenceBetweenLastMonth() {
        return differenceBetweenLastMonth;
    }

    public void setDifferenceBetweenLastMonth(double differenceBetweenLastMonth) {
        this.differenceBetweenLastMonth = differenceBetweenLastMonth;
    }

    public double getDifferencePercentageBetweenLastMonth() {
        return differencePercentageBetweenLastMonth;
    }

    public void setDifferencePercentageBetweenLastMonth(double differencePercentageBetweenLastMonth) {
        this.differencePercentageBetweenLastMonth = differencePercentageBetweenLastMonth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InsightsMonthlyDTO that = (InsightsMonthlyDTO) o;
        return Objects.equals(totalAmountSpent, that.totalAmountSpent) &&
                Objects.equals(numberOfTransactions, that.numberOfTransactions) &&
                Objects.equals(differenceBetweenLastMonth, that.differenceBetweenLastMonth) &&
                Objects.equals(differencePercentageBetweenLastMonth, that.differencePercentageBetweenLastMonth) &&
                Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(totalPerCategoryInsightsDTOs, that.totalPerCategoryInsightsDTOs) &&
                Objects.equals(highestAmountCategory, that.highestAmountCategory) &&
                Objects.equals(categoryWithTheMostTransactionsInsightsDTO, that.categoryWithTheMostTransactionsInsightsDTO) &&
                Objects.equals(biggestExpense, that.biggestExpense);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, totalPerCategoryInsightsDTOs, highestAmountCategory, categoryWithTheMostTransactionsInsightsDTO, biggestExpense, totalAmountSpent, numberOfTransactions, differenceBetweenLastMonth, differencePercentageBetweenLastMonth);
    }

    @Override
    public String toString() {
        return "InsightsMonthlyDTO{" +
                "from=" + from +
                ", to=" + to +
                ", totalPerCategoryInsightsDTOs=" + totalPerCategoryInsightsDTOs +
                ", highestAmountCategory=" + highestAmountCategory +
                ", categoryWithTheMostTransactionsInsightsDTO=" + categoryWithTheMostTransactionsInsightsDTO +
                ", biggestExpense=" + biggestExpense +
                ", totalAmountSpent=" + totalAmountSpent +
                ", numberOfTransactions=" + numberOfTransactions +
                ", differenceBetweenLastMonth=" + differenceBetweenLastMonth +
                ", differencePercentageBetweenLastMonth=" + differencePercentageBetweenLastMonth +
                '}';
    }
}