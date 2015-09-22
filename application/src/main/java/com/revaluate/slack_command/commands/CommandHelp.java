package com.revaluate.slack_command.commands;

import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Help", commandNames = {"h"})
public class CommandHelp {

    @Override
    public String toString() {
        return "CommandHelp{" +
                '}';
    }
}
