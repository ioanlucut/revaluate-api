package com.revaluate.domain.email;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class AbstractSendToBuilder
    implements Cloneable {
  protected AbstractSendToBuilder self;
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

  /**
   * Creates a new {@link AbstractSendToBuilder}.
   */
  public AbstractSendToBuilder() {
    self = (AbstractSendToBuilder)this;
  }

  /**
   * Sets the default value for the {@link AbstractSendTo#id} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AbstractSendToBuilder withId(int value) {
    this.value$id$int = value;
    this.isSet$id$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link AbstractSendTo#firstName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AbstractSendToBuilder withFirstName(String value) {
    this.value$firstName$java$lang$String = value;
    this.isSet$firstName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link AbstractSendTo#lastName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AbstractSendToBuilder withLastName(String value) {
    this.value$lastName$java$lang$String = value;
    this.isSet$lastName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link AbstractSendTo#email} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AbstractSendToBuilder withEmail(String value) {
    this.value$email$java$lang$String = value;
    this.isSet$email$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link AbstractSendTo#emailId} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AbstractSendToBuilder withEmailId(int value) {
    this.value$emailId$int = value;
    this.isSet$emailId$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link AbstractSendTo#emailType} property.
   *
   * @param value the default value
   * @return this builder
   */
  public AbstractSendToBuilder withEmailType(EmailType value) {
    this.value$emailType$com$revaluate$domain$email$EmailType = value;
    this.isSet$emailType$com$revaluate$domain$email$EmailType = true;
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
      AbstractSendToBuilder result = (AbstractSendToBuilder)super.clone();
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
  public AbstractSendToBuilder but() {
    return (AbstractSendToBuilder)clone();
  }

  /**
   * Creates a new {@link AbstractSendTo} based on this builder's settings.
   *
   * @return the created AbstractSendTo
   */
  public AbstractSendTo build() {
    try {
      AbstractSendTo result = new AbstractSendTo();
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
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
