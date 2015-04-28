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
    private List<String> insightData;

    @NotNull
    @JsonView({Views.DetailsView.class})
    private List<String> insightColors;

    @NotNull
    @JsonView({Views.DetailsView.class})
    private List<String> insightLabels;

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

    public List<String> getInsightData() {
        return insightData;
    }

    public void setInsightData(List<String> insightData) {
        this.insightData = insightData;
    }

    public List<String> getInsightColors() {
        return insightColors;
    }

    public void setInsightColors(List<String> insightColors) {
        this.insightColors = insightColors;
    }

    public List<String> getInsightLabels() {
        return insightLabels;
    }

    public void setInsightLabels(List<String> insightLabels) {
        this.insightLabels = insightLabels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InsightDTO that = (InsightDTO) o;
        return Objects.equals(from, that.from) &&
                Objects.equals(to, that.to) &&
                Objects.equals(insightData, that.insightData) &&
                Objects.equals(insightColors, that.insightColors) &&
                Objects.equals(insightLabels, that.insightLabels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, insightData, insightColors, insightLabels);
    }

    @Override
    public String toString() {
        return "InsightDTO{" +
                "from=" + from +
                ", to=" + to +
                ", insightData=" + insightData +
                ", insightColors=" + insightColors +
                ", insightLabels=" + insightLabels +
                '}';
    }
}