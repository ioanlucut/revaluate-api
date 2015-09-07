package com.revaluate.domain.insights.statistics;

import java.util.Map;
import java.util.Set;
import javax.annotation.Generated;

@Generated("PojoBuilder")
public class MonthsPerYearsDTOBuilder
    implements Cloneable {
  protected MonthsPerYearsDTOBuilder self;
  protected Map<Integer, Set<Integer>> value$monthsPerYears$java$util$Map;
  protected boolean isSet$monthsPerYears$java$util$Map;

  /**
   * Creates a new {@link MonthsPerYearsDTOBuilder}.
   */
  public MonthsPerYearsDTOBuilder() {
    self = (MonthsPerYearsDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link MonthsPerYearsDTO#monthsPerYears} property.
   *
   * @param value the default value
   * @return this builder
   */
  public MonthsPerYearsDTOBuilder withMonthsPerYears(Map<Integer, Set<Integer>> value) {
    this.value$monthsPerYears$java$util$Map = value;
    this.isSet$monthsPerYears$java$util$Map = true;
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
      MonthsPerYearsDTOBuilder result = (MonthsPerYearsDTOBuilder)super.clone();
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
  public MonthsPerYearsDTOBuilder but() {
    return (MonthsPerYearsDTOBuilder)clone();
  }

  /**
   * Creates a new {@link MonthsPerYearsDTO} based on this builder's settings.
   *
   * @return the created MonthsPerYearsDTO
   */
  public MonthsPerYearsDTO build() {
    try {
      MonthsPerYearsDTO result = new MonthsPerYearsDTO();
      if (isSet$monthsPerYears$java$util$Map) {
        result.setMonthsPerYears(value$monthsPerYears$java$util$Map);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
