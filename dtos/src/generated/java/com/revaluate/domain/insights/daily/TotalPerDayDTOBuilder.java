package com.revaluate.domain.insights.daily;

import javax.annotation.Generated;
import org.joda.time.MonthDay;

@Generated("PojoBuilder")
public class TotalPerDayDTOBuilder
    implements Cloneable {
  protected TotalPerDayDTOBuilder self;
  protected MonthDay value$monthDay$org$joda$time$MonthDay;
  protected boolean isSet$monthDay$org$joda$time$MonthDay;
  protected double value$totalAmount$double;
  protected boolean isSet$totalAmount$double;

  /**
   * Creates a new {@link TotalPerDayDTOBuilder}.
   */
  public TotalPerDayDTOBuilder() {
    self = (TotalPerDayDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link TotalPerDayDTO#monthDay} property.
   *
   * @param value the default value
   * @return this builder
   */
  public TotalPerDayDTOBuilder withMonthDay(MonthDay value) {
    this.value$monthDay$org$joda$time$MonthDay = value;
    this.isSet$monthDay$org$joda$time$MonthDay = true;
    return self;
  }

  /**
   * Sets the default value for the {@link TotalPerDayDTO#totalAmount} property.
   *
   * @param value the default value
   * @return this builder
   */
  public TotalPerDayDTOBuilder withTotalAmount(double value) {
    this.value$totalAmount$double = value;
    this.isSet$totalAmount$double = true;
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
      TotalPerDayDTOBuilder result = (TotalPerDayDTOBuilder)super.clone();
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
  public TotalPerDayDTOBuilder but() {
    return (TotalPerDayDTOBuilder)clone();
  }

  /**
   * Creates a new {@link TotalPerDayDTO} based on this builder's settings.
   *
   * @return the created TotalPerDayDTO
   */
  public TotalPerDayDTO build() {
    try {
      TotalPerDayDTO result = new TotalPerDayDTO();
      if (isSet$monthDay$org$joda$time$MonthDay) {
        result.setMonthDay(value$monthDay$org$joda$time$MonthDay);
      }
      if (isSet$totalAmount$double) {
        result.setTotalAmount(value$totalAmount$double);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
