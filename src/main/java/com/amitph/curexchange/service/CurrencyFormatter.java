package com.amitph.curexchange.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Component
public class CurrencyFormatter {
    @Autowired
    Map<String, Integer> currencyFormats;

    public Double format(String currency, Double amount) {
        return (new BigDecimal(amount).setScale(getDecimalPlace(currency), RoundingMode.HALF_EVEN)).doubleValue();
    }

    private Integer getDecimalPlace(String currency) {
        return currencyFormats.computeIfAbsent(currency, k -> 2);
    }
}