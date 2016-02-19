package com.revaluate.expense.service;

import com.revaluate.currency.persistence.Currency;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class ExpensesUtilsTest {

    @Test
    public void formatWithCurrencyWorks() throws Exception {
        Currency currency = new Currency();
        currency.setCurrencyCode("USD");

        assertThat(ExpensesUtils.format(new BigDecimal("222.22"), currency)).isEqualTo(("$222.22"));
    }

    @Test
    public void parseCurrency_isCorrect() throws Exception {
        assertThat(ExpensesUtils.parseCurrency("22.22").doubleValue()).isEqualTo((22.22));
        assertThat(ExpensesUtils.parseCurrency("22.2223").doubleValue()).isEqualTo((22.22));
        assertThat(ExpensesUtils.parseCurrency("1111.98").doubleValue()).isEqualTo((1111.98));
        assertThat(ExpensesUtils.parseCurrency("10000.98").doubleValue()).isEqualTo((10000.98));
        assertThat(ExpensesUtils.parseCurrency("23.4").doubleValue()).isEqualTo((23.40));
        assertThat(ExpensesUtils.parseCurrency("23").doubleValue()).isEqualTo((23.00));
        assertThat(ExpensesUtils.parseCurrency("23.23232").doubleValue()).isEqualTo((23.23));
        assertThat(ExpensesUtils.parseCurrency("23.23.23").doubleValue()).isEqualTo((23.23));
        assertThat(ExpensesUtils.parseCurrency("123131231").doubleValue()).isEqualTo((123131231.00));

        assertThat(ExpensesUtils.parseCurrency("22.22,33").doubleValue()).isEqualTo((22.22));
        assertThat(ExpensesUtils.parseCurrency("22.22,3,3").doubleValue()).isEqualTo((22.22));
    }

    @Test
    public void formatThisYearDate() throws Exception {
        String actual = ExpensesUtils.formatDate(LocalDateTime.now());
        assertThat(actual.length()).isGreaterThanOrEqualTo(1);
    }

    @Test
    public void formatAnotherYearDate() throws Exception {
        String actual = ExpensesUtils.formatDate(LocalDateTime.now().minusYears(1));
        assertThat(actual.length()).isGreaterThanOrEqualTo(1);
    }
}