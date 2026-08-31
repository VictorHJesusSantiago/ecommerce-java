package com.ecommerce.service;

import com.ecommerce.model.entity.InventoryItem;
import com.ecommerce.model.entity.Warehouse;

import java.util.List;

public interface InventoryService {

    List<InventoryItem> getInventoryByProduct(Long productId);

    List<InventoryItem> getInventoryByWarehouse(Long warehouseId);

    InventoryItem createInventoryItem(InventoryItem item);

    void adjustStock(Long inventoryItemId, int quantityChange, String reason);

    void reserveStock(Long productId, Long variantId, int quantity);

    void releaseStock(Long productId, Long variantId, int quantity);

    void fulfillStock(Long productId, Long variantId, int quantity);

    int getAvailableStock(Long productId);

    int getAvailableStockForVariant(Long variantId);

    List<InventoryItem> getLowStockItems(Long warehouseId);

    List<InventoryItem> getOutOfStockItems();

    Warehouse createWarehouse(Warehouse warehouse);

    Warehouse updateWarehouse(Long id, Warehouse warehouse);

    Warehouse getDefaultWarehouse();

    List<Warehouse> getAllWarehouses();
}
