package com.ecommerce.scheduler;

import com.ecommerce.repository.CouponRepository;
import com.ecommerce.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class MarketingScheduler {

    private final CouponRepository couponRepository;
    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 8 * * ?")
    public void checkExpiringCoupons() {
        log.info("Checking for expiring coupons");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = now.plusDays(3);
        var expiringCoupons = couponRepository.findExpiringSoon(now, deadline);
        log.info("Found {} coupons expiring within 3 days", expiringCoupons.size());
    }

    @Scheduled(cron = "0 0 9 * * MON")
    public void generateMarketingReport() {
        log.info("Generating weekly marketing report");
    }

    @Scheduled(cron = "0 0 7 * * ?")
    public void disableExpiredPromotions() {
        log.info("Disabling expired promotions and coupons");
    }
}
