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
public class MarketingCampaignDto {
    private Long id;
    private String name;
    private String type;
    private String status;
    private BigDecimal budget;
    private BigDecimal spent;
    private long impressions;
    private long clicks;
    private long conversions;
    private BigDecimal ctr;
    private BigDecimal conversionRate;
    private BigDecimal roi;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
