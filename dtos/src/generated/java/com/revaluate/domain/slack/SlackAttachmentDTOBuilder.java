package com.revaluate.domain.slack;

import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated("PojoBuilder")
public class SlackAttachmentDTOBuilder
    implements Cloneable {
  protected SlackAttachmentDTOBuilder self;
  protected String value$title$java$lang$String;
  protected boolean isSet$title$java$lang$String;
  protected String value$titleLink$java$lang$String;
  protected boolean isSet$titleLink$java$lang$String;
  protected String value$fallback$java$lang$String;
  protected boolean isSet$fallback$java$lang$String;
  protected String value$text$java$lang$String;
  protected boolean isSet$text$java$lang$String;
  protected String value$pretext$java$lang$String;
  protected boolean isSet$pretext$java$lang$String;
  protected String value$thumb_url$java$lang$String;
  protected boolean isSet$thumb_url$java$lang$String;
  protected String value$color$java$lang$String;
  protected boolean isSet$color$java$lang$String;
  protected Map<String, String> value$miscRootFields$java$util$Map;
  protected boolean isSet$miscRootFields$java$util$Map;
  protected List<SlackField> value$fields$java$util$List;
  protected boolean isSet$fields$java$util$List;
  protected List<String> value$markdown_in$java$util$List;
  protected boolean isSet$markdown_in$java$util$List;

  /**
   * Creates a new {@link SlackAttachmentDTOBuilder}.
   */
  public SlackAttachmentDTOBuilder() {
    self = (SlackAttachmentDTOBuilder)this;
  }

  /**
   * Sets the default value for the {@link SlackAttachmentDTO#title} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackAttachmentDTOBuilder withTitle(String value) {
    this.value$title$java$lang$String = value;
    this.isSet$title$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SlackAttachmentDTO#titleLink} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackAttachmentDTOBuilder withTitleLink(String value) {
    this.value$titleLink$java$lang$String = value;
    this.isSet$titleLink$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SlackAttachmentDTO#fallback} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackAttachmentDTOBuilder withFallback(String value) {
    this.value$fallback$java$lang$String = value;
    this.isSet$fallback$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SlackAttachmentDTO#text} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackAttachmentDTOBuilder withText(String value) {
    this.value$text$java$lang$String = value;
    this.isSet$text$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SlackAttachmentDTO#pretext} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackAttachmentDTOBuilder withPretext(String value) {
    this.value$pretext$java$lang$String = value;
    this.isSet$pretext$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SlackAttachmentDTO#thumb_url} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackAttachmentDTOBuilder withThumb_url(String value) {
    this.value$thumb_url$java$lang$String = value;
    this.isSet$thumb_url$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SlackAttachmentDTO#color} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackAttachmentDTOBuilder withColor(String value) {
    this.value$color$java$lang$String = value;
    this.isSet$color$java$lang$String = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SlackAttachmentDTO#miscRootFields} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackAttachmentDTOBuilder withMiscRootFields(Map<String, String> value) {
    this.value$miscRootFields$java$util$Map = value;
    this.isSet$miscRootFields$java$util$Map = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SlackAttachmentDTO#fields} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackAttachmentDTOBuilder withFields(List<SlackField> value) {
    this.value$fields$java$util$List = value;
    this.isSet$fields$java$util$List = true;
    return self;
  }

  /**
   * Sets the default value for the {@link SlackAttachmentDTO#markdown_in} property.
   *
   * @param value the default value
   * @return this builder
   */
  public SlackAttachmentDTOBuilder withMarkdown_in(List<String> value) {
    this.value$markdown_in$java$util$List = value;
    this.isSet$markdown_in$java$util$List = true;
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
      SlackAttachmentDTOBuilder result = (SlackAttachmentDTOBuilder)super.clone();
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
  public SlackAttachmentDTOBuilder but() {
    return (SlackAttachmentDTOBuilder)clone();
  }

  /**
   * Creates a new {@link SlackAttachmentDTO} based on this builder's settings.
   *
   * @return the created SlackAttachmentDTO
   */
  public SlackAttachmentDTO build() {
    try {
      SlackAttachmentDTO result = new SlackAttachmentDTO();
      if (isSet$title$java$lang$String) {
        result.setTitle(value$title$java$lang$String);
      }
      if (isSet$titleLink$java$lang$String) {
        result.setTitleLink(value$titleLink$java$lang$String);
      }
      if (isSet$fallback$java$lang$String) {
        result.setFallback(value$fallback$java$lang$String);
      }
      if (isSet$text$java$lang$String) {
        result.setText(value$text$java$lang$String);
      }
      if (isSet$pretext$java$lang$String) {
        result.setPretext(value$pretext$java$lang$String);
      }
      if (isSet$color$java$lang$String) {
        result.setColor(value$color$java$lang$String);
      }
      if (isSet$thumb_url$java$lang$String) {
        result.thumb_url = value$thumb_url$java$lang$String;
      }
      if (isSet$miscRootFields$java$util$Map) {
        result.miscRootFields = value$miscRootFields$java$util$Map;
      }
      if (isSet$fields$java$util$List) {
        result.fields = value$fields$java$util$List;
      }
      if (isSet$markdown_in$java$util$List) {
        result.markdown_in = value$markdown_in$java$util$List;
      }
      return result;
    } catch (RuntimeException ex) {
      throw ex;
    } catch (Exception ex) {
      throw new java.lang.reflect.UndeclaredThrowableException(ex);
    }
  }
}
