package com.amitph.curexchange.service;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class InputValidator {
    public Boolean isInputValid(String inputString) {
        if (StringUtils.isEmpty(inputString))
            return FALSE;

        String[] inputArray = inputString.trim().split("\\s+");
        if (inputArray.length != 4)
            return FALSE;

        try {
            Double.parseDouble(inputArray[1]);
        } catch (NumberFormatException nfe) {
            return FALSE;
        }
        return TRUE;
    }
}