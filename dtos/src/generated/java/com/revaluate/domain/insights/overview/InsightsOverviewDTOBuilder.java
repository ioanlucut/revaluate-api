package com.revaluate.domain.insights.overview;

import java.util.List;
import javax.annotation.Generated;
import org.joda.time.LocalDateTime;

@Generated("PojoBuilder")
public class InsightsOverviewDTOBuilder
    implements Cloneable {
  protected InsightsOverviewDTOBuilder self;
  protected LocalDateTime value$from$org$joda$time$LocalDateTime;
  protected boolean isSet$from$org$joda$time$LocalDateTime;
  protected LocalDateTime value$to$org$joda$time$LocalDateTime;
  protected boolean isSet$to$org$joda$time$LocalDateTime;
  protected double value$totalAmountSpent$double;
  protected boolean isSet$totalAmountSpent$double;
  protected long value$numberOfTransactions$long;
  protected boolean isSet$numberOfTransactions$long;
  protected List<TotalPerMonthDTO> value$insightsOverview$java$util$List;
  protected boolean isSet$insightsOverview$java$util$List;

  /**
   * Creates a new {@link InsightsOverviewDTOBuilder}.
   */
  public InsightsOverviewDTOBuilder() {
    self = (InsightsOverviewDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link InsightsOverviewDTO#from} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsOverviewDTOBuilder withFrom(LocalDateTime value) {
    this.value$from$org$joda$time$LocalDateTime = value;
    this.isSet$from$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightsOverviewDTO#to} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsOverviewDTOBuilder withTo(LocalDateTime value) {
    this.value$to$org$joda$time$LocalDateTime = value;
    this.isSet$to$org$joda$time$LocalDateTime = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightsOverviewDTO#totalAmountSpent} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsOverviewDTOBuilder withTotalAmountSpent(double value) {
    this.value$totalAmountSpent$double = value;
    this.isSet$totalAmountSpent$double = true;
    return self;
  }

  /**
   * Sets the default value for the {@link InsightsOverviewDTO#numberOfTransactions} property.
   *
   * @param value the default value
   * @return this builder
   */
  public InsightsOverviewDTOBuilder withNumberOfTransactions(long value) {
    this.value$numberOfTransactions$long = value;
    this.isSet$numberOfTransactions$long = true;
    return self;
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
      if (isSet$from$org$joda$time$LocalDateTime) {
        result.setFrom(value$from$org$joda$time$LocalDateTime);
      }
      if (isSet$to$org$joda$time$LocalDateTime) {
        result.setTo(value$to$org$joda$time$LocalDateTime);
      }
      if (isSet$totalAmountSpent$double) {
        result.setTotalAmountSpent(value$totalAmountSpent$double);
      }
      if (isSet$numberOfTransactions$long) {
        result.setNumberOfTransactions(value$numberOfTransactions$long);
      }
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
