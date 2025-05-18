package com.bank.account.service.utils;

import org.springframework.stereotype.Component;

@Component
public class AccountNumberGenerator {
    private static final String BANK_CODE = "12";
    private static final String BRANCH_CODE = "983";

    private static long sequence = 10000001;

    public static synchronized String generateAccountNumber() {
        String base = BANK_CODE + BRANCH_CODE + String.format("%08d", sequence++);
        String checkDigit = calculateCheckDigit(base);
        return base + checkDigit;
    }

    private static String calculateCheckDigit(String input) {
        int sum = 0;
        for (int i = 0; i < input.length(); i++) {
            sum += Character.getNumericValue(input.charAt(i));
        }
        return String.valueOf(sum % 10);
    }
}