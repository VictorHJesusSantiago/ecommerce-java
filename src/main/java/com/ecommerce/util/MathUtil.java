package com.ecommerce.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MathUtil {

    private MathUtil() {}

    public static BigDecimal calculatePercentage(BigDecimal value, BigDecimal percentage) {
        return value.multiply(percentage).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateDiscount(BigDecimal originalPrice, BigDecimal discountPercentage) {
        BigDecimal discount = calculatePercentage(originalPrice, discountPercentage);
        return originalPrice.subtract(discount);
    }

    public static BigDecimal calculateMarkup(BigDecimal cost, BigDecimal markupPercentage) {
        BigDecimal markup = calculatePercentage(cost, markupPercentage);
        return cost.add(markup);
    }

    public static BigDecimal calculateMargin(BigDecimal sellingPrice, BigDecimal cost) {
        if (sellingPrice.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        BigDecimal profit = sellingPrice.subtract(cost);
        return profit.multiply(BigDecimal.valueOf(100)).divide(sellingPrice, 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal roundHalfUp(BigDecimal value, int scale) {
        return value.setScale(scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal roundHalfDown(BigDecimal value, int scale) {
        return value.setScale(scale, RoundingMode.HALF_DOWN);
    }

    public static BigDecimal roundUp(BigDecimal value, int scale) {
        return value.setScale(scale, RoundingMode.CEILING);
    }

    public static BigDecimal roundDown(BigDecimal value, int scale) {
        return value.setScale(scale, RoundingMode.FLOOR);
    }

    public static BigDecimal calculateAverage(BigDecimal sum, long count) {
        if (count == 0) return BigDecimal.ZERO;
        return sum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateCompoundInterest(BigDecimal principal, BigDecimal rate, int periods) {
        BigDecimal multiplier = BigDecimal.ONE.add(rate.divide(BigDecimal.valueOf(100), 10, RoundingMode.HALF_UP));
        return principal.multiply(multiplier.pow(periods)).setScale(2, RoundingMode.HALF_UP);
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public static long clamp(long value, long min, long max) {
        return Math.max(min, Math.min(max, value));
    }
}
