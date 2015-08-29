package com.revaluate.domain.goal;

import com.revaluate.domain.insights.daily.InsightsDailyDTO;
import javax.annotation.Generated;

@Generated("PojoBuilder")
public class GoalStatusDTOBuilder
    implements Cloneable {
  protected GoalStatusDTOBuilder self;
  protected double value$currentValue$double;
  protected boolean isSet$currentValue$double;
  protected InsightsDailyDTO value$insightsDaily$com$revaluate$domain$insights$daily$InsightsDailyDTO;
  protected boolean isSet$insightsDaily$com$revaluate$domain$insights$daily$InsightsDailyDTO;

  /**
   * Creates a new {@link GoalStatusDTOBuilder}.
   */
  public GoalStatusDTOBuilder() {
    self = (GoalStatusDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link GoalStatusDTO#currentValue} property.
   *
   * @param value the default value
   * @return this builder
   */
  public GoalStatusDTOBuilder withCurrentValue(double value) {
    this.value$currentValue$double = value;
    this.isSet$currentValue$double = true;
    return self;
  }

  /**
   * Sets the default value for the {@link GoalStatusDTO#insightsDaily} property.
   *
   * @param value the default value
   * @return this builder
   */
  public GoalStatusDTOBuilder withInsightsDaily(InsightsDailyDTO value) {
    this.value$insightsDaily$com$revaluate$domain$insights$daily$InsightsDailyDTO = value;
    this.isSet$insightsDaily$com$revaluate$domain$insights$daily$InsightsDailyDTO = true;
    return self;
  }

  /**
   * Returns a clone of this builder.
   *
   * @return the clone
   */
  @Override
  public Object clone() {
    try {
      GoalStatusDTOBuilder result = (GoalStatusDTOBuilder)super.clone();
      result.self = result;
      return result;
    } catch (CloneNotSupportedException e) {
      throw new InternalError(e.getMessage());
    }
  }

  /**
   * Returns a clone of this builder.
   *
   * @return the clone
   */
  public GoalStatusDTOBuilder but() {
    return (GoalStatusDTOBuilder)clone();
  }

  /**
   * Creates a new {@link GoalStatusDTO} based on this builder's settings.
   *
   * @return the created GoalStatusDTO
   */
  public GoalStatusDTO build() {
    try {
      GoalStatusDTO result = new GoalStatusDTO();
      if (isSet$currentValue$double) {
        result.setCurrentValue(value$currentValue$double);
      }
      if (isSet$insightsDaily$com$revaluate$domain$insights$daily$InsightsDailyDTO) {
        result.setInsightsDaily(value$insightsDaily$com$revaluate$domain$insights$daily$InsightsDailyDTO);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
