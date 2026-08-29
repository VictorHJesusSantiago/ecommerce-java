package com.ecommerce.model.dto.response.inventory;

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
public class InventoryItemResponse {
    private Long id;
    private Long productId;
    private String productName;
    private Long variantId;
    private String variantName;
    private Long warehouseId;
    private String warehouseName;
    private Long supplierId;
    private String supplierName;
    private Integer quantity;
    private Integer reservedQuantity;
    private Integer availableQuantity;
    private String status;
    private boolean inStock;
    private Integer reorderPoint;
    private Integer reorderQuantity;
    private Integer lowStockThreshold;
    private String binLocation;
    private String batchNumber;
    private String lotNumber;
    private String serialNumber;
    private LocalDateTime expiresAt;
    private LocalDateTime manufacturedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
