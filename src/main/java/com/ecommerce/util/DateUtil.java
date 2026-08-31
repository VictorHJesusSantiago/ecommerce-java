package com.ecommerce.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public final class DateUtil {

    private DateUtil() {}

    public static boolean isExpired(LocalDateTime dateTime) {
        return dateTime != null && LocalDateTime.now().isAfter(dateTime);
    }

    public static boolean isWithinDays(LocalDateTime dateTime, long days) {
        if (dateTime == null) return false;
        long daysBetween = ChronoUnit.DAYS.between(LocalDateTime.now(), dateTime);
        return daysBetween >= 0 && daysBetween <= days;
    }

    public static LocalDateTime daysAgo(int days) {
        return LocalDateTime.now().minusDays(days);
    }

    public static LocalDateTime daysFromNow(int days) {
        return LocalDateTime.now().plusDays(days);
    }

    public static LocalDateTime hoursAgo(int hours) {
        return LocalDateTime.now().minusHours(hours);
    }

    public static LocalDateTime monthsAgo(int months) {
        return LocalDateTime.now().minusMonths(months);
    }

    public static long daysBetween(LocalDateTime start, LocalDateTime end) {
        return ChronoUnit.DAYS.between(start, end);
    }
}
