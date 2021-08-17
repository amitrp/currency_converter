package com.amitph.curexchange;

import com.amitph.curexchange.service.CurrencyConverterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.Console;

@SpringBootApplication
public class Application implements CommandLineRunner {
    @Autowired
    CurrencyConverterService currencyConverterService;

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