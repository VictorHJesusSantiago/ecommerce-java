package com.ecommerce.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public final class PriceUtil {

    private PriceUtil() {}

    public static BigDecimal calculatePercentageDiscount(BigDecimal price, BigDecimal percentage) {
        return price.multiply(percentage).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateFixedDiscount(BigDecimal price, BigDecimal fixedAmount) {
        return price.subtract(fixedAmount).max(BigDecimal.ZERO);
    }

    public static BigDecimal calculateTax(BigDecimal amount, BigDecimal taxRate) {
        return amount.multiply(taxRate).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateTotalWithTax(BigDecimal amount, BigDecimal taxRate) {
        BigDecimal tax = calculateTax(amount, taxRate);
        return amount.add(tax);
    }

    public static BigDecimal calculateDiscountedPrice(BigDecimal originalPrice, BigDecimal discountAmount) {
        return originalPrice.subtract(discountAmount).max(BigDecimal.ZERO);
    }

    public static String formatCurrency(BigDecimal amount, String currencyCode) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        format.setCurrency(Currency.getInstance(currencyCode));
        return format.format(amount);
    }

    public static BigDecimal roundToTwoDecimals(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal calculateShipping(BigDecimal weight, BigDecimal ratePerKg, BigDecimal baseRate) {
        return baseRate.add(weight.multiply(ratePerKg));
    }

    public static BigDecimal calculateBuyXGetY(BigDecimal price, int buyQty, int getQty, int totalQty) {
        int payingItems = Math.min(totalQty, buyQty);
        int freeItems = Math.max(0, totalQty - payingItems);
        int itemsToPay = Math.min(totalQty, buyQty + Math.max(0, totalQty - buyQty - getQty));
        return price.multiply(BigDecimal.valueOf(itemsToPay));
    }
}
