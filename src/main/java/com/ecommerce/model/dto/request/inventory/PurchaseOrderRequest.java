package com.ecommerce.model.dto.request.inventory;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOrderRequest {
    private Long supplierId;
    private BigDecimal subtotal;
    private BigDecimal total;
    private BigDecimal shippingCost;
    private BigDecimal tax;
    private BigDecimal discount;
    private String paymentTerms;
    private String notes;
    private LocalDateTime expectedDeliveryDate;
    private List<PurchaseOrderItemRequest> items;
}
