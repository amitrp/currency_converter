package com.amitph.curexchange.service;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class InputValidator {
    public boolean isInputValid(String inputString) {

        if (!StringUtils.hasText(inputString)) {
            return false;
        }

        String[] inputArray = inputString.trim().split("\\s+");
        return inputArray.length == 4 && isNumeric(inputArray[1]);
    }

    private boolean isNumeric(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
