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
public class UserSessionResponse {
    private Long id;
    private String ipAddress;
    private String userAgent;
    private String deviceType;
    private String browser;
    private String os;
    private String location;
    private boolean isActive;
    private LocalDateTime lastActiveAt;
    private LocalDateTime createdAt;
}
