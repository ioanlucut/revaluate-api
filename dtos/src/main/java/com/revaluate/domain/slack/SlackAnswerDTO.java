package com.revaluate.domain.slack;

import net.karneim.pojobuilder.GeneratePojoBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

@GeneratePojoBuilder
public class SlackAnswerDTO implements Serializable {

    private static final long serialVersionUID = -1799428438852023627L;

    @NotEmpty
    public String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}