package com.revaluate.slack_command.commands;

import com.revaluate.expense.service.ExpensesUtils;
import com.revaluate.slack.SlackException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IgnoreMe {

    private static double getPrice(String price) throws SlackException {
        String priceMatch = getPriceMatch(price);
        System.out.println(priceMatch);

        try {
            return ExpensesUtils.parseCurrency(priceMatch).doubleValue();
        } catch (Exception ex) {

            throw new SlackException("The price format is wrong.");
        }
    }


    private static String getPriceMatch(String text) throws SlackException {
        Pattern p = Pattern.compile("-?[\\d\\.]+");
        Matcher m = p.matcher(text);
        if (m.find()) {
            return m.group();
        }

        throw new SlackException("No valid price was identified.");
    }

    public static void main(String[] args) throws SlackException {
        System.out.println(getPrice("22.22"));
        System.out.println(getPrice("22,22"));
        System.out.println(getPrice("22,22,,"));
        System.out.println(getPrice("23.4"));
        System.out.println(getPrice("23"));
        System.out.println(getPrice("23.23232"));
        System.out.println(getPrice("23.23.23 "));
        System.out.println(getPrice("123131231"));
    }
}
