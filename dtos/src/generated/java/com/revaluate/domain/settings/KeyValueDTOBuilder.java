package com.revaluate.domain.settings;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class KeyValueDTOBuilder
    implements Cloneable {
  protected KeyValueDTOBuilder self;
  protected String value$key$java$lang$String;
  protected boolean isSet$key$java$lang$String;
  protected String value$value$java$lang$String;
  protected boolean isSet$value$java$lang$String;

  /**
   * Creates a new {@link KeyValueDTOBuilder}.
   */
  public KeyValueDTOBuilder() {
    self = (KeyValueDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link KeyValueDTO#key} property.
   *
   * @param value the default value
   * @return this builder
   */
  public KeyValueDTOBuilder withKey(String value) {
    this.value$key$java$lang$String = value;
    this.isSet$key$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link KeyValueDTO#value} property.
   *
   * @param value the default value
   * @return this builder
   */
  public KeyValueDTOBuilder withValue(String value) {
    this.value$value$java$lang$String = value;
    this.isSet$value$java$lang$String = true;
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
      KeyValueDTOBuilder result = (KeyValueDTOBuilder)super.clone();
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
  public KeyValueDTOBuilder but() {
    return (KeyValueDTOBuilder)clone();
  }

  /**
   * Creates a new {@link KeyValueDTO} based on this builder's settings.
   *
   * @return the created KeyValueDTO
   */
  public KeyValueDTO build() {
    try {
      KeyValueDTO result = new KeyValueDTO();
      if (isSet$key$java$lang$String) {
        result.setKey(value$key$java$lang$String);
      }
      if (isSet$value$java$lang$String) {
        result.setValue(value$value$java$lang$String);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
