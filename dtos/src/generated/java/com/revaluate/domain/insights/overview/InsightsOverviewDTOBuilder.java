package com.revaluate.domain.insights.overview;

import java.util.List;
import javax.annotation.Generated;

@Generated("PojoBuilder")
public class InsightsOverviewDTOBuilder
    implements Cloneable {
  protected InsightsOverviewDTOBuilder self;
  protected List<TotalPerMonthDTO> value$insightsOverview$java$util$List;
  protected boolean isSet$insightsOverview$java$util$List;

  /**
   * Creates a new {@link InsightsOverviewDTOBuilder}.
   */
  public InsightsOverviewDTOBuilder() {
    self = (InsightsOverviewDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link InsightsOverviewDTO#insightsOverview} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsOverviewDTOBuilder withInsightsOverview(List<TotalPerMonthDTO> value) {
    this.value$insightsOverview$java$util$List = value;
    this.isSet$insightsOverview$java$util$List = true;
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
      InsightsOverviewDTOBuilder result = (InsightsOverviewDTOBuilder)super.clone();
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
  public InsightsOverviewDTOBuilder but() {
    return (InsightsOverviewDTOBuilder)clone();
  }

  /**
   * Creates a new {@link InsightsOverviewDTO} based on this builder's settings.
   *
   * @return the created InsightsOverviewDTO
   */
  public InsightsOverviewDTO build() {
    try {
      InsightsOverviewDTO result = new InsightsOverviewDTO();
      if (isSet$insightsOverview$java$util$List) {
        result.setInsightsOverview(value$insightsOverview$java$util$List);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
