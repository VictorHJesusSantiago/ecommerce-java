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
public class UrlRedirectResponse {
    private Long id;
    private String fromUrl;
    private String toUrl;
    private String type;
    private boolean isActive;
    private long hitCount;
    private LocalDateTime lastHitAt;
    private LocalDateTime createdAt;
}
