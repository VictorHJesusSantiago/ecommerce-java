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
public class WebhookResponse {
    private Long id;
    private String name;
    private String url;
    private String events;
    private String description;
    private boolean isActive;
    private int retryCount;
    private int timeout;
    private LocalDateTime lastTriggeredAt;
    private Integer lastStatusCode;
    private long successCount;
    private long failureCount;
    private LocalDateTime createdAt;
}
