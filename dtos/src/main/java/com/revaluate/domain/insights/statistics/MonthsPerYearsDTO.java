package com.revaluate.domain.insights.statistics;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@GeneratePojoBuilder
public class MonthsPerYearsDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotNull
    private Map<Integer, Set<Integer>> monthsPerYears;

    public Map<Integer, Set<Integer>> getMonthsPerYears() {
        return monthsPerYears;
    }

    public void setMonthsPerYears(Map<Integer, Set<Integer>> monthsPerYears) {
        this.monthsPerYears = monthsPerYears;
    }

    @Override
    public String toString() {
        return "InsightsMonthsPerYearsDTO{" +
                "insightsMonthsPerYears=" + monthsPerYears +
                '}';
    }
}
