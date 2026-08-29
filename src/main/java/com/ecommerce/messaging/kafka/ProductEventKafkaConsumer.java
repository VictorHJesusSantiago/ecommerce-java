package com.ecommerce.messaging.kafka;

import com.ecommerce.event.ProductCreatedEvent;
import com.ecommerce.event.StockLowEvent;
import com.ecommerce.event.StockOutEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductEventKafkaConsumer {

    private final com.ecommerce.service.NotificationService notificationService;
    private final com.ecommerce.repository.UserRepository userRepository;

    @KafkaListener(topics = "product.created", groupId = "ecommerce-product")
    public void handleProductCreated(ProductCreatedEvent event) {
        log.info("Received product created event: {}", event.getProductName());
    }

    @KafkaListener(topics = "inventory.stock-low", groupId = "ecommerce-inventory")
    public void handleStockLow(StockLowEvent event) {
        log.warn("Low stock alert for product {}: {} remaining", event.getProductName(), event.getCurrentQuantity());
        userRepository.findByEmail("admin@ecommerce.com").ifPresent(admin ->
                notificationService.sendNotification(admin.getId(), "Low Stock Alert",
                        "Product " + event.getProductName() + " has low stock (" + event.getCurrentQuantity() + " remaining)",
                        com.ecommerce.model.enums.NotificationCategory.INVENTORY_ALERT)
        );
    }

    @KafkaListener(topics = "inventory.stock-out", groupId = "ecommerce-inventory")
    public void handleStockOut(StockOutEvent event) {
        log.error("Out of stock alert for product {}", event.getProductName());
        userRepository.findByEmail("admin@ecommerce.com").ifPresent(admin ->
                notificationService.sendNotification(admin.getId(), "Out of Stock Alert",
                        "Product " + event.getProductName() + " is now out of stock",
                        com.ecommerce.model.enums.NotificationCategory.INVENTORY_ALERT)
        );
    }
}
