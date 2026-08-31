package com.ecommerce.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilTest {

    @Test
    void isValidEmail_Valid_ReturnsTrue() {
        assertTrue(ValidationUtil.isValidEmail("test@example.com"));
    }

    @Test
    void isValidEmail_Invalid_ReturnsFalse() {
        assertFalse(ValidationUtil.isValidEmail("invalid-email"));
        assertFalse(ValidationUtil.isValidEmail("test@"));
        assertFalse(ValidationUtil.isValidEmail("@example.com"));
    }

    @Test
    void isValidEmail_Null_ReturnsFalse() {
        assertFalse(ValidationUtil.isValidEmail(null));
    }

    @Test
    void isValidPhone_Valid_ReturnsTrue() {
        assertTrue(ValidationUtil.isValidPhone("+1234567890"));
        assertTrue(ValidationUtil.isValidPhone("1234567890"));
    }

    @Test
    void isValidPhone_Invalid_ReturnsFalse() {
        assertFalse(ValidationUtil.isValidPhone("123"));
        assertFalse(ValidationUtil.isValidPhone("abc"));
    }

    @Test
    void isStrongPassword_Strong_ReturnsTrue() {
        assertTrue(ValidationUtil.isStrongPassword("StrongP@ss1"));
    }

    @Test
    void isStrongPassword_Weak_ReturnsFalse() {
        assertFalse(ValidationUtil.isStrongPassword("weak"));
        assertFalse(ValidationUtil.isStrongPassword("nouppercase1!"));
        assertFalse(ValidationUtil.isStrongPassword("NOLOWERCASE1!"));
        assertFalse(ValidationUtil.isStrongPassword("NoDigits!"));
    }

    @Test
    void isValidUrl_Valid_ReturnsTrue() {
        assertTrue(ValidationUtil.isValidUrl("https://example.com"));
        assertTrue(ValidationUtil.isValidUrl("http://localhost:8080"));
    }

    @Test
    void isValidUrl_Invalid_ReturnsFalse() {
        assertFalse(ValidationUtil.isValidUrl("not-a-url"));
    }
}
