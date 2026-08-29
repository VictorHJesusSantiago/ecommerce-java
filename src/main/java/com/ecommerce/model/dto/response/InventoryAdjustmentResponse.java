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
public class InventoryAdjustmentResponse {
    private Long id;
    private String adjustmentNumber;
    private Long warehouseId;
    private String warehouseName;
    private String reason;
    private String notes;
    private String status;
    private int totalItems;
    private BigDecimal totalValueChange;
    private String performedByName;
    private String approvedByName;
    private LocalDateTime approvedAt;
    private LocalDateTime createdAt;
}
