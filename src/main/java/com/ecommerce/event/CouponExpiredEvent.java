package com.ecommerce.event;

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
public class CouponExpiredEvent {
    private Long couponId;
    private String couponCode;
    private LocalDateTime expiredAt;
    private long totalUsageCount;
}
