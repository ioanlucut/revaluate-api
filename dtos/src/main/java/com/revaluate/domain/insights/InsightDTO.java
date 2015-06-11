package com.revaluate.domain.insights;

import com.revaluate.domain.category.CategoryDTO;
import com.revaluate.domain.expense.ExpenseDTO;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@GeneratePojoBuilder
public class InsightDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotNull
    private LocalDateTime from;

    @NotNull
    private LocalDateTime to;

    @NotNull
    private List<TotalPerCategoryInsightDTO> totalPerCategoryInsightDTOs;

    @NotNull
    private CategoryDTO highestAmountCategory;

    @NotNull
    private CategoryWithTheMostTransactionsInsightsDTO categoryWithTheMostTransactionsInsightsDTO;

    @NotNull
    private ExpenseDTO biggestExpense;

    private double totalAmountSpent;
    private long numberOfTransactions;
    private long totalNumberOfTransactions;
    private double differenceBetweenLastMonth;
    private double differencePercentageBetweenLastMonth;

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

    public List<TotalPerCategoryInsightDTO> getTotalPerCategoryInsightDTOs() {
        return totalPerCategoryInsightDTOs;
    }

    public void setTotalPerCategoryInsightDTOs(List<TotalPerCategoryInsightDTO> totalPerCategoryInsightDTOs) {
        this.totalPerCategoryInsightDTOs = totalPerCategoryInsightDTOs;
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

    public long getTotalNumberOfTransactions() {
        return totalNumberOfTransactions;
    }

    public void setTotalNumberOfTransactions(long totalNumberOfTransactions) {
        this.totalNumberOfTransactions = totalNumberOfTransactions;
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
        InsightDTO that = (InsightDTO) o;
        return Objects.equals(totalAmountSpent, that.totalAmountSpent) &&
                Objects.equals(numberOfTransactions, that.numberOfTransactions) &&
                Objects.equals(totalNumberOfTransactions, that.totalNumberOfTransactions) &&
                Objects.equals(differenceBetweenLastMonth, that.differenceBetweenLastMonth) &&
                Objects.equals(differencePercentageBetweenLastMonth, that.differencePercentageBetweenLastMonth) &&
                Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(totalPerCategoryInsightDTOs, that.totalPerCategoryInsightDTOs) &&
                Objects.equals(highestAmountCategory, that.highestAmountCategory) &&
                Objects.equals(categoryWithTheMostTransactionsInsightsDTO, that.categoryWithTheMostTransactionsInsightsDTO) &&
                Objects.equals(biggestExpense, that.biggestExpense);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, totalPerCategoryInsightDTOs, highestAmountCategory, categoryWithTheMostTransactionsInsightsDTO, biggestExpense, totalAmountSpent, numberOfTransactions, totalNumberOfTransactions, differenceBetweenLastMonth, differencePercentageBetweenLastMonth);
    }

    @Override
    public String toString() {
        return "InsightDTO{" +
                "from=" + from +
                ", to=" + to +
                ", totalPerCategoryInsightDTOs=" + totalPerCategoryInsightDTOs +
                ", highestAmountCategory=" + highestAmountCategory +
                ", categoryWithTheMostTransactionsInsightsDTO=" + categoryWithTheMostTransactionsInsightsDTO +
                ", biggestExpense=" + biggestExpense +
                ", totalAmountSpent=" + totalAmountSpent +
                ", numberOfTransactions=" + numberOfTransactions +
                ", totalNumberOfTransactions=" + totalNumberOfTransactions +
                ", differenceBetweenLastMonth=" + differenceBetweenLastMonth +
                ", differencePercentageBetweenLastMonth=" + differencePercentageBetweenLastMonth +
                '}';
    }
}