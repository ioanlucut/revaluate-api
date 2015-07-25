package com.revaluate.domain.insights.overview;

import javax.annotation.Generated;
import org.joda.time.YearMonth;

@Generated("PojoBuilder")
public class TotalPerMonthDTOBuilder
    implements Cloneable {
  protected TotalPerMonthDTOBuilder self;
  protected YearMonth value$yearMonth$org$joda$time$YearMonth;
  protected boolean isSet$yearMonth$org$joda$time$YearMonth;
  protected String value$totalAmountFormatted$java$lang$String;
  protected boolean isSet$totalAmountFormatted$java$lang$String;
  protected double value$totalAmount$double;
  protected boolean isSet$totalAmount$double;

  /**
   * Creates a new {@link TotalPerMonthDTOBuilder}.
   */
  public TotalPerMonthDTOBuilder() {
    self = (TotalPerMonthDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link TotalPerMonthDTO#yearMonth} property.
   *
   * @param value the default value
   * @return this builder
   */
  public TotalPerMonthDTOBuilder withYearMonth(YearMonth value) {
    this.value$yearMonth$org$joda$time$YearMonth = value;
    this.isSet$yearMonth$org$joda$time$YearMonth = true;
    return self;
  }

  /**
   * Sets the default value for the {@link TotalPerMonthDTO#totalAmountFormatted} property.
   *
   * @param value the default value
   * @return this builder
   */
  public TotalPerMonthDTOBuilder withTotalAmountFormatted(String value) {
    this.value$totalAmountFormatted$java$lang$String = value;
    this.isSet$totalAmountFormatted$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link TotalPerMonthDTO#totalAmount} property.
   *
   * @param value the default value
   * @return this builder
   */
  public TotalPerMonthDTOBuilder withTotalAmount(double value) {
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
      TotalPerMonthDTOBuilder result = (TotalPerMonthDTOBuilder)super.clone();
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
  public TotalPerMonthDTOBuilder but() {
    return (TotalPerMonthDTOBuilder)clone();
  }

  /**
   * Creates a new {@link TotalPerMonthDTO} based on this builder's settings.
   *
   * @return the created TotalPerMonthDTO
   */
  public TotalPerMonthDTO build() {
    try {
      TotalPerMonthDTO result = new TotalPerMonthDTO();
      if (isSet$yearMonth$org$joda$time$YearMonth) {
        result.setYearMonth(value$yearMonth$org$joda$time$YearMonth);
      }
      if (isSet$totalAmountFormatted$java$lang$String) {
        result.setTotalAmountFormatted(value$totalAmountFormatted$java$lang$String);
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
