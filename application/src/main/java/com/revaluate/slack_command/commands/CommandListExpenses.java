package com.revaluate.slack_command.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "List expenses")
public class CommandListExpenses {

    @Parameter(names = {"-cat"}, required = false)
    private String category;

    @Parameter(names = {"-limit"}, required = false)
    private Integer limit;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "CommandListExpenses{" +
                "category='" + category + '\'' +
                ", limit=" + limit +
                '}';
    }
}
