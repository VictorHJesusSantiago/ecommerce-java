package com.ecommerce.util;

import java.text.Normalizer;
import java.util.Locale;
import java.util.UUID;

public final class SlugUtil {

    private SlugUtil() {}

    public static String toSlug(String input) {
        if (input == null || input.isBlank()) return "";
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}", "")
                .toLowerCase(Locale.ENGLISH)
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("[\\s-]+", "-")
                .replaceAll("^-|-$", "");
    }

    public static String generateUniqueSlug(String baseSlug) {
        return baseSlug + "-" + UUID.randomUUID().toString().substring(0, 8);
    }
}
