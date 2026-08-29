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
public class AbTestResponse {
    private Long id;
    private String name;
    private String description;
    private String type;
    private String status;
    private Integer trafficPercentage;
    private String controlVariant;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal confidenceLevel;
    private long totalParticipants;
    private long totalConversions;
    private BigDecimal conversionRate;
    private String winner;
    private LocalDateTime createdAt;
}
