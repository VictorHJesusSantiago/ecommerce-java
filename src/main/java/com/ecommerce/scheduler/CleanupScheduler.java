package com.ecommerce.scheduler;

import com.ecommerce.repository.*;
import com.ecommerce.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class CleanupScheduler {

    private final CartRepository cartRepository;
    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanupAbandonedCarts() {
        log.info("Running abandoned cart cleanup");
        LocalDateTime cutoff = LocalDateTime.now().minusDays(7);
        var abandonedCarts = cartRepository.findAbandonedCartsBefore(cutoff);
        log.info("Found {} abandoned carts to cleanup", abandonedCarts.size());
    }

    @Scheduled(cron = "0 0 3 * * ?")
    public void cleanupOldAuditLogs() {
        log.info("Running audit log cleanup");
        LocalDateTime cutoff = LocalDateTime.now().minusDays(90);
        auditLogRepository.deleteByCreatedAtBefore(cutoff);
        log.info("Audit log cleanup completed");
    }

    @Scheduled(cron = "0 0 4 * * ?")
    public void cleanupUnverifiedUsers() {
        log.info("Running unverified user cleanup");
        LocalDateTime cutoff = LocalDateTime.now().minusDays(30);
        var unverifiedUsers = userRepository.findUnverifiedUsersBefore(cutoff);
        log.info("Found {} unverified users older than 30 days", unverifiedUsers.size());
    }

    @Scheduled(cron = "0 0 1 * * ?")
    public void cleanupExpiredSessions() {
        log.info("Running expired session cleanup");
    }

    @Scheduled(cron = "0 */30 * * * ?")
    public void releaseExpiredCartReservations() {
        log.debug("Checking for expired cart reservations");
    }
}
