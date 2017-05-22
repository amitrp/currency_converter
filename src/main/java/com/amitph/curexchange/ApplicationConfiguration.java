package com.amitph.curexchange;

import static java.util.stream.Collectors.toMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

@Configuration
public class ApplicationConfiguration {
    Map<String, Integer> currencyFormats;
    Table<String, String, Double> currencyRates;
    Table<String, String, String> currencyCrossReference;

    @Bean
    public Map<String, Integer> currencyFormats() {
        return currencyFormats;
    }

    @Bean
    public Table<String, String, Double> currencyRates() {
        return currencyRates;
    }

    @Bean
    public Table<String, String, String> currencyCrossReference() {
        return currencyCrossReference;
    }

    @Value(value = "classpath:input/currency_formats.txt")
    private Resource currencyFormatsTxt;

    @Value(value = "classpath:input/currency_rates.txt")
    private Resource currencyRateTxt;

    @Value(value = "classpath:input/currency_cross_reference.txt")
    private Resource currencyCrossReferenceTxt;

    @PostConstruct
    public void init() throws Exception {
        setCurrencyFormats();
        setCurrencyRates();
        setCurrencyCrossReference();
    }

    private void setCurrencyFormats() throws IOException {
        try (Stream<String> stream = Files.lines(Paths.get(currencyFormatsTxt.getFile().getPath()))) {
            currencyFormats = stream.map(s -> s.split("="))
                    .collect(toMap(a -> a[0], a -> Integer.parseInt(a[1])));
        }
    }

    private void setCurrencyRates() throws IOException {
        currencyRates = HashBasedTable.create();
        try (Stream<String> stream = Files.lines(Paths.get(currencyRateTxt.getFile().getPath()))) {
            stream.map(s -> s.split("=")).forEach(arr ->
                    currencyRates.put(arr[0].substring(0, 3), arr[0].substring(3), Double.parseDouble(arr[1]))
            );
        }
    }

    private void setCurrencyCrossReference() throws IOException {
        currencyCrossReference = HashBasedTable.create();
        try (Stream<String> stream = Files.lines(Paths.get(currencyCrossReferenceTxt.getFile().getPath()))) {
            stream.map(s -> s.split("=")).forEach(arr ->
                    currencyCrossReference.put(arr[0].substring(0, 3), arr[0].substring(3), arr[1])
            );
        }
    }
}