package com.revaluate.domain.color;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class ColorDTOBuilder
    implements Cloneable {
  protected ColorDTOBuilder self;
  protected int value$id$int;
  protected boolean isSet$id$int;
  protected String value$color$java$lang$String;
  protected boolean isSet$color$java$lang$String;
  protected String value$colorName$java$lang$String;
  protected boolean isSet$colorName$java$lang$String;
  protected int value$priority$int;
  protected boolean isSet$priority$int;

  /**
   * Creates a new {@link ColorDTOBuilder}.
   */
  public ColorDTOBuilder() {
    self = (ColorDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link ColorDTO#id} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ColorDTOBuilder withId(int value) {
    this.value$id$int = value;
    this.isSet$id$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ColorDTO#color} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ColorDTOBuilder withColor(String value) {
    this.value$color$java$lang$String = value;
    this.isSet$color$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ColorDTO#colorName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ColorDTOBuilder withColorName(String value) {
    this.value$colorName$java$lang$String = value;
    this.isSet$colorName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ColorDTO#priority} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ColorDTOBuilder withPriority(int value) {
    this.value$priority$int = value;
    this.isSet$priority$int = true;
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
      ColorDTOBuilder result = (ColorDTOBuilder)super.clone();
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
  public ColorDTOBuilder but() {
    return (ColorDTOBuilder)clone();
  }

  /**
   * Creates a new {@link ColorDTO} based on this builder's settings.
   *
   * @return the created ColorDTO
   */
  public ColorDTO build() {
    try {
      ColorDTO result = new ColorDTO();
      if (isSet$id$int) {
        result.setId(value$id$int);
      }
      if (isSet$color$java$lang$String) {
        result.setColor(value$color$java$lang$String);
      }
      if (isSet$colorName$java$lang$String) {
        result.setColorName(value$colorName$java$lang$String);
      }
      if (isSet$priority$int) {
        result.setPriority(value$priority$int);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
