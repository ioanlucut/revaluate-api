package com.revaluate.insights.service;

import com.revaluate.domain.insights.overview.InsightsOverviewDTO;
import org.joda.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public interface InsightOverviewService {

    @NotNull
    InsightsOverviewDTO getInsightsOverviewBetween(int userId, @NotNull LocalDateTime after, @NotNull LocalDateTime before);
}