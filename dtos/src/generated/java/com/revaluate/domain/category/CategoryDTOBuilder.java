package com.revaluate.domain.category;

import com.revaluate.domain.color.ColorDTO;
import javax.annotation.Generated;

@Generated("PojoBuilder")
public class CategoryDTOBuilder
    implements Cloneable {
  protected CategoryDTOBuilder self;
  protected int value$id$int;
  protected boolean isSet$id$int;
  protected String value$name$java$lang$String;
  protected boolean isSet$name$java$lang$String;
  protected ColorDTO value$color$com$revaluate$domain$color$ColorDTO;
  protected boolean isSet$color$com$revaluate$domain$color$ColorDTO;

  /**
   * Creates a new {@link CategoryDTOBuilder}.
   */
  public CategoryDTOBuilder() {
    self = (CategoryDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link CategoryDTO#id} property.
   *
   * @param value the default value
   * @return this builder
   */
  public CategoryDTOBuilder withId(int value) {
    this.value$id$int = value;
    this.isSet$id$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link CategoryDTO#name} property.
   *
   * @param value the default value
   * @return this builder
   */
  public CategoryDTOBuilder withName(String value) {
    this.value$name$java$lang$String = value;
    this.isSet$name$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link CategoryDTO#color} property.
   *
   * @param value the default value
   * @return this builder
   */
  public CategoryDTOBuilder withColor(ColorDTO value) {
    this.value$color$com$revaluate$domain$color$ColorDTO = value;
    this.isSet$color$com$revaluate$domain$color$ColorDTO = true;
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
      CategoryDTOBuilder result = (CategoryDTOBuilder)super.clone();
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
  public CategoryDTOBuilder but() {
    return (CategoryDTOBuilder)clone();
  }

  /**
   * Creates a new {@link CategoryDTO} based on this builder's settings.
   *
   * @return the created CategoryDTO
   */
  public CategoryDTO build() {
    try {
      CategoryDTO result = new CategoryDTO();
      if (isSet$id$int) {
        result.setId(value$id$int);
      }
      if (isSet$name$java$lang$String) {
        result.setName(value$name$java$lang$String);
      }
      if (isSet$color$com$revaluate$domain$color$ColorDTO) {
        result.setColor(value$color$com$revaluate$domain$color$ColorDTO);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
