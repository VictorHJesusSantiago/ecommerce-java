package com.ecommerce.listener;

import com.ecommerce.event.OrderCreatedEvent;
import com.ecommerce.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventListener {

    private final NotificationService notificationService;

    @Async
    @EventListener
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Processing order created event for order: {}", event.getOrderNumber());

        notificationService.sendOrderNotification(
                event.getUserId(),
                event.getOrderNumber(),
                "CONFIRMED"
        );

        log.info("Order creation event processed for order: {}", event.getOrderNumber());
    }
}
