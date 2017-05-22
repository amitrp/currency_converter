package com.amitph.curexchange.service;

import com.google.common.collect.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConverterService {

    @Autowired
    Table<String, String, Double> currencyRates;

    @Autowired
    Table<String, String, String> currencyCrossReference;

    @Autowired
    InputValidator validator;

    @Autowired
    CurrencyFormatter formatter;

    public String convertCurrency(String input) {
        if (!validator.isInputValid(input)) return "Invalid Input";

        String[] inputArray = input.trim().split("\\s+");
        String base = inputArray[0];
        Double ccy1 = Double.parseDouble(inputArray[1]);
        String term = inputArray[3];

        Double rate = calculateRate(base, term);
        return printableResult(base, term, ccy1, rate);

    }

    private String printableResult(String base, String term, Double ccy1, Double rate) {
        return
                rate == 0
                        ? "Unable to find rate for " + base + "/" + term
                        : base + " " + ccy1 + " = " + term + " " + (formatter.format(term, ccy1 * rate));
    }

    private Double calculateRate(String base, String term) {
        if (base.equals(term)) return 1d;
        Double multiplier = calculateRateDirect(base, term);
        return multiplier != 0 ? multiplier : calculateRateCrossReference(base, term);
    }

    private Double calculateRateDirect(String base, String term) {
        return currencyRates.contains(base, term) ? currencyRates.get(base, term) : (
                currencyRates.contains(term, base) ? (1 / currencyRates.get(term, base)) : 0d);
    }

    private Double calculateRateCrossReference(String base, String term) {
        if (currencyCrossReference.contains(base, term))
            return calculateRate(base, currencyCrossReference.get(base, term)) * calculateRate(currencyCrossReference.get(base, term), term);
        else if (currencyCrossReference.contains(term, base))
            return calculateRate(base, currencyCrossReference.get(term, base)) * calculateRate(currencyCrossReference.get(term, base), term);
        else return 0d;
    }
}