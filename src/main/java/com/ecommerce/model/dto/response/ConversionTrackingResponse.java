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
public class ConversionTrackingResponse {
    private Long id;
    private Long userId;
    private String sessionId;
    private Long orderId;
    private BigDecimal orderTotal;
    private String firstTouchSource;
    private String lastTouchSource;
    private String utmSource;
    private String utmMedium;
    private String utmCampaign;
    private String landingPage;
    private String referrerUrl;
    private LocalDateTime createdAt;
}
