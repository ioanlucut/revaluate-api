package com.revaluate.expense.domain;

import com.revaluate.category.domain.CategoryDTO;
import java.util.Date;
import javax.annotation.Generated;

@Generated("PojoBuilder")
public class ExpenseDTOBuilder
    implements Cloneable {
  protected ExpenseDTOBuilder self;
  protected int value$id$int;
  protected boolean isSet$id$int;
  protected double value$value$double;
  protected boolean isSet$value$double;
  protected String value$description$java$lang$String;
  protected boolean isSet$description$java$lang$String;
  protected CategoryDTO value$category$com$revaluate$category$domain$CategoryDTO;
  protected boolean isSet$category$com$revaluate$category$domain$CategoryDTO;
  protected Date value$addedDate$java$util$Date;
  protected boolean isSet$addedDate$java$util$Date;

  /**
   * Creates a new {@link ExpenseDTOBuilder}.
   */
  public ExpenseDTOBuilder() {
    self = (ExpenseDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link ExpenseDTO#id} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseDTOBuilder withId(int value) {
    this.value$id$int = value;
    this.isSet$id$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ExpenseDTO#value} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseDTOBuilder withValue(double value) {
    this.value$value$double = value;
    this.isSet$value$double = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ExpenseDTO#description} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseDTOBuilder withDescription(String value) {
    this.value$description$java$lang$String = value;
    this.isSet$description$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ExpenseDTO#category} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseDTOBuilder withCategory(CategoryDTO value) {
    this.value$category$com$revaluate$category$domain$CategoryDTO = value;
    this.isSet$category$com$revaluate$category$domain$CategoryDTO = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ExpenseDTO#addedDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseDTOBuilder withAddedDate(Date value) {
    this.value$addedDate$java$util$Date = value;
    this.isSet$addedDate$java$util$Date = true;
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
      ExpenseDTOBuilder result = (ExpenseDTOBuilder)super.clone();
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
  public ExpenseDTOBuilder but() {
    return (ExpenseDTOBuilder)clone();
  }

  /**
   * Creates a new {@link ExpenseDTO} based on this builder's settings.
   *
   * @return the created ExpenseDTO
   */
  public ExpenseDTO build() {
    try {
      ExpenseDTO result = new ExpenseDTO();
      if (isSet$id$int) {
        result.setId(value$id$int);
      }
      if (isSet$value$double) {
        result.setValue(value$value$double);
      }
      if (isSet$description$java$lang$String) {
        result.setDescription(value$description$java$lang$String);
      }
      if (isSet$category$com$revaluate$category$domain$CategoryDTO) {
        result.setCategory(value$category$com$revaluate$category$domain$CategoryDTO);
      }
      if (isSet$addedDate$java$util$Date) {
        result.setAddedDate(value$addedDate$java$util$Date);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
