package com.amitph.curexchange.service;

import static java.lang.Boolean.TRUE;
import static java.lang.Double.parseDouble;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CurrencyConverterServiceTest {
    private CurrencyConverterService service;

    private InputValidator validator;

    private CurrencyFormatter formatter;
    private Table<String, String, Double> currencyRates;
    private Table<String, String, String> currencyCrossReference;

    @BeforeEach
    public void setup() {
        currencyRates = mockCurrencyRates();
        currencyCrossReference = mockCurrencyCrossReferences();
        formatter = mock(CurrencyFormatter.class);
        validator = mock(InputValidator.class);
        service =
                new CurrencyConverterService(
                        currencyRates, currencyCrossReference, validator, formatter);
    }

    @Test
    public void validatorIsCalledWhenServiceIsCalled() {
        String inputString = "AUD 1000 IN USD";
        service.convertCurrency(inputString);
        verify(validator).isInputValid(inputString);
    }

    @Test
    public void formatterIsCalledWhenServiceIsCalledWithValidInput() {
        String inputString = "AUD 100.00 in USD";
        when(validator.isInputValid(inputString)).thenReturn(TRUE);
        service.convertCurrency(inputString);
        verify(formatter).format("USD", 83.71);
    }

    @Test
    public void currencyConversionWorksCorrectly() {
        Map<String, String> inputOutput = new HashMap<>();
        inputOutput.put("AUD 100.00 in USD", "AUD 100.0 = USD 83.71");
        inputOutput.put("EUR 100.00 in USD", "EUR 100.0 = USD 123.15");

        String base, term;
        Double amount;
        for (String input : inputOutput.keySet()) {
            when(validator.isInputValid(input)).thenReturn(TRUE);
            String[] arr = input.split("\\s+");
            base = arr[0];
            amount = parseDouble(arr[1]);
            term = arr[3];
            when(formatter.format(term, amount * currencyRates.get(base, term)))
                    .thenReturn(
                            (new BigDecimal(amount * currencyRates.get(base, term))
                                            .setScale(2, RoundingMode.HALF_EVEN))
                                    .doubleValue());
            assertEquals(inputOutput.get(input), service.convertCurrency(input));
        }
    }

    @Test
    public void unknownCurrencyConversionShowsProperMessage() {
        Map<String, String> inputOutput = new HashMap<>();
        inputOutput.put("AUD 100.00 in ABC", "Unable to find rate for AUD/ABC");
        inputOutput.put("EUR 100.00 in JPY", "Unable to find rate for EUR/JPY");

        for (String input : inputOutput.keySet()) {
            when(validator.isInputValid(input)).thenReturn(TRUE);
            assertEquals(inputOutput.get(input), service.convertCurrency(input));
        }
    }

    @Test
    public void inverseCurrencyConversionWorksCorrectly() {
        Map<String, String> inputOutput = new HashMap<>();
        inputOutput.put("USD 100.00 in AUD", "USD 100.0 = AUD 119.46");
        inputOutput.put("USD 100.00 in EUR", "USD 100.0 = EUR 81.2");

        String base, term;
        double amount;
        for (String input : inputOutput.keySet()) {
            when(validator.isInputValid(input)).thenReturn(TRUE);
            String[] arr = input.split("\\s+");
            base = arr[0];
            amount = parseDouble(arr[1]);
            term = arr[3];
            when(formatter.format(term, amount * (1 / currencyRates.get(term, base))))
                    .thenReturn(
                            (new BigDecimal(amount * (1 / currencyRates.get(term, base)))
                                            .setScale(2, RoundingMode.HALF_EVEN))
                                    .doubleValue());
            assertEquals(inputOutput.get(input), service.convertCurrency(input));
        }
    }

    @Test
    public void referencialCurrencyConversionWorksCorrectly() {
        String inputString = "AUD 100.00 in DKK";
        String expected = "AUD 100.0 = DKK 505.76";

        when(validator.isInputValid(inputString)).thenReturn(TRUE);
        String[] arr = inputString.split("\\s+");
        String term = arr[3];
        when(formatter.format(term, 505.7606617945594))
                .thenReturn(
                        (new BigDecimal(505.7606617945594).setScale(2, RoundingMode.HALF_EVEN))
                                .doubleValue());
        assertEquals(expected, service.convertCurrency(inputString));
    }

    private Table<String, String, String> mockCurrencyCrossReferences() {
        Table<String, String, String> currencyCrossReferences = HashBasedTable.create();
        currencyCrossReferences.put("AUD", "DKK", "USD");
        currencyCrossReferences.put("USD", "DKK", "EUR");
        return currencyCrossReferences;
    }

    private Table<String, String, Double> mockCurrencyRates() {
        Table<String, String, Double> currencyRates = HashBasedTable.create();
        currencyRates.put("AUD", "USD", 0.8371);
        currencyRates.put("EUR", "USD", 1.2315);
        currencyRates.put("EUR", "DKK", 7.4405);
        return currencyRates;
    }
}
