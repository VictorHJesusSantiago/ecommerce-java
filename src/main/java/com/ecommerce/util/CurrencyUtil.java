package com.ecommerce.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Locale;

public final class CurrencyUtil {

    private CurrencyUtil() {}

    public static String format(BigDecimal amount, String currencyCode) {
        Currency currency = Currency.getInstance(currencyCode);
        return String.format(Locale.US, "%s %,.2f", currency.getSymbol(), amount);
    }

    public static String formatUSD(BigDecimal amount) {
        return format(amount, "USD");
    }

    public static BigDecimal convert(BigDecimal amount, BigDecimal rate) {
        return amount.multiply(rate).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal convert(BigDecimal amount, BigDecimal fromRate, BigDecimal toRate) {
        BigDecimal usdAmount = amount.divide(fromRate, 10, RoundingMode.HALF_UP);
        return usdAmount.multiply(toRate).setScale(2, RoundingMode.HALF_UP);
    }

    public static boolean isNegative(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    public static boolean isPositive(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean isZero(BigDecimal amount) {
        return amount.compareTo(BigDecimal.ZERO) == 0;
    }

    public static BigDecimal abs(BigDecimal amount) {
        return amount.abs();
    }

    public static BigDecimal max(BigDecimal a, BigDecimal b) {
        return a.max(b);
    }

    public static BigDecimal min(BigDecimal a, BigDecimal b) {
        return a.min(b);
    }

    public static BigDecimal calculatePercentage(BigDecimal amount, BigDecimal percentage) {
        return amount.multiply(percentage).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateDiscount(BigDecimal price, BigDecimal discountPercentage) {
        BigDecimal discount = calculatePercentage(price, discountPercentage);
        return price.subtract(discount).setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateTax(BigDecimal amount, BigDecimal taxRate) {
        return calculatePercentage(amount, taxRate);
    }

    public static BigDecimal calculateShipping(BigDecimal weight, BigDecimal ratePerKg) {
        return weight.multiply(ratePerKg).setScale(2, RoundingMode.HALF_UP);
    }
}
