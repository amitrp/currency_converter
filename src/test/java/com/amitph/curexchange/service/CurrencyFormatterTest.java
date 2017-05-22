package com.amitph.curexchange.service;

import static org.junit.Assert.assertEquals;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class CurrencyFormatterTest {
    CurrencyFormatter formatter;

    @Before
    public void setup() {
        formatter = new CurrencyFormatter();
        Map<String, Integer> currencyFormats = new HashMap<>();
        formatter.currencyFormats = currencyFormats;
        currencyFormats.put("AUD", 2);
        currencyFormats.put("JPY", 0);
    }

    @Test
    public void correctCurrencyAndAmountGetsFormattedCorrectly() {
        Table<String, Double, Double> currencyAmountExpectedAmount = HashBasedTable.create();
        currencyAmountExpectedAmount.put("AUD", 123.456, 123.46);
        currencyAmountExpectedAmount.put("AUD", 123.421, 123.42);
        currencyAmountExpectedAmount.put("AUD", 123.0, 123.0);
        currencyAmountExpectedAmount.put("JPY", 123.3456, 123.0);

        for (String currency : currencyAmountExpectedAmount.rowKeySet()) {
            for (Double amount : currencyAmountExpectedAmount.row(currency).keySet()) {
                assertEquals(currencyAmountExpectedAmount.get(currency, amount), formatter.format(currency, amount));
            }
        }
    }

    @Test
    public void incorrectCurrencyAndAmountFormatsToDefaultDecimalPlaces() {
        Table<String, Double, Double> currencyAmountExpectedAmount = HashBasedTable.create();
        currencyAmountExpectedAmount.put("ABC", 123.456, 123.46);
        currencyAmountExpectedAmount.put("PQR", 123.0, 123.0);

        for (String currency : currencyAmountExpectedAmount.rowKeySet()) {
            for (Double amount : currencyAmountExpectedAmount.row(currency).keySet()) {
                assertEquals(currencyAmountExpectedAmount.get(currency, amount), formatter.format(currency, amount));
            }
        }
    }
}