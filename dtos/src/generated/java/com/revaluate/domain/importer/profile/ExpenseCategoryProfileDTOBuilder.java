package com.revaluate.domain.importer.profile;

import com.revaluate.domain.category.CategoryDTO;
import java.util.Map;
import javax.annotation.Generated;

@Generated("PojoBuilder")
public class ExpenseCategoryProfileDTOBuilder
    implements Cloneable {
  protected ExpenseCategoryProfileDTOBuilder self;
  protected Map<String, CategoryDTO> value$categoriesMatching$java$util$Map;
  protected boolean isSet$categoriesMatching$java$util$Map;

  /**
   * Creates a new {@link ExpenseCategoryProfileDTOBuilder}.
   */
  public ExpenseCategoryProfileDTOBuilder() {
    self = (ExpenseCategoryProfileDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link ExpenseCategoriesMatchingProfileDTO#categoriesMatching} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseCategoryProfileDTOBuilder withCategoriesMatching(Map<String, CategoryDTO> value) {
    this.value$categoriesMatching$java$util$Map = value;
    this.isSet$categoriesMatching$java$util$Map = true;
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
      ExpenseCategoryProfileDTOBuilder result = (ExpenseCategoryProfileDTOBuilder)super.clone();
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
  public ExpenseCategoryProfileDTOBuilder but() {
    return (ExpenseCategoryProfileDTOBuilder)clone();
  }

  /**
   * Creates a new {@link ExpenseCategoriesMatchingProfileDTO} based on this builder's settings.
   *
   * @return the created ExpenseCategoriesMatchingProfileDTO
   */
  public ExpenseCategoriesMatchingProfileDTO build() {
    try {
      ExpenseCategoriesMatchingProfileDTO result = new ExpenseCategoriesMatchingProfileDTO();
      if (isSet$categoriesMatching$java$util$Map) {
        result.setCategoriesMatchingMap(value$categoriesMatching$java$util$Map);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
