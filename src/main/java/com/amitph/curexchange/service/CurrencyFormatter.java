package com.amitph.curexchange.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyFormatter {
    private final Map<String, Integer> currencyFormats;

    public Double format(String currency, Double amount) {
        return (new BigDecimal(amount).setScale(getDecimalPlace(currency), RoundingMode.HALF_EVEN))
                .doubleValue();
    }

    private Integer getDecimalPlace(String currency) {
        return currencyFormats.computeIfAbsent(currency, k -> 2);
    }
}
