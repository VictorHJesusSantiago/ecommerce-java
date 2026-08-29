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
public class WebhookLogResponse {
    private Long id;
    private Long webhookId;
    private String webhookName;
    private String event;
    private Integer responseCode;
    private String status;
    private Long durationMs;
    private String errorMessage;
    private int retryCount;
    private LocalDateTime createdAt;
}
