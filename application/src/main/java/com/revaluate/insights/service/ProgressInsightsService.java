package com.revaluate.insights.service;

import com.revaluate.domain.insights.progress.ProgressInsightsDTO;
import net.bull.javamelody.MonitoredWithSpring;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.NotNull;

@MonitoredWithSpring
public interface ProgressInsightsService {

    @NotNull
    ProgressInsightsDTO fetchProgressInsightsBetween(int userId, @NotNull LocalDateTime after, @NotNull LocalDateTime before);
}