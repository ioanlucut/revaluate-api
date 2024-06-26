package com.revaluate.slack_command.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.util.List;

@Parameters(commandDescription = "Add expense", commandNames = {"a"})
public class CommandAddExpense {

    @Parameter(description = "Expense details with format [price] [category] [description]", required = true)
    private List<String> expenseDetails;

    public List<String> getExpenseDetails() {
        return expenseDetails;
    }

    public void setExpenseDetails(List<String> expenseDetails) {
        this.expenseDetails = expenseDetails;
    }

    @Override
    public String toString() {
        return "CommandAddExpense{" +
                "expenseDetails=" + expenseDetails +
                '}';
    }
}
