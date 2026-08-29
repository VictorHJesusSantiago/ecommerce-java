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
public class ProductPriceLogResponse {
    private Long id;
    private Long productId;
    private String productName;
    private BigDecimal oldPrice;
    private BigDecimal newPrice;
    private BigDecimal oldCompareAtPrice;
    private BigDecimal newCompareAtPrice;
    private String changeType;
    private String reason;
    private String changedByName;
    private LocalDateTime createdAt;
}
