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
public class PurchaseOrderResponse {
    private Long id;
    private Long supplierId;
    private String supplierName;
    private String poNumber;
    private BigDecimal subtotal;
    private BigDecimal total;
    private BigDecimal shippingCost;
    private BigDecimal tax;
    private BigDecimal discount;
    private String status;
    private String paymentStatus;
    private String paymentTerms;
    private String notes;
    private LocalDateTime expectedDeliveryDate;
    private LocalDateTime actualDeliveryDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
