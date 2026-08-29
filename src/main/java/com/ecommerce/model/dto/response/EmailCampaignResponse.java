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
public class EmailCampaignResponse {
    private Long id;
    private String name;
    private String subject;
    private String type;
    private String status;
    private long totalRecipients;
    private long totalSent;
    private long totalDelivered;
    private long totalOpened;
    private long totalClicked;
    private long totalBounced;
    private BigDecimal openRate;
    private BigDecimal clickRate;
    private BigDecimal bounceRate;
    private LocalDateTime sentAt;
    private LocalDateTime createdAt;
}
