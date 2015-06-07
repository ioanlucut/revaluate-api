package com.revaluate.generators;

import java.util.Currency;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class CurrenciesSymbolGenerator {

    public static void main(String[] arguments) {
        Set<String> currencySet = new HashSet<>();
        Locale[] locs = Locale.getAvailableLocales();

        for (Locale loc : locs) {
            try {
                Currency instance = Currency.getInstance(loc);
                if (!currencySet.contains(instance.getCurrencyCode())) {
                    currencySet.add(instance.getCurrencyCode());
                    System.out.println(String.format("UPDATE currencies SET symbol = '%s' WHERE  currency_code = '%s';", instance.getSymbol(loc), instance.getCurrencyCode()));
                }
            } catch (Exception exc) {
                // Locale not found
            }
        }
    }
}