package com.revaluate.domain.insights.progress;

import com.revaluate.domain.insights.AbstractInsightDTO;
import com.revaluate.domain.insights.monthly.InsightsMonthlyDTO;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@GeneratePojoBuilder
public class ProgressInsightsDTO extends AbstractInsightDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotNull
    private List<InsightsMonthlyDTO> insightsMonthlyDTO;

    public List<InsightsMonthlyDTO> getInsightsMonthlyDTO() {
        return insightsMonthlyDTO;
    }

    public void setInsightsMonthlyDTO(List<InsightsMonthlyDTO> insightsMonthlyDTO) {
        this.insightsMonthlyDTO = insightsMonthlyDTO;
    }

    @Override
    public String toString() {
        return "ProgressInsightsDTO{" +
                ", insightsMonthlyDTO=" + insightsMonthlyDTO +
                "} " + super.toString();
    }
}