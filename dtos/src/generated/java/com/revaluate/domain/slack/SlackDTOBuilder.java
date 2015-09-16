package com.revaluate.domain.slack;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class SlackDTOBuilder
    implements Cloneable {
  protected SlackDTOBuilder self;
  protected String value$token$java$lang$String;
  protected boolean isSet$token$java$lang$String;
  protected String value$teamId$java$lang$String;
  protected boolean isSet$teamId$java$lang$String;
  protected String value$teamDomain$java$lang$String;
  protected boolean isSet$teamDomain$java$lang$String;
  protected String value$channelId$java$lang$String;
  protected boolean isSet$channelId$java$lang$String;
  protected String value$channelName$java$lang$String;
  protected boolean isSet$channelName$java$lang$String;
  protected String value$userId$java$lang$String;
  protected boolean isSet$userId$java$lang$String;
  protected String value$userName$java$lang$String;
  protected boolean isSet$userName$java$lang$String;
  protected String value$command$java$lang$String;
  protected boolean isSet$command$java$lang$String;
  protected String value$text$java$lang$String;
  protected boolean isSet$text$java$lang$String;

  /**
   * Creates a new {@link SlackDTOBuilder}.
   */
  public SlackDTOBuilder() {
    self = (SlackDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link SlackDTO#token} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackDTOBuilder withToken(String value) {
    this.value$token$java$lang$String = value;
    this.isSet$token$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SlackDTO#teamId} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackDTOBuilder withTeamId(String value) {
    this.value$teamId$java$lang$String = value;
    this.isSet$teamId$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SlackDTO#teamDomain} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackDTOBuilder withTeamDomain(String value) {
    this.value$teamDomain$java$lang$String = value;
    this.isSet$teamDomain$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SlackDTO#channelId} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackDTOBuilder withChannelId(String value) {
    this.value$channelId$java$lang$String = value;
    this.isSet$channelId$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SlackDTO#channelName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackDTOBuilder withChannelName(String value) {
    this.value$channelName$java$lang$String = value;
    this.isSet$channelName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SlackDTO#userId} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackDTOBuilder withUserId(String value) {
    this.value$userId$java$lang$String = value;
    this.isSet$userId$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SlackDTO#userName} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackDTOBuilder withUserName(String value) {
    this.value$userName$java$lang$String = value;
    this.isSet$userName$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SlackDTO#command} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackDTOBuilder withCommand(String value) {
    this.value$command$java$lang$String = value;
    this.isSet$command$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SlackDTO#text} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackDTOBuilder withText(String value) {
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
      SlackDTOBuilder result = (SlackDTOBuilder)super.clone();
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
  public SlackDTOBuilder but() {
    return (SlackDTOBuilder)clone();
  }

  /**
   * Creates a new {@link SlackDTO} based on this builder's settings.
   *
   * @return the created SlackDTO
   */
  public SlackDTO build() {
    try {
      SlackDTO result = new SlackDTO();
      if (isSet$token$java$lang$String) {
        result.setToken(value$token$java$lang$String);
      }
      if (isSet$teamId$java$lang$String) {
        result.setTeamId(value$teamId$java$lang$String);
      }
      if (isSet$teamDomain$java$lang$String) {
        result.setTeamDomain(value$teamDomain$java$lang$String);
      }
      if (isSet$channelId$java$lang$String) {
        result.setChannelId(value$channelId$java$lang$String);
      }
      if (isSet$channelName$java$lang$String) {
        result.setChannelName(value$channelName$java$lang$String);
      }
      if (isSet$userId$java$lang$String) {
        result.setUserId(value$userId$java$lang$String);
      }
      if (isSet$userName$java$lang$String) {
        result.setUserName(value$userName$java$lang$String);
      }
      if (isSet$command$java$lang$String) {
        result.setCommand(value$command$java$lang$String);
      }
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
