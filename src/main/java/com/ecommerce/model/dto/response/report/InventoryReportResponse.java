package com.ecommerce.model.dto.response.report;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryReportResponse {
    private long totalItems;
    private long inStockItems;
    private long lowStockItems;
    private long outOfStockItems;
    private long totalWarehouses;
    private long totalSuppliers;
    private long pendingPurchaseOrders;
    private long receivedPurchaseOrders;
}
