package com.revaluate.domain.insights.statistics;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;
import java.util.Set;

@GeneratePojoBuilder
public class InsightsMonthsPerYearsDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotNull
    private Map<Integer, Set<Integer>> insightsMonthsPerYears;

    public Map<Integer, Set<Integer>> getInsightsMonthsPerYears() {
        return insightsMonthsPerYears;
    }

    public void setInsightsMonthsPerYears(Map<Integer, Set<Integer>> insightsMonthsPerYears) {
        this.insightsMonthsPerYears = insightsMonthsPerYears;
    }

    @Override
    public String toString() {
        return "InsightsMonthsPerYearsDTO{" +
                "insightsMonthsPerYears=" + insightsMonthsPerYears +
                '}';
    }
}
