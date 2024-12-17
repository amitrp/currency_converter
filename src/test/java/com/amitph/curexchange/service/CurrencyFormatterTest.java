package com.amitph.curexchange.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CurrencyFormatterTest {
    private CurrencyFormatter formatter;

    @BeforeEach
    public void setup() {
        Map<String, Integer> currencyFormats = new HashMap<>();
        currencyFormats.put("AUD", 2);
        currencyFormats.put("JPY", 0);

        formatter = new CurrencyFormatter(currencyFormats);
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
                assertEquals(
                        currencyAmountExpectedAmount.get(currency, amount),
                        formatter.format(currency, amount));
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
                assertEquals(
                        currencyAmountExpectedAmount.get(currency, amount),
                        formatter.format(currency, amount));
            }
        }
    }
}
