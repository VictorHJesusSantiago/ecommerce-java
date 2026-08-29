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
public class GiftCardRedeemedEvent {
    private Long giftCardId;
    private String giftCardCode;
    private Long userId;
    private BigDecimal amount;
    private BigDecimal remainingBalance;
    private LocalDateTime redeemedAt;
}
