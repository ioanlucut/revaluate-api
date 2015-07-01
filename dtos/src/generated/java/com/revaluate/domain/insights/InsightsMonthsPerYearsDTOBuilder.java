package com.revaluate.domain.insights;

import java.util.Map;
import java.util.Set;
import javax.annotation.Generated;

@Generated("PojoBuilder")
public class InsightsMonthsPerYearsDTOBuilder
    implements Cloneable {
  protected InsightsMonthsPerYearsDTOBuilder self;
  protected Map<Integer, Set<Integer>> value$insightsMonthsPerYears$java$util$Map;
  protected boolean isSet$insightsMonthsPerYears$java$util$Map;

  /**
   * Creates a new {@link InsightsMonthsPerYearsDTOBuilder}.
   */
  public InsightsMonthsPerYearsDTOBuilder() {
    self = (InsightsMonthsPerYearsDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link InsightsMonthsPerYearsDTO#insightsMonthsPerYears} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsMonthsPerYearsDTOBuilder withInsightsMonthsPerYears(Map<Integer, Set<Integer>> value) {
    this.value$insightsMonthsPerYears$java$util$Map = value;
    this.isSet$insightsMonthsPerYears$java$util$Map = true;
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
      InsightsMonthsPerYearsDTOBuilder result = (InsightsMonthsPerYearsDTOBuilder)super.clone();
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
  public InsightsMonthsPerYearsDTOBuilder but() {
    return (InsightsMonthsPerYearsDTOBuilder)clone();
  }

  /**
   * Creates a new {@link InsightsMonthsPerYearsDTO} based on this builder's settings.
   *
   * @return the created InsightsMonthsPerYearsDTO
   */
  public InsightsMonthsPerYearsDTO build() {
    try {
      InsightsMonthsPerYearsDTO result = new InsightsMonthsPerYearsDTO();
      if (isSet$insightsMonthsPerYears$java$util$Map) {
        result.setInsightsMonthsPerYears(value$insightsMonthsPerYears$java$util$Map);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
