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
public class SalesTrendDto {
    private String period;
    private BigDecimal totalSales;
    private long orderCount;
    private BigDecimal averageOrderValue;
    private BigDecimal growthRate;
}
