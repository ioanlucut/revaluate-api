package com.revaluate.domain.goal;

import com.revaluate.domain.insights.daily.InsightsDailyDTO;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@GeneratePojoBuilder
public class GoalStatusDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @Digits(integer = 20, fraction = 2)
    private double currentValue;

    @NotNull
    private InsightsDailyDTO insightsDaily;

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public InsightsDailyDTO getInsightsDaily() {
        return insightsDaily;
    }

    public void setInsightsDaily(InsightsDailyDTO insightsDaily) {
        this.insightsDaily = insightsDaily;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoalStatusDTO)) return false;
        GoalStatusDTO that = (GoalStatusDTO) o;
        return Objects.equals(currentValue, that.currentValue) &&
                Objects.equals(insightsDaily, that.insightsDaily);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentValue, insightsDaily);
    }

    @Override
    public String toString() {
        return "GoalStatusDTO{" +
                "currentValue=" + currentValue +
                ", insightsDaily=" + insightsDaily +
                '}';
    }
}