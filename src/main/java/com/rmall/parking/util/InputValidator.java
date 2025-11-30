package com.rmall.parking.util;

import java.util.regex.Pattern;

public final class InputValidator {
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^\\d{10}$");
    private static final Pattern CAR_NUMBER_PATTERN = Pattern.compile("^[A-Z0-9-]+$");
    
    private InputValidator() {}
    
    public static boolean isValidMobileNumber(String mobile) {
        return mobile != null && MOBILE_PATTERN.matcher(mobile).matches();
    }
    
    public static boolean isValidCarNumber(String carNo) {
        if (carNo == null || carNo.isBlank()) {
            return false;
        }
        return CAR_NUMBER_PATTERN.matcher(carNo.toUpperCase()).matches();
    }
    
    public static boolean isValidName(String name) {
        return name != null && !name.isBlank() && name.length() >= 2;
    }
    
    public static boolean isValidToken(int token) {
        return token > 0 && token <= 29999;
    }
    
    public static String sanitize(String input) {
        return input == null ? "" : input.trim();
    }
}