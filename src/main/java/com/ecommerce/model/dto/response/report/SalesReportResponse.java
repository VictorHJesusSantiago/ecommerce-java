package com.ecommerce.model.dto.response.report;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesReportResponse {
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalSales;
    private long orderCount;
    private BigDecimal averageOrderValue;
    private BigDecimal totalDiscounts;
    private BigDecimal netSales;
    private List<DailySales> dailySales;
    private List<TopProduct> topProducts;
    private List<TopCategory> topCategories;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailySales {
        private LocalDate date;
        private BigDecimal revenue;
        private long orderCount;
        private long itemsSold;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopProduct {
        private Long productId;
        private String name;
        private String imageUrl;
        private long totalSold;
        private BigDecimal revenue;
        private BigDecimal averageRating;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopCategory {
        private Long categoryId;
        private String name;
        private long totalSold;
        private BigDecimal revenue;
    }
}
