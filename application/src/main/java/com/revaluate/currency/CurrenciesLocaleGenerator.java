package com.revaluate.currency;

import java.util.*;

public class CurrenciesLocaleGenerator {

    public static Map<String, List<String>> generateCurrencyLocaleMap() {
        Map<String, List<String>> currencyLocales = new HashMap<>();
        Locale[] locales = sortedAvailableLocales();

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
        Locale[] locales = sortedAvailableLocales();

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

        currencyLocales.forEach((currencyCode, currencyLocalesList) -> currencyLocalesList.sort(
                Comparator.comparing((Locale locale) -> knowsSymbolOf(currencyCode, locale) ? 0 : 1)));

        return currencyLocales;
    }

    /**
     * A locale that only knows the ISO code (e.g. "EUR") formats amounts as "EUR123,22", so prefer one that knows the symbol.
     */
    private static boolean knowsSymbolOf(String currencyCode, Locale locale) {
        return !Currency.getInstance(currencyCode).getSymbol(locale).equals(currencyCode);
    }

    /**
     * The JDK does not specify the order of its available locales, and it differs between builds.
     * Sorting them makes the locale picked for a currency, and so the formatted amount, the same on every JVM.
     */
    private static Locale[] sortedAvailableLocales() {
        Locale[] locales = Locale.getAvailableLocales();
        Arrays.sort(locales, Comparator.comparing(Locale::toString));

        return locales;
    }
}
