package com.revaluate.domain.slack;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class SlackAnswerDTOBuilder
    implements Cloneable {
  protected SlackAnswerDTOBuilder self;
  protected String value$text$java$lang$String;
  protected boolean isSet$text$java$lang$String;

  /**
   * Creates a new {@link SlackAnswerDTOBuilder}.
   */
  public SlackAnswerDTOBuilder() {
    self = (SlackAnswerDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link SlackAnswerDTO#text} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackAnswerDTOBuilder withText(String value) {
    this.value$text$java$lang$String = value;
    this.isSet$text$java$lang$String = true;
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
      SlackAnswerDTOBuilder result = (SlackAnswerDTOBuilder)super.clone();
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
  public SlackAnswerDTOBuilder but() {
    return (SlackAnswerDTOBuilder)clone();
  }

  /**
   * Creates a new {@link SlackAnswerDTO} based on this builder's settings.
   *
   * @return the created SlackAnswerDTO
   */
  public SlackAnswerDTO build() {
    try {
      SlackAnswerDTO result = new SlackAnswerDTO();
      if (isSet$text$java$lang$String) {
        result.setText(value$text$java$lang$String);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
