package com.revaluate.core;

import net.karneim.pojobuilder.GeneratePojoBuilder;

/**
 * Created by ilucut
 */
@GeneratePojoBuilder
public class Answer {

    private String answer;

    public Answer() {
    }

    public Answer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
