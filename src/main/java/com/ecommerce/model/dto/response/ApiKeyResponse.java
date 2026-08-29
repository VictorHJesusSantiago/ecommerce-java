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
public class ApiKeyResponse {
    private Long id;
    private String name;
    private String keyPrefix;
    private String scopes;
    private boolean isActive;
    private LocalDateTime expiresAt;
    private LocalDateTime lastUsedAt;
    private long usageCount;
    private Integer rateLimit;
    private String description;
    private LocalDateTime createdAt;
}
