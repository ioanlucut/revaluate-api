package com.revaluate.domain.slack;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class SlackIdentityResponseDTOBuilder
    implements Cloneable {
  protected SlackIdentityResponseDTOBuilder self;
  protected String value$ok$java$lang$String;
  protected boolean isSet$ok$java$lang$String;
  protected String value$teamId$java$lang$String;
  protected boolean isSet$teamId$java$lang$String;
  protected String value$userId$java$lang$String;
  protected boolean isSet$userId$java$lang$String;

  /**
   * Creates a new {@link SlackIdentityResponseDTOBuilder}.
   */
  public SlackIdentityResponseDTOBuilder() {
    self = (SlackIdentityResponseDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link SlackIdentityResponseDTO#ok} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackIdentityResponseDTOBuilder withOk(String value) {
    this.value$ok$java$lang$String = value;
    this.isSet$ok$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SlackIdentityResponseDTO#teamId} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackIdentityResponseDTOBuilder withTeamId(String value) {
    this.value$teamId$java$lang$String = value;
    this.isSet$teamId$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SlackIdentityResponseDTO#userId} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackIdentityResponseDTOBuilder withUserId(String value) {
    this.value$userId$java$lang$String = value;
    this.isSet$userId$java$lang$String = true;
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
      SlackIdentityResponseDTOBuilder result = (SlackIdentityResponseDTOBuilder)super.clone();
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
  public SlackIdentityResponseDTOBuilder but() {
    return (SlackIdentityResponseDTOBuilder)clone();
  }

  /**
   * Creates a new {@link SlackIdentityResponseDTO} based on this builder's settings.
   *
   * @return the created SlackIdentityResponseDTO
   */
  public SlackIdentityResponseDTO build() {
    try {
      SlackIdentityResponseDTO result = new SlackIdentityResponseDTO();
      if (isSet$ok$java$lang$String) {
        result.setOk(value$ok$java$lang$String);
      }
      if (isSet$teamId$java$lang$String) {
        result.setTeamId(value$teamId$java$lang$String);
      }
      if (isSet$userId$java$lang$String) {
        result.setUserId(value$userId$java$lang$String);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
