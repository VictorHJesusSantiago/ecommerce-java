package com.ecommerce.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class PriceUtilTest {

    @Test
    void calculatePercentageDiscount_ReturnsCorrectValue() {
        BigDecimal result = PriceUtil.calculatePercentageDiscount(BigDecimal.valueOf(100), BigDecimal.valueOf(10));
        assertEquals(BigDecimal.valueOf(10.00), result);
    }

    @Test
    void calculateFixedDiscount_ReturnsCorrectValue() {
        BigDecimal result = PriceUtil.calculateFixedDiscount(BigDecimal.valueOf(100), BigDecimal.valueOf(25));
        assertEquals(BigDecimal.valueOf(75.00), result);
    }

    @Test
    void calculateFixedDiscount_OverAmount_ReturnsZero() {
        BigDecimal result = PriceUtil.calculateFixedDiscount(BigDecimal.valueOf(10), BigDecimal.valueOf(25));
        assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    void calculateTax_ReturnsCorrectValue() {
        BigDecimal result = PriceUtil.calculateTax(BigDecimal.valueOf(100), BigDecimal.valueOf(8.5));
        assertEquals(BigDecimal.valueOf(8.50), result);
    }

    @Test
    void calculateTotalWithTax_ReturnsCorrectValue() {
        BigDecimal result = PriceUtil.calculateTotalWithTax(BigDecimal.valueOf(100), BigDecimal.valueOf(10));
        assertEquals(BigDecimal.valueOf(110.00), result);
    }

    @Test
    void formatCurrency_ReturnsFormattedString() {
        String result = PriceUtil.formatCurrency(BigDecimal.valueOf(99.99), "USD");
        assertNotNull(result);
        assertTrue(result.contains("99.99"));
    }
}
