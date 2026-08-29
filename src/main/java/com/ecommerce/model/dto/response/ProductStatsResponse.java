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
public class ProductStatsResponse {
    private Long productId;
    private long totalViews;
    private long dailyViews;
    private long weeklyViews;
    private long monthlyViews;
    private long totalSold;
    private long dailySold;
    private long weeklySold;
    private long monthlySold;
    private BigDecimal totalRevenue;
    private BigDecimal dailyRevenue;
    private BigDecimal weeklyRevenue;
    private BigDecimal monthlyRevenue;
    private long totalCartAdds;
    private long totalWishlistAdds;
    private Double conversionRate;
    private LocalDateTime lastViewedAt;
    private LocalDateTime lastSoldAt;
}
