package com.ecommerce.scheduler;

import com.ecommerce.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryScheduler {

    private final InventoryItemRepository inventoryItemRepository;
    private final ProductRepository productRepository;

    @Scheduled(cron = "0 0 6 * * ?")
    public void checkLowStockItems() {
        log.info("Running low stock check");
        var warehouses = new java.util.ArrayList<com.ecommerce.model.entity.Warehouse>();
        for (var warehouse : warehouses) {
            var lowStockItems = inventoryItemRepository.findLowStockItems(warehouse.getId());
            if (!lowStockItems.isEmpty()) {
                log.warn("Found {} low stock items in warehouse: {}", lowStockItems.size(), warehouse.getName());
            }
        }
    }

    @Scheduled(cron = "0 0 6 * * MON")
    public void generateInventoryReport() {
        log.info("Generating weekly inventory report");
        var outOfStockItems = inventoryItemRepository.findOutOfStockItems();
        log.info("Found {} out of stock items", outOfStockItems.size());
    }
}
