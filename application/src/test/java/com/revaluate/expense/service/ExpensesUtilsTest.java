package com.revaluate.expense.service;

import com.revaluate.currency.persistence.Currency;
import org.joda.time.LocalDateTime;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class ExpensesUtilsTest {

    @Test
    public void formatWithCurrencyWorks() throws Exception {
        Currency currency = new Currency();
        currency.setCurrencyCode("USD");

        assertThat(ExpensesUtils.format(new BigDecimal("222.22"), currency), is("$222.22"));
    }

    @Test
    public void formatThisYearDate() throws Exception {
        String actual = ExpensesUtils.formatDate(LocalDateTime.now());
        assertThat(actual.length(), is(11));
    }

    @Test
    public void formatAnotherYearDate() throws Exception {
        String actual = ExpensesUtils.formatDate(LocalDateTime.now().minusYears(1));
        assertThat(actual.length(), is(16));
    }
}