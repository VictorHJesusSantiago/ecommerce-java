package com.ecommerce.model.dto.request.inventory;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderItemRequest {
    private Long productId;
    private Long variantId;
    private Integer quantity;
    private BigDecimal unitCost;
    private BigDecimal totalCost;
    private String notes;
}
