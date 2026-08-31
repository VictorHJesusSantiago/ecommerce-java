package com.ecommerce.util;

import java.util.regex.Pattern;

public final class ValidationUtil {

    private ValidationUtil() {}

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^\\+?[0-9]{10,15}$"
    );

    private static final Pattern URL_PATTERN = Pattern.compile(
            "^https?://.*"
    );

    private static final Pattern POSTAL_CODE_PATTERN = Pattern.compile(
            "^[0-9]{5}(-[0-9]{4})?$"
    );

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone.replaceAll("[\\s()-]", "")).matches();
    }

    public static boolean isValidUrl(String url) {
        return url != null && URL_PATTERN.matcher(url).matches();
    }

    public static boolean isValidPostalCode(String postalCode) {
        return postalCode != null && POSTAL_CODE_PATTERN.matcher(postalCode).matches();
    }

    public static boolean isValidCreditCardNumber(String cardNumber) {
        if (cardNumber == null) return false;
        String clean = cardNumber.replaceAll("[\\s-]", "");
        return clean.matches("^[0-9]{13,19}$");
    }

    public static boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) return false;
        boolean hasUpper = password.chars().anyMatch(Character::isUpperCase);
        boolean hasLower = password.chars().anyMatch(Character::isLowerCase);
        boolean hasDigit = password.chars().anyMatch(Character::isDigit);
        boolean hasSpecial = password.chars().anyMatch(c -> "!@#$%^&*()_+-=[]{}|;':\",./<>?".indexOf(c) >= 0);
        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
}
