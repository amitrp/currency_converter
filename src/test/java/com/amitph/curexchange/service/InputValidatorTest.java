package com.amitph.curexchange.service;

import static java.lang.Boolean.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class InputValidatorTest {
    InputValidator validator;

    @Before
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

        for (String input : inputStringExpectedBoolean.keySet())
            Assert.assertEquals(inputStringExpectedBoolean.get(input), validator.isInputValid(input));
    }

    @Test
    public void invalidAmountInInputInvalidatedCorrectly() {
        Map<String, Boolean> inputStringExpectedBoolean = new HashMap<>();
        inputStringExpectedBoolean.put("AUD xx.00 in USD", FALSE);
        inputStringExpectedBoolean.put("AUD  USD", FALSE);

        for (String input : inputStringExpectedBoolean.keySet())
            Assert.assertEquals(inputStringExpectedBoolean.get(input), validator.isInputValid(input));
    }
}