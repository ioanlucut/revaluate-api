package com.revaluate.domain.insights;

import com.fasterxml.jackson.annotation.JsonView;
import com.revaluate.views.Views;
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
    @JsonView({Views.DetailsView.class})
    private LocalDateTime from;

    @NotNull
    @JsonView({Views.DetailsView.class})
    private LocalDateTime to;

    @NotNull
    @JsonView({Views.DetailsView.class})
    private List<TotalPerCategoryInsightDTO> totalPerCategoryInsightDTOs;

    private double totalAmountSpent;
    private long numberOfTransactions;
    private long totalNumberOfTransactions;

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

    public long getTotalNumberOfTransactions() {
        return totalNumberOfTransactions;
    }

    public void setTotalNumberOfTransactions(long totalNumberOfTransactions) {
        this.totalNumberOfTransactions = totalNumberOfTransactions;
    }

    public List<TotalPerCategoryInsightDTO> getTotalPerCategoryInsightDTOs() {
        return totalPerCategoryInsightDTOs;
    }

    public void setTotalPerCategoryInsightDTOs(List<TotalPerCategoryInsightDTO> totalPerCategoryInsightDTOs) {
        this.totalPerCategoryInsightDTOs = totalPerCategoryInsightDTOs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InsightDTO that = (InsightDTO) o;
        return Objects.equals(totalAmountSpent, that.totalAmountSpent) &&
                Objects.equals(numberOfTransactions, that.numberOfTransactions) &&
                Objects.equals(totalNumberOfTransactions, that.totalNumberOfTransactions) &&
                Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(totalPerCategoryInsightDTOs, that.totalPerCategoryInsightDTOs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, totalPerCategoryInsightDTOs, totalAmountSpent, numberOfTransactions, totalNumberOfTransactions);
    }

    @Override
    public String toString() {
        return "InsightDTO{" +
                "from=" + from +
                ", to=" + to +
                ", totalPerCategoryInsightDTOs=" + totalPerCategoryInsightDTOs +
                ", totalAmountSpent=" + totalAmountSpent +
                ", numberOfTransactions=" + numberOfTransactions +
                ", totalNumberOfTransactions=" + totalNumberOfTransactions +
                '}';
    }
}