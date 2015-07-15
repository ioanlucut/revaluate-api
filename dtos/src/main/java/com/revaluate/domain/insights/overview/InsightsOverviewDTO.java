package com.revaluate.domain.insights.overview;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

@GeneratePojoBuilder
public class InsightsOverviewDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotNull
    private Map<String, TotalPerMonthDTO> insightsOverview;

    public Map<String, TotalPerMonthDTO> getInsightsOverview() {
        return insightsOverview;
    }

    public void setInsightsOverview(Map<String, TotalPerMonthDTO> insightsOverview) {
        this.insightsOverview = insightsOverview;
    }

    @Override
    public String toString() {
        return "InsightsOverviewDTO{" +
                "insightsOverview=" + insightsOverview +
                '}';
    }
}
