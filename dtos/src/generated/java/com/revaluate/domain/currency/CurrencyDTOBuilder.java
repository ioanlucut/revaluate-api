package com.revaluate.domain.currency;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class CurrencyDTOBuilder
    implements Cloneable {
  protected CurrencyDTOBuilder self;
  protected String value$currencyCode$java$lang$String;
  protected boolean isSet$currencyCode$java$lang$String;
  protected String value$displayName$java$lang$String;
  protected boolean isSet$displayName$java$lang$String;
  protected String value$symbol$java$lang$String;
  protected boolean isSet$symbol$java$lang$String;
  protected int value$numericCode$int;
  protected boolean isSet$numericCode$int;

  /**
   * Creates a new {@link CurrencyDTOBuilder}.
   */
  public CurrencyDTOBuilder() {
    self = (CurrencyDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link CurrencyDTO#currencyCode} property.
   *
   * @param value the default value
   * @return this builder
   */
  public CurrencyDTOBuilder withCurrencyCode(String value) {
    this.value$currencyCode$java$lang$String = value;
    this.isSet$currencyCode$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link CurrencyDTO#displayName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public CurrencyDTOBuilder withDisplayName(String value) {
    this.value$displayName$java$lang$String = value;
    this.isSet$displayName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link CurrencyDTO#symbol} property.
   *
   * @param value the default value
   * @return this builder
   */
  public CurrencyDTOBuilder withSymbol(String value) {
    this.value$symbol$java$lang$String = value;
    this.isSet$symbol$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link CurrencyDTO#numericCode} property.
   *
   * @param value the default value
   * @return this builder
   */
  public CurrencyDTOBuilder withNumericCode(int value) {
    this.value$numericCode$int = value;
    this.isSet$numericCode$int = true;
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
      CurrencyDTOBuilder result = (CurrencyDTOBuilder)super.clone();
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
  public CurrencyDTOBuilder but() {
    return (CurrencyDTOBuilder)clone();
  }

  /**
   * Creates a new {@link CurrencyDTO} based on this builder's settings.
   *
   * @return the created CurrencyDTO
   */
  public CurrencyDTO build() {
    try {
      CurrencyDTO result = new CurrencyDTO();
      if (isSet$currencyCode$java$lang$String) {
        result.setCurrencyCode(value$currencyCode$java$lang$String);
      }
      if (isSet$displayName$java$lang$String) {
        result.setDisplayName(value$displayName$java$lang$String);
      }
      if (isSet$symbol$java$lang$String) {
        result.setSymbol(value$symbol$java$lang$String);
      }
      if (isSet$numericCode$int) {
        result.setNumericCode(value$numericCode$int);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
