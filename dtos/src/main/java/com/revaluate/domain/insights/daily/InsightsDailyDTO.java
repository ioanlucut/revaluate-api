package com.revaluate.domain.insights.daily;

import com.revaluate.domain.insights.AbstractInsightDTO;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@GeneratePojoBuilder
public class InsightsDailyDTO extends AbstractInsightDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotNull
    private List<TotalPerDayDTO> totalPerDayDTOs;

    public List<TotalPerDayDTO> getTotalPerDayDTOs() {
        return totalPerDayDTOs;
    }

    public void setTotalPerDayDTOs(List<TotalPerDayDTO> totalPerDayDTOs) {
        this.totalPerDayDTOs = totalPerDayDTOs;
    }

    @Override
    public String toString() {
        return "InsightsDailyDTO{" +
                "totalPerDayDTOs=" + totalPerDayDTOs +
                "} " + super.toString();
    }
}
