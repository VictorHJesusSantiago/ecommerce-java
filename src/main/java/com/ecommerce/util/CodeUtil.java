package com.ecommerce.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public final class CodeUtil {

    private CodeUtil() {}

    public static String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    public static String generateRefundNumber() {
        return "REF-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    public static String generateTransactionNumber() {
        return "TX-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    public static String generateReturnNumber() {
        return "RET-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    public static String generateTicketNumber() {
        return "TKT-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    public static String generateGiftCardCode() {
        return "GC-" + UUID.randomUUID().toString().substring(0, 16).toUpperCase().replace("-", "");
    }

    public static String generateVerificationToken() {
        return UUID.randomUUID().toString() + "-" + UUID.randomUUID().toString();
    }

    public static String generatePoNumber() {
        return "PO-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }

    public static String generateCouponCode(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt((int) (Math.random() * chars.length())));
        }
        return sb.toString();
    }
}
