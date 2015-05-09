package com.revaluate.domain.importer.profile;

import com.revaluate.domain.category.CategoryDTO;
import java.util.Map;
import javax.annotation.Generated;

@Generated("PojoBuilder")
public class ExpenseCategoriesMatchingProfileDTOBuilder
    implements Cloneable {
  protected ExpenseCategoriesMatchingProfileDTOBuilder self;
  protected Map<String, CategoryDTO> value$categoriesMatchingMap$java$util$Map;
  protected boolean isSet$categoriesMatchingMap$java$util$Map;

  /**
   * Creates a new {@link ExpenseCategoriesMatchingProfileDTOBuilder}.
   */
  public ExpenseCategoriesMatchingProfileDTOBuilder() {
    self = (ExpenseCategoriesMatchingProfileDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link ExpenseCategoriesMatchingProfileDTO#categoriesMatchingMap} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseCategoriesMatchingProfileDTOBuilder withCategoriesMatchingMap(Map<String, CategoryDTO> value) {
    this.value$categoriesMatchingMap$java$util$Map = value;
    this.isSet$categoriesMatchingMap$java$util$Map = true;
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
      ExpenseCategoriesMatchingProfileDTOBuilder result = (ExpenseCategoriesMatchingProfileDTOBuilder)super.clone();
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
  public ExpenseCategoriesMatchingProfileDTOBuilder but() {
    return (ExpenseCategoriesMatchingProfileDTOBuilder)clone();
  }

  /**
   * Creates a new {@link ExpenseCategoriesMatchingProfileDTO} based on this builder's settings.
   *
   * @return the created ExpenseCategoriesMatchingProfileDTO
   */
  public ExpenseCategoriesMatchingProfileDTO build() {
    try {
      ExpenseCategoriesMatchingProfileDTO result = new ExpenseCategoriesMatchingProfileDTO();
      if (isSet$categoriesMatchingMap$java$util$Map) {
        result.setCategoriesMatchingMap(value$categoriesMatchingMap$java$util$Map);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
