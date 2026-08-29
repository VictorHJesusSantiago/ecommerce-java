package com.ecommerce.model.dto.request.inventory;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemRequest {
    private Long productId;
    private Long variantId;
    private Long warehouseId;
    private Long supplierId;
    private Integer quantity;
    private Integer reorderPoint;
    private Integer reorderQuantity;
    private Integer lowStockThreshold;
    private String binLocation;
    private String batchNumber;
    private String lotNumber;
    private String serialNumber;
    private LocalDateTime expiresAt;
    private LocalDateTime manufacturedAt;
}
