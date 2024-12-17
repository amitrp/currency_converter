package com.amitph.curexchange;

import com.amitph.curexchange.service.CurrencyConverterService;
import java.io.Console;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class Application implements CommandLineRunner {
    private final CurrencyConverterService currencyConverterService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        String input = "";
        Console console = System.console();
        while (!input.equalsIgnoreCase("exit")) {
            input = console.readLine("> ");
            System.out.println(currencyConverterService.convertCurrency(input.toUpperCase()));
        }
    }
}
