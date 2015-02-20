package com.revaluate.core.resource;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class AnswerBuilder
    implements Cloneable {
  protected AnswerBuilder self;
  protected String value$answer$java$lang$String;
  protected boolean isSet$answer$java$lang$String;

  /**
   * Creates a new {@link AnswerBuilder}.
   */
  public AnswerBuilder() {
    self = (AnswerBuilder)this;
  }

  /**
   * Sets the default value for the {@link Answer#answer} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AnswerBuilder withAnswer(String value) {
    this.value$answer$java$lang$String = value;
    this.isSet$answer$java$lang$String = true;
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
      AnswerBuilder result = (AnswerBuilder)super.clone();
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
  public AnswerBuilder but() {
    return (AnswerBuilder)clone();
  }

  /**
   * Creates a new {@link Answer} based on this builder's settings.
   *
   * @return the created Answer
   */
  public Answer build() {
    try {
      Answer result = new Answer();
      if (isSet$answer$java$lang$String) {
        result.setAnswer(value$answer$java$lang$String);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
