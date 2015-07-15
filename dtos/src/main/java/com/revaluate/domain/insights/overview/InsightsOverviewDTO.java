package com.revaluate.domain.insights.overview;

import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@GeneratePojoBuilder
public class InsightsOverviewDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotNull
    private List<TotalPerMonthDTO> insightsOverview;

    public List<TotalPerMonthDTO> getInsightsOverview() {
        return insightsOverview;
    }

    public void setInsightsOverview(List<TotalPerMonthDTO> insightsOverview) {
        this.insightsOverview = insightsOverview;
    }

    @Override
    public String toString() {
        return "InsightsOverviewDTO{" +
                "insightsOverview=" + insightsOverview +
                '}';
    }
}
