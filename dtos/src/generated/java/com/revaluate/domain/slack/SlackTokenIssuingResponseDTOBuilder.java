package com.revaluate.domain.slack;

import javax.annotation.Generated;

@Generated("PojoBuilder")
public class SlackTokenIssuingResponseDTOBuilder
    implements Cloneable {
  protected SlackTokenIssuingResponseDTOBuilder self;
  protected String value$accessToken$java$lang$String;
  protected boolean isSet$accessToken$java$lang$String;
  protected String value$scope$java$lang$String;
  protected boolean isSet$scope$java$lang$String;

  /**
   * Creates a new {@link SlackTokenIssuingResponseDTOBuilder}.
   */
  public SlackTokenIssuingResponseDTOBuilder() {
    self = (SlackTokenIssuingResponseDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link SlackTokenIssuingResponseDTO#accessToken} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackTokenIssuingResponseDTOBuilder withAccessToken(String value) {
    this.value$accessToken$java$lang$String = value;
    this.isSet$accessToken$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SlackTokenIssuingResponseDTO#scope} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackTokenIssuingResponseDTOBuilder withScope(String value) {
    this.value$scope$java$lang$String = value;
    this.isSet$scope$java$lang$String = true;
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
      SlackTokenIssuingResponseDTOBuilder result = (SlackTokenIssuingResponseDTOBuilder)super.clone();
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
  public SlackTokenIssuingResponseDTOBuilder but() {
    return (SlackTokenIssuingResponseDTOBuilder)clone();
  }

  /**
   * Creates a new {@link SlackTokenIssuingResponseDTO} based on this builder's settings.
   *
   * @return the created SlackTokenIssuingResponseDTO
   */
  public SlackTokenIssuingResponseDTO build() {
    try {
      SlackTokenIssuingResponseDTO result = new SlackTokenIssuingResponseDTO();
      if (isSet$accessToken$java$lang$String) {
        result.setAccessToken(value$accessToken$java$lang$String);
      }
      if (isSet$scope$java$lang$String) {
        result.setScope(value$scope$java$lang$String);
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
