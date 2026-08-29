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
public class GiftCardCreatedEvent {
    private Long giftCardId;
    private String giftCardCode;
    private BigDecimal initialBalance;
    private Long createdByUserId;
    private LocalDateTime createdAt;
}
