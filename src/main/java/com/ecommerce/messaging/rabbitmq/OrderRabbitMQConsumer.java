package com.ecommerce.messaging.rabbitmq;

import com.ecommerce.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderRabbitMQConsumer {

    private final com.ecommerce.service.NotificationService notificationService;

    @RabbitListener(queues = "order.created")
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info("RabbitMQ: Received order created event: {}", event.getOrderNumber());
        notificationService.sendOrderNotification(event.getUserId(), event.getOrderNumber(), "Created");
    }

    @RabbitListener(queues = "order.status-changed")
    public void handleOrderStatusChanged(OrderStatusChangedEvent event) {
        log.info("RabbitMQ: Order {} status changed to {}", event.getOrderNumber(), event.getNewStatus());
        notificationService.sendOrderNotification(event.getUserId(), event.getOrderNumber(), event.getNewStatus());
    }

    @RabbitListener(queues = "payment.completed")
    public void handlePaymentCompleted(com.ecommerce.event.PaymentCompletedEvent event) {
        log.info("RabbitMQ: Payment completed for order: {}", event.getOrderNumber());
        notificationService.sendPaymentNotification(event.getUserId(), event.getOrderNumber(), "Completed");
    }

    @RabbitListener(queues = "payment.failed")
    public void handlePaymentFailed(com.ecommerce.event.PaymentFailedEvent event) {
        log.info("RabbitMQ: Payment failed for order: {}", event.getOrderNumber());
        notificationService.sendPaymentNotification(event.getUserId(), event.getOrderNumber(), "Failed");
    }
}
