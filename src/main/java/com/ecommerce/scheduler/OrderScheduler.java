package com.ecommerce.scheduler;

import com.ecommerce.model.enums.OrderStatus;
import com.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderScheduler {

    private final OrderRepository orderRepository;

    @Scheduled(cron = "0 0 * * * ?")
    public void checkPendingPayments() {
        log.info("Checking for orders with pending payments");
        LocalDateTime cutoff = LocalDateTime.now().minusHours(2);
        var unpaidOrders = orderRepository.findUnpaidOrdersBefore(cutoff);
        log.info("Found {} orders with pending payments older than 2 hours", unpaidOrders.size());
    }

    @Scheduled(cron = "0 */15 * * * ?")
    public void processPendingShipments() {
        log.debug("Processing pending shipments");
        var pendingShipments = orderRepository.findPendingShipments();
        log.debug("Found {} orders pending shipment", pendingShipments.size());
    }

    @Scheduled(cron = "0 0 5 * * ?")
    public void autoCancelExpiredOrders() {
        log.info("Auto-cancelling expired orders");
        LocalDateTime cutoff = LocalDateTime.now().minusDays(3);
        var expiredOrders = orderRepository.findByStatusAndDateRange(OrderStatus.PENDING, LocalDateTime.MIN, cutoff);
        for (var order : expiredOrders) {
            order.setStatus(OrderStatus.CANCELLED);
            order.setCancelledAt(LocalDateTime.now());
            order.setCancelReason("Auto-cancelled: Payment not received within 3 days");
            orderRepository.save(order);
            log.info("Auto-cancelled order: {}", order.getOrderNumber());
        }
    }
}
