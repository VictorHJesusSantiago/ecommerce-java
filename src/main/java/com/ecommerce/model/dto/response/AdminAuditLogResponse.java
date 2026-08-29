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
public class AdminAuditLogResponse {
    private Long id;
    private Long adminUserId;
    private String adminUserName;
    private String action;
    private String entityType;
    private Long entityId;
    private String entityName;
    private String ipAddress;
    private String notes;
    private LocalDateTime createdAt;
}
