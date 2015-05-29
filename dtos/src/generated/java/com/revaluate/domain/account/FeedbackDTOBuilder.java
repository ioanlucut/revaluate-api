package com.revaluate.domain.account;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class FeedbackDTOBuilder
    implements Cloneable {
  protected FeedbackDTOBuilder self;
  protected String value$subject$java$lang$String;
  protected boolean isSet$subject$java$lang$String;
  protected String value$message$java$lang$String;
  protected boolean isSet$message$java$lang$String;

  /**
   * Creates a new {@link FeedbackDTOBuilder}.
   */
  public FeedbackDTOBuilder() {
    self = (FeedbackDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link FeedbackDTO#subject} property.
   *
   * @param value the default value
   * @return this builder
   */
  public FeedbackDTOBuilder withSubject(String value) {
    this.value$subject$java$lang$String = value;
    this.isSet$subject$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link FeedbackDTO#message} property.
   *
   * @param value the default value
   * @return this builder
   */
  public FeedbackDTOBuilder withMessage(String value) {
    this.value$message$java$lang$String = value;
    this.isSet$message$java$lang$String = true;
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
      FeedbackDTOBuilder result = (FeedbackDTOBuilder)super.clone();
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
  public FeedbackDTOBuilder but() {
    return (FeedbackDTOBuilder)clone();
  }

  /**
   * Creates a new {@link FeedbackDTO} based on this builder's settings.
   *
   * @return the created FeedbackDTO
   */
  public FeedbackDTO build() {
    try {
      FeedbackDTO result = new FeedbackDTO();
      if (isSet$subject$java$lang$String) {
        result.setSubject(value$subject$java$lang$String);
      }
      if (isSet$message$java$lang$String) {
        result.setMessage(value$message$java$lang$String);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
