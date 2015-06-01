package com.revaluate.domain.email;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class SendFeedbackToBuilder
    implements Cloneable {
  protected SendFeedbackToBuilder self;
  protected int value$id$int;
  protected boolean isSet$id$int;
  protected String value$firstName$java$lang$String;
  protected boolean isSet$firstName$java$lang$String;
  protected String value$lastName$java$lang$String;
  protected boolean isSet$lastName$java$lang$String;
  protected String value$email$java$lang$String;
  protected boolean isSet$email$java$lang$String;
  protected int value$emailId$int;
  protected boolean isSet$emailId$int;
  protected EmailType value$emailType$com$revaluate$domain$email$EmailType;
  protected boolean isSet$emailType$com$revaluate$domain$email$EmailType;
  protected String value$subject$java$lang$String;
  protected boolean isSet$subject$java$lang$String;
  protected String value$message$java$lang$String;
  protected boolean isSet$message$java$lang$String;

  /**
   * Creates a new {@link SendFeedbackToBuilder}.
   */
  public SendFeedbackToBuilder() {
    self = (SendFeedbackToBuilder)this;
  }

  /**
   * Sets the default value for the {@link SendFeedbackTo#id} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SendFeedbackToBuilder withId(int value) {
    this.value$id$int = value;
    this.isSet$id$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SendFeedbackTo#firstName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SendFeedbackToBuilder withFirstName(String value) {
    this.value$firstName$java$lang$String = value;
    this.isSet$firstName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SendFeedbackTo#lastName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SendFeedbackToBuilder withLastName(String value) {
    this.value$lastName$java$lang$String = value;
    this.isSet$lastName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SendFeedbackTo#email} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SendFeedbackToBuilder withEmail(String value) {
    this.value$email$java$lang$String = value;
    this.isSet$email$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SendFeedbackTo#emailId} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SendFeedbackToBuilder withEmailId(int value) {
    this.value$emailId$int = value;
    this.isSet$emailId$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SendFeedbackTo#emailType} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SendFeedbackToBuilder withEmailType(EmailType value) {
    this.value$emailType$com$revaluate$domain$email$EmailType = value;
    this.isSet$emailType$com$revaluate$domain$email$EmailType = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SendFeedbackTo#subject} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SendFeedbackToBuilder withSubject(String value) {
    this.value$subject$java$lang$String = value;
    this.isSet$subject$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SendFeedbackTo#message} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SendFeedbackToBuilder withMessage(String value) {
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
      SendFeedbackToBuilder result = (SendFeedbackToBuilder)super.clone();
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
  public SendFeedbackToBuilder but() {
    return (SendFeedbackToBuilder)clone();
  }

  /**
   * Creates a new {@link SendFeedbackTo} based on this builder's settings.
   *
   * @return the created SendFeedbackTo
   */
  public SendFeedbackTo build() {
    try {
      SendFeedbackTo result = new SendFeedbackTo();
      if (isSet$id$int) {
        result.setId(value$id$int);
      }
      if (isSet$firstName$java$lang$String) {
        result.setFirstName(value$firstName$java$lang$String);
      }
      if (isSet$lastName$java$lang$String) {
        result.setLastName(value$lastName$java$lang$String);
      }
      if (isSet$email$java$lang$String) {
        result.setEmail(value$email$java$lang$String);
      }
      if (isSet$emailId$int) {
        result.setEmailId(value$emailId$int);
      }
      if (isSet$emailType$com$revaluate$domain$email$EmailType) {
        result.setEmailType(value$emailType$com$revaluate$domain$email$EmailType);
      }
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
