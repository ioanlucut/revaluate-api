package com.revaluate.domain.email;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class SendToBuilder
    implements Cloneable {
  protected SendToBuilder self;
  protected int value$id$int;
  protected boolean isSet$id$int;
  protected String value$firstName$java$lang$String;
  protected boolean isSet$firstName$java$lang$String;
  protected String value$lastName$java$lang$String;
  protected boolean isSet$lastName$java$lang$String;
  protected String value$email$java$lang$String;
  protected boolean isSet$email$java$lang$String;
  protected int value$emailTokenId$int;
  protected boolean isSet$emailTokenId$int;
  protected String value$emailToken$java$lang$String;
  protected boolean isSet$emailToken$java$lang$String;
  protected EmailType value$emailType$com$revaluate$domain$email$EmailType;
  protected boolean isSet$emailType$com$revaluate$domain$email$EmailType;

  /**
   * Creates a new {@link SendToBuilder}.
   */
  public SendToBuilder() {
    self = (SendToBuilder)this;
  }

  /**
   * Sets the default value for the {@link SendTo#id} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SendToBuilder withId(int value) {
    this.value$id$int = value;
    this.isSet$id$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SendTo#firstName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SendToBuilder withFirstName(String value) {
    this.value$firstName$java$lang$String = value;
    this.isSet$firstName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SendTo#lastName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SendToBuilder withLastName(String value) {
    this.value$lastName$java$lang$String = value;
    this.isSet$lastName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SendTo#email} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SendToBuilder withEmail(String value) {
    this.value$email$java$lang$String = value;
    this.isSet$email$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SendTo#emailTokenId} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SendToBuilder withEmailTokenId(int value) {
    this.value$emailTokenId$int = value;
    this.isSet$emailTokenId$int = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SendTo#emailToken} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SendToBuilder withEmailToken(String value) {
    this.value$emailToken$java$lang$String = value;
    this.isSet$emailToken$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SendTo#emailType} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SendToBuilder withEmailType(EmailType value) {
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
      SendToBuilder result = (SendToBuilder)super.clone();
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
  public SendToBuilder but() {
    return (SendToBuilder)clone();
  }

  /**
   * Creates a new {@link SendTo} based on this builder's settings.
   *
   * @return the created SendTo
   */
  public SendTo build() {
    try {
      SendTo result = new SendTo();
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
      if (isSet$emailTokenId$int) {
        result.setEmailTokenId(value$emailTokenId$int);
      }
      if (isSet$emailToken$java$lang$String) {
        result.setEmailToken(value$emailToken$java$lang$String);
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
