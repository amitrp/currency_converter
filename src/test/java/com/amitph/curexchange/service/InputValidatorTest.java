package com.amitph.curexchange.service;

import static java.lang.Boolean.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InputValidatorTest {
    private InputValidator validator;

    @BeforeEach
    public void setup() {
        validator = new InputValidator();
    }

    @Test
    public void checkIfInputStringIsValidatedCorrectly() {
        Map<String, Boolean> inputStringExpectedBoolean = new HashMap<>();
        inputStringExpectedBoolean.put("AUD 100.00 in USD", TRUE);
        inputStringExpectedBoolean.put("AUD 100.00  USD", FALSE);
        inputStringExpectedBoolean.put("AUD 100.00 in", FALSE);
        inputStringExpectedBoolean.put("  AUD 100.00 in USD  ", TRUE);
        inputStringExpectedBoolean.put("AUD     100.00 in        USD", TRUE);

        for (String input : inputStringExpectedBoolean.keySet()) {
            assertEquals(inputStringExpectedBoolean.get(input), validator.isInputValid(input));
        }
    }

    @Test
    public void invalidAmountInInputInvalidatedCorrectly() {
        Map<String, Boolean> inputStringExpectedBoolean = new HashMap<>();
        inputStringExpectedBoolean.put("AUD xx.00 in USD", FALSE);
        inputStringExpectedBoolean.put("AUD  USD", FALSE);

        for (String input : inputStringExpectedBoolean.keySet())
            assertEquals(inputStringExpectedBoolean.get(input), validator.isInputValid(input));
    }
}
