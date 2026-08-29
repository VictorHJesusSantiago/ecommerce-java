package com.ecommerce.messaging.kafka;

import com.ecommerce.event.OrderCreatedEvent;
import com.ecommerce.event.PaymentCompletedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventKafkaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void sendOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Sending order created event for order: {}", event.getOrderNumber());
        kafkaTemplate.send("order.created", event);
    }

    public void sendOrderStatusChangedEvent(Object event) {
        log.info("Sending order status changed event");
        kafkaTemplate.send("order.status-changed", event);
    }

    public void sendPaymentCompletedEvent(PaymentCompletedEvent event) {
        log.info("Sending payment completed event for order: {}", event.getOrderNumber());
        kafkaTemplate.send("payment.completed", event);
    }

    public void sendPaymentFailedEvent(Object event) {
        log.info("Sending payment failed event");
        kafkaTemplate.send("payment.failed", event);
    }

    public void sendProductCreatedEvent(Object event) {
        log.info("Sending product created event");
        kafkaTemplate.send("product.created", event);
    }

    public void sendStockLowEvent(Object event) {
        log.info("Sending stock low event");
        kafkaTemplate.send("inventory.stock-low", event);
    }

    public void sendStockOutEvent(Object event) {
        log.info("Sending stock out event");
        kafkaTemplate.send("inventory.stock-out", event);
    }

    public void sendReviewCreatedEvent(Object event) {
        log.info("Sending review created event");
        kafkaTemplate.send("review.created", event);
    }
}
