package com.ecommerce.model.dto.response;

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
public class InventoryAlertDto {
    private Long productId;
    private String productName;
    private String sku;
    private int currentStock;
    private int lowStockThreshold;
    private int warehouseCount;
    private LocalDateTime lastRestockedAt;
    private BigDecimal estimatedValue;
}
