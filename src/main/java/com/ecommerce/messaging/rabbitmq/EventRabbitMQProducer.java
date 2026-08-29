package com.ecommerce.messaging.rabbitmq;

import com.ecommerce.event.OrderStatusChangedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventRabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendOrderCreatedEvent(Object event) {
        log.info("RabbitMQ: Sending order created event");
        rabbitTemplate.convertAndSend("order.created", event);
    }

    public void sendOrderStatusChangedEvent(OrderStatusChangedEvent event) {
        log.info("RabbitMQ: Sending order status changed event");
        rabbitTemplate.convertAndSend("order.status-changed", event);
    }

    public void sendPaymentCompletedEvent(Object event) {
        log.info("RabbitMQ: Sending payment completed event");
        rabbitTemplate.convertAndSend("payment.completed", event);
    }

    public void sendPaymentFailedEvent(Object event) {
        log.info("RabbitMQ: Sending payment failed event");
        rabbitTemplate.convertAndSend("payment.failed", event);
    }

    public void sendEmailEvent(Object event) {
        log.info("RabbitMQ: Sending email event");
        rabbitTemplate.convertAndSend("email.send", event);
    }

    public void sendNotificationEvent(Object event) {
        log.info("RabbitMQ: Sending notification event");
        rabbitTemplate.convertAndSend("notification.send", event);
    }
}
