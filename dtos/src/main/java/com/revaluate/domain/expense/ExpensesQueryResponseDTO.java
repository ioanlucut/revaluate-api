package com.revaluate.domain.expense;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@GeneratePojoBuilder
public class ExpensesQueryResponseDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    private int currentPage;

    private int currentSize;

    private int totalSize;

    @NotNull
    private List<GroupedExpensesDTO> groupedExpensesDTOList;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<GroupedExpensesDTO> getGroupedExpensesDTOList() {
        return groupedExpensesDTOList;
    }

    public void setGroupedExpensesDTOList(List<GroupedExpensesDTO> groupedExpensesDTOList) {
        this.groupedExpensesDTOList = groupedExpensesDTOList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExpensesQueryResponseDTO)) return false;
        ExpensesQueryResponseDTO that = (ExpensesQueryResponseDTO) o;
        return Objects.equals(currentPage, that.currentPage) &&
                Objects.equals(currentSize, that.currentSize) &&
                Objects.equals(totalSize, that.totalSize) &&
                Objects.equals(groupedExpensesDTOList, that.groupedExpensesDTOList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentPage, currentSize, totalSize, groupedExpensesDTOList);
    }

    @Override
    public String toString() {
        return "ExpensesDTO{" +
                "currentPage=" + currentPage +
                ", currentSize=" + currentSize +
                ", totalSize=" + totalSize +
                ", groupedExpensesDTOList=" + groupedExpensesDTOList +
                '}';
    }
}