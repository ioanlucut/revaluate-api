package com.revaluate.slack_command.commands;

import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Categories", commandNames = {"c"})
public class CommandCategories {

    @Override
    public String toString() {
        return "CommandCategories{" +
                '}';
    }
}
