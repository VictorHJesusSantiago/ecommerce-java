package com.ecommerce.model.dto.response.marketing;

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
public class PromotionResponse {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String content;
    private String imageUrl;
    private String bannerUrl;
    private boolean isActive;
    private LocalDateTime startsAt;
    private LocalDateTime expiresAt;
    private int priority;
    private long usageCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
