package com.revaluate.domain.importer.profile;

import com.revaluate.domain.importer.column.ExpenseColumn;
import javax.annotation.Generated;

@Generated("PojoBuilder")
public class ExpenseProfileEntryDTOBuilder
    implements Cloneable {
  protected ExpenseProfileEntryDTOBuilder self;
  protected ExpenseColumn value$expenseColumn$com$revaluate$domain$importer$column$ExpenseColumn;
  protected boolean isSet$expenseColumn$com$revaluate$domain$importer$column$ExpenseColumn;
  protected int value$importColumnIndex$int;
  protected boolean isSet$importColumnIndex$int;
  protected String value$importColumnName$java$lang$String;
  protected boolean isSet$importColumnName$java$lang$String;

  /**
   * Creates a new {@link ExpenseProfileEntryDTOBuilder}.
   */
  public ExpenseProfileEntryDTOBuilder() {
    self = (ExpenseProfileEntryDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link ExpenseProfileEntryDTO#expenseColumn} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseProfileEntryDTOBuilder withExpenseColumn(ExpenseColumn value) {
    this.value$expenseColumn$com$revaluate$domain$importer$column$ExpenseColumn = value;
    this.isSet$expenseColumn$com$revaluate$domain$importer$column$ExpenseColumn = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ExpenseProfileEntryDTO#importColumnIndex} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseProfileEntryDTOBuilder withImportColumnIndex(int value) {
    this.value$importColumnIndex$int = value;
    this.isSet$importColumnIndex$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link ExpenseProfileEntryDTO#importColumnName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public ExpenseProfileEntryDTOBuilder withImportColumnName(String value) {
    this.value$importColumnName$java$lang$String = value;
    this.isSet$importColumnName$java$lang$String = true;
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
      ExpenseProfileEntryDTOBuilder result = (ExpenseProfileEntryDTOBuilder)super.clone();
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
  public ExpenseProfileEntryDTOBuilder but() {
    return (ExpenseProfileEntryDTOBuilder)clone();
  }

  /**
   * Creates a new {@link ExpenseProfileEntryDTO} based on this builder's settings.
   *
   * @return the created ExpenseProfileEntryDTO
   */
  public ExpenseProfileEntryDTO build() {
    try {
      ExpenseProfileEntryDTO result = new ExpenseProfileEntryDTO();
      if (isSet$expenseColumn$com$revaluate$domain$importer$column$ExpenseColumn) {
        result.setExpenseColumn(value$expenseColumn$com$revaluate$domain$importer$column$ExpenseColumn);
      }
      if (isSet$importColumnIndex$int) {
        result.setImportColumnIndex(value$importColumnIndex$int);
      }
      if (isSet$importColumnName$java$lang$String) {
        result.setImportColumnName(value$importColumnName$java$lang$String);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
