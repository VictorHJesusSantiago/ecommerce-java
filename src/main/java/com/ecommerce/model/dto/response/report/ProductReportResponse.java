package com.ecommerce.model.dto.response.report;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReportResponse {
    private long totalProducts;
    private long activeProducts;
    private long inactiveProducts;
    private long outOfStock;
    private long lowStock;
    private long inStock;
    private double averageRating;
    private long totalReviews;
    private long totalSales;
    private double averageSellThroughRate;
}
