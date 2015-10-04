package com.revaluate.currency;

import java.util.*;

public class CurrenciesLocaleGenerator {

    public static Map<String, List<String>> generateCurrencyLocaleMap() {
        Map<String, List<String>> currencyLocales = new HashMap<>();
        Locale[] locales = Locale.getAvailableLocales();

        for (Locale locale : locales) {
            try {
                Currency instance = Currency.getInstance(locale);
                List<String> orDefault = currencyLocales.getOrDefault(instance.getCurrencyCode(), new ArrayList<>());
                orDefault.add(locale.toString());
                currencyLocales.put(instance.getCurrencyCode(), orDefault);
            } catch (Exception ex) {
                // Locale not found
            }
        }

        return currencyLocales;
    }

    public static Map<String, List<Locale>> generateCurrencyLocalesMap() {
        Map<String, List<Locale>> currencyLocales = new HashMap<>();
        Locale[] locales = Locale.getAvailableLocales();

        for (Locale locale : locales) {
            try {
                Currency instance = Currency.getInstance(locale);
                List<Locale> orDefault = currencyLocales.getOrDefault(instance.getCurrencyCode(), new ArrayList<>());
                orDefault.add(locale);
                currencyLocales.put(instance.getCurrencyCode(), orDefault);
            } catch (Exception ex) {
                // Locale not found
            }
        }

        return currencyLocales;
    }
}