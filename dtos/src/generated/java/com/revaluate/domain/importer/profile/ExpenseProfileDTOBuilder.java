package com.revaluate.domain.importer.profile;

import com.revaluate.domain.importer.column.ExpenseColumn;
import java.util.Map;
import javax.annotation.Generated;

@Generated("PojoBuilder")
public class ExpenseProfileDTOBuilder
    implements Cloneable {
  protected ExpenseProfileDTOBuilder self;
  protected Map<ExpenseColumn, String> value$expenseColumnMatchingMap$java$util$Map;
  protected boolean isSet$expenseColumnMatchingMap$java$util$Map;
  protected char value$delimiter$char;
  protected boolean isSet$delimiter$char;
  protected String value$spentDateFormat$java$lang$String;
  protected boolean isSet$spentDateFormat$java$lang$String;

  /**
   * Creates a new {@link ExpenseProfileDTOBuilder}.
   */
  public ExpenseProfileDTOBuilder() {
    self = (ExpenseProfileDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link ExpenseProfileDTO#expenseColumnMatchingMap} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseProfileDTOBuilder withExpenseColumnMatchingMap(Map<ExpenseColumn, String> value) {
    this.value$expenseColumnMatchingMap$java$util$Map = value;
    this.isSet$expenseColumnMatchingMap$java$util$Map = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ExpenseProfileDTO#delimiter} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseProfileDTOBuilder withDelimiter(char value) {
    this.value$delimiter$char = value;
    this.isSet$delimiter$char = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ExpenseProfileDTO#spentDateFormat} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseProfileDTOBuilder withSpentDateFormat(String value) {
    this.value$spentDateFormat$java$lang$String = value;
    this.isSet$spentDateFormat$java$lang$String = true;
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
      ExpenseProfileDTOBuilder result = (ExpenseProfileDTOBuilder)super.clone();
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
  public ExpenseProfileDTOBuilder but() {
    return (ExpenseProfileDTOBuilder)clone();
  }

  /**
   * Creates a new {@link ExpenseProfileDTO} based on this builder's settings.
   *
   * @return the created ExpenseProfileDTO
   */
  public ExpenseProfileDTO build() {
    try {
      ExpenseProfileDTO result = new ExpenseProfileDTO();
      if (isSet$expenseColumnMatchingMap$java$util$Map) {
        result.setExpenseColumnMatchingMap(value$expenseColumnMatchingMap$java$util$Map);
      }
      if (isSet$delimiter$char) {
        result.setDelimiter(value$delimiter$char);
      }
      if (isSet$spentDateFormat$java$lang$String) {
        result.setSpentDateFormat(value$spentDateFormat$java$lang$String);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
