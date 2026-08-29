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
public class FlashSaleResponse {
    private Long id;
    private String name;
    private String description;
    private String bannerUrl;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private boolean isActive;
    private Integer maxQuantityPerProduct;
    private long totalSold;
    private BigDecimal totalRevenue;
    private int itemsCount;
    private LocalDateTime createdAt;
}
