package com.revaluate.expense.service;

import com.revaluate.currency.CurrenciesLocaleGenerator;
import com.revaluate.currency.persistence.Currency;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public interface ExpensesUtils {

    Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {{
        put("^\\d{8}$", "yyyyMMdd");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
        put("^\\d{12}$", "yyyyMMddHHmm");
        put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
        put("^\\d{14}$", "yyyyMMddHHmmss");
        put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
    }};

    String PATTERN = "0.00";
    int DIGITS_SCALE = 2;

    Map<String, List<Locale>> STRING_LIST_MAP = CurrenciesLocaleGenerator.generateCurrencyLocalesMap();

    /**
     * Determine SimpleDateFormat pattern matching with the given date string. Returns null if
     * format is unknown. You can simply extend DateUtil with more formats if needed.
     *
     * @param dateString The date string to determine the SimpleDateFormat pattern for.
     * @return The matching SimpleDateFormat pattern, or null if format is unknown.
     * @see SimpleDateFormat
     */
    static Optional<String> determineDateFormat(String dateString) {
        for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
            if (dateString.toLowerCase().matches(regexp)) {
                return Optional.ofNullable(DATE_FORMAT_REGEXPS.get(regexp));
            }
        }
        return Optional.empty(); // Unknown format.
    }

    static BigDecimal parseCurrency(String value) throws ParseException {
        // Pick a suitable locale.  GERMANY, for example, uses the 1.234,56 format.
        // You could call .getCurrencyInstance() instead, but then it will
        // require the value to contain the correct currency symbol at a
        // specific position.
        NumberFormat fmt = NumberFormat.getNumberInstance(Locale.GERMANY);

        // By default, fmt.parse() returns a Number that is a Double.
        // However, some decimals, such as 0.10, cannot be represented
        // exactly in floating point, so it is considered best practice to
        // use BigDecimal for storing monetary values.
        ((DecimalFormat) fmt).setParseBigDecimal(true);

        return (BigDecimal) fmt.parse(value);
    }

    static String format(BigDecimal amount, Currency currency) throws ParseException {
        Locale firstLocaleFound = STRING_LIST_MAP.getOrDefault(currency.getCurrencyCode(), Collections.singletonList(Locale.US)).stream().findFirst().orElse(Locale.US);
        NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(firstLocaleFound);
        //-----------------------------------------------------------------
        // Format total amount
        //-----------------------------------------------------------------
        return currencyInstance.format(amount.setScale(DIGITS_SCALE, BigDecimal.ROUND_DOWN));
    }

    static String formatDate(LocalDateTime localeDateTime) {
        if (LocalDateTime.now().getYear() == localeDateTime.getYear()) {
            return new SimpleDateFormat("EEE, MMM d").format(localeDateTime.toDate());
        }

        return new SimpleDateFormat("EEE, MMM d, ''yy").format(localeDateTime.toDate());
    }

}