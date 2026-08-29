package com.ecommerce.messaging.kafka;

import com.ecommerce.event.OrderCreatedEvent;
import com.ecommerce.event.OrderStatusChangedEvent;
import com.ecommerce.event.PaymentCompletedEvent;
import com.ecommerce.event.PaymentFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventKafkaConsumer {

    private final com.ecommerce.service.NotificationService notificationService;

    @KafkaListener(topics = "order.created", groupId = "ecommerce-order")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("Received order created event: {}", event.getOrderNumber());
        notificationService.sendOrderNotification(event.getUserId(), event.getOrderNumber(), "Created");
    }

    @KafkaListener(topics = "order.status-changed", groupId = "ecommerce-order")
    public void handleOrderStatusChanged(OrderStatusChangedEvent event) {
        log.info("Received order status changed event: {} -> {}", event.getOrderNumber(), event.getNewStatus());
        notificationService.sendOrderNotification(event.getUserId(), event.getOrderNumber(), event.getNewStatus());
    }

    @KafkaListener(topics = "payment.completed", groupId = "ecommerce-payment")
    public void handlePaymentCompleted(PaymentCompletedEvent event) {
        log.info("Received payment completed event for order: {}", event.getOrderNumber());
        notificationService.sendPaymentNotification(event.getUserId(), event.getOrderNumber(), "Completed");
    }

    @KafkaListener(topics = "payment.failed", groupId = "ecommerce-payment")
    public void handlePaymentFailed(PaymentFailedEvent event) {
        log.info("Received payment failed event for order: {}", event.getOrderNumber());
        notificationService.sendPaymentNotification(event.getUserId(), event.getOrderNumber(), "Failed");
    }
}
