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
public class CartUpdatedEvent {
    private Long userId;
    private Long cartId;
    private int previousItemCount;
    private int currentItemCount;
    private BigDecimal previousTotal;
    private BigDecimal currentTotal;
    private LocalDateTime updatedAt;
}
