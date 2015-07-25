package com.revaluate.insights.service;

import com.revaluate.domain.insights.progress.ProgressInsightsDTO;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public interface ProgressInsightsService {

    @NotNull
    ProgressInsightsDTO fetchProgressInsightsBetween(int userId, @NotNull LocalDateTime after, @NotNull LocalDateTime before);
}