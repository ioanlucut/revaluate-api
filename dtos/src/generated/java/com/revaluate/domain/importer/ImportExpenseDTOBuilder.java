package com.revaluate.domain.importer;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class ImportExpenseDTOBuilder
    implements Cloneable {
  protected ImportExpenseDTOBuilder self;
  protected String value$value$java$lang$String;
  protected boolean isSet$value$java$lang$String;
  protected String value$description$java$lang$String;
  protected boolean isSet$description$java$lang$String;
  protected String value$category$java$lang$String;
  protected boolean isSet$category$java$lang$String;
  protected String value$spentDate$java$lang$String;
  protected boolean isSet$spentDate$java$lang$String;

  /**
   * Creates a new {@link ImportExpenseDTOBuilder}.
   */
  public ImportExpenseDTOBuilder() {
    self = (ImportExpenseDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link ImportExpenseDTO#value} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ImportExpenseDTOBuilder withValue(String value) {
    this.value$value$java$lang$String = value;
    this.isSet$value$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ImportExpenseDTO#description} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ImportExpenseDTOBuilder withDescription(String value) {
    this.value$description$java$lang$String = value;
    this.isSet$description$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ImportExpenseDTO#category} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ImportExpenseDTOBuilder withCategory(String value) {
    this.value$category$java$lang$String = value;
    this.isSet$category$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ImportExpenseDTO#spentDate} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ImportExpenseDTOBuilder withSpentDate(String value) {
    this.value$spentDate$java$lang$String = value;
    this.isSet$spentDate$java$lang$String = true;
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
      ImportExpenseDTOBuilder result = (ImportExpenseDTOBuilder)super.clone();
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
  public ImportExpenseDTOBuilder but() {
    return (ImportExpenseDTOBuilder)clone();
  }

  /**
   * Creates a new {@link ImportExpenseDTO} based on this builder's settings.
   *
   * @return the created ImportExpenseDTO
   */
  public ImportExpenseDTO build() {
    try {
      ImportExpenseDTO result = new ImportExpenseDTO();
      if (isSet$value$java$lang$String) {
        result.setValue(value$value$java$lang$String);
      }
      if (isSet$description$java$lang$String) {
        result.setDescription(value$description$java$lang$String);
      }
      if (isSet$category$java$lang$String) {
        result.setCategory(value$category$java$lang$String);
      }
      if (isSet$spentDate$java$lang$String) {
        result.setSpentDate(value$spentDate$java$lang$String);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
