package com.revaluate.expense.service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by ilucut on 9/18/15.
 */
public class tt {


    public static BigDecimal parseCurrency(String value) throws ParseException {
        // Pick a suitable locale.  GERMANY, for example, uses the 1.234,56 format.
        // You could call .getCurrencyInstance() instead, but then it will
        // require the value to contain the correct currency symbol at a
        // specific position.
        NumberFormat fmt = NumberFormat.getNumberInstance(Locale.GERMANY);

        // By default, fmt.parse() returns a Number that is a Double.
        // However, some decimals, such as 0.10, cannot be represented
        // exactly in floating point, so it is considered best practice to
        // use BigDecimal for storing monetary values.
        ((DecimalFormat)fmt).setParseBigDecimal(true);

        return (BigDecimal)fmt.parse(value);
    }
    public static void main(String[] args) throws ParseException {
        System.out.println(parseCurrency("11.22,22"));
        System.out.println(parseCurrency("11.............22,,22"));
    }
}
