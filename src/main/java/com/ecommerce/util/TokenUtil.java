package com.ecommerce.util;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public final class TokenUtil {

    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private TokenUtil() {}

    public static String generateRandomToken(int length) {
        byte[] bytes = new byte[length];
        SECURE_RANDOM.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String generateApiKey() {
        return "ak_" + generateRandomToken(32);
    }

    public static String generateSecretKey() {
        return "sk_" + generateRandomToken(48);
    }

    public static String generateWebhookSecret() {
        return "whsec_" + generateRandomToken(32);
    }

    public static String generateReferralCode() {
        return "REF-" + generateRandomToken(8).toUpperCase();
    }

    public static String generateVoucherCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(SECURE_RANDOM.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public static String generateOTP() {
        int otp = 100000 + SECURE_RANDOM.nextInt(900000);
        return String.valueOf(otp);
    }
}
