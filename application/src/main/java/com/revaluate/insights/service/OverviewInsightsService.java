package com.revaluate.insights.service;

import com.revaluate.domain.insights.overview.InsightsOverviewDTO;
import net.bull.javamelody.MonitoredWithSpring;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.NotNull;

@MonitoredWithSpring
public interface OverviewInsightsService {

    @NotNull
    InsightsOverviewDTO getOverviewInsightsBetween(int userId, @NotNull LocalDateTime after, @NotNull LocalDateTime before);
}