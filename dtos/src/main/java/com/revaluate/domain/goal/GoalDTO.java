package com.revaluate.domain.goal;

import com.revaluate.domain.category.CategoryDTO;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@GeneratePojoBuilder
public class GoalDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    private int id;

    @Digits(integer = 20, fraction = 2)
    private double value;

    @NotNull
    private GoalTarget goalTarget;

    @NotNull
    private CategoryDTO category;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public GoalTarget getGoalTarget() {
        return goalTarget;
    }

    public void setGoalTarget(GoalTarget goalTarget) {
        this.goalTarget = goalTarget;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoalDTO)) return false;
        GoalDTO goalDTO = (GoalDTO) o;
        return Objects.equals(id, goalDTO.id) &&
                Objects.equals(value, goalDTO.value) &&
                Objects.equals(goalTarget, goalDTO.goalTarget) &&
                Objects.equals(category, goalDTO.category) &&
                Objects.equals(startDate, goalDTO.startDate) &&
                Objects.equals(endDate, goalDTO.endDate) &&
                Objects.equals(createdDate, goalDTO.createdDate) &&
                Objects.equals(modifiedDate, goalDTO.modifiedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, goalTarget, category, startDate, endDate, createdDate, modifiedDate);
    }

    @Override
    public String toString() {
        return "GoalDTO{" +
                "id=" + id +
                ", value=" + value +
                ", goalTarget=" + goalTarget +
                ", category=" + category +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", createdDate=" + createdDate +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
}