package com.revaluate.generators;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class CurrenciesSymbolGenerator {

    public static void main(String[] arguments) {
        generateCurrencyCode();

        System.out.println("----------");

        generateCurrencyFractionSize();

        System.out.println("----------");

        generateCurrencyLocale();

        System.out.println("----------");

        List<String> strings = Arrays.asList("#DD5440", "#E29C45", "#E5C236", "#A1D16C", "#00B16A", "#16A085", "#59ABE3", "#4B77BE", "#2B5496", "#8471B1", "#BC73BF", "#D2527F", "#908E8E", "#6C6C6C", "#383838");
        AtomicInteger atomicInteger = new AtomicInteger(1);
        strings.stream().forEach(s -> {
            int andAdd = atomicInteger.getAndIncrement();
            System.out.println(String.format("UPDATE color SET color_name = '%s' WHERE  id = '%s';", s, andAdd));
        });
    }

    private static void generateCurrencyCode() {
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

    private static void generateCurrencyFractionSize() {
        Set<String> currencySet = new HashSet<>();
        Locale[] locs = Locale.getAvailableLocales();

        for (Locale loc : locs) {
            try {
                Currency instance = Currency.getInstance(loc);
                if (!currencySet.contains(instance.getCurrencyCode())) {
                    currencySet.add(instance.getCurrencyCode());
                    System.out.println(String.format("UPDATE currencies SET fraction_size = '%s' WHERE  currency_code = '%s';", instance.getDefaultFractionDigits(), instance.getCurrencyCode()));
                }
            } catch (Exception exc) {
                // Locale not found
            }
        }
    }

    private static void generateCurrencyLocale() {
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

        System.out.println(currencyLocales);
    }
}