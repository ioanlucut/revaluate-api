package com.revaluate.domain.settings;

import java.util.List;
import javax.annotation.Generated;

@Generated("PojoBuilder")
public class AppConfigDTOBuilder
    implements Cloneable {
  protected AppConfigDTOBuilder self;
  protected double value$version$double;
  protected boolean isSet$version$double;
  protected List<KeyValueDTO> value$keyValueDTOList$java$util$List;
  protected boolean isSet$keyValueDTOList$java$util$List;

  /**
   * Creates a new {@link AppConfigDTOBuilder}.
   */
  public AppConfigDTOBuilder() {
    self = (AppConfigDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link AppConfigDTO#version} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AppConfigDTOBuilder withVersion(double value) {
    this.value$version$double = value;
    this.isSet$version$double = true;
    return self;
  }

  /**
   * Sets the default value for the {@link AppConfigDTO#keyValueDTOList} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AppConfigDTOBuilder withKeyValueDTOList(List<KeyValueDTO> value) {
    this.value$keyValueDTOList$java$util$List = value;
    this.isSet$keyValueDTOList$java$util$List = true;
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
      AppConfigDTOBuilder result = (AppConfigDTOBuilder)super.clone();
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
  public AppConfigDTOBuilder but() {
    return (AppConfigDTOBuilder)clone();
  }

  /**
   * Creates a new {@link AppConfigDTO} based on this builder's settings.
   *
   * @return the created AppConfigDTO
   */
  public AppConfigDTO build() {
    try {
      AppConfigDTO result = new AppConfigDTO();
      if (isSet$version$double) {
        result.setVersion(value$version$double);
      }
      if (isSet$keyValueDTOList$java$util$List) {
        result.setKeyValueDTOList(value$keyValueDTOList$java$util$List);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
