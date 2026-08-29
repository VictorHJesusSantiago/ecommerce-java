package com.ecommerce.listener;

import com.ecommerce.event.StockLowEvent;
import com.ecommerce.event.StockOutEvent;
import com.ecommerce.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryEventListener {

    private final NotificationService notificationService;

    @Async
    @EventListener
    public void handleStockLow(StockLowEvent event) {
        log.warn("Low stock alert for product: {} (SKU: {}), Current: {}, Threshold: {}",
                event.getProductName(), event.getSku(), event.getCurrentStock(), event.getThreshold());
    }

    @Async
    @EventListener
    public void handleStockOut(StockOutEvent event) {
        log.warn("Out of stock alert for product: {} (SKU: {})", event.getProductName(), event.getSku());
    }
}
