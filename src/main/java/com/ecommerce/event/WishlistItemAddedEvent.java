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
public class WishlistItemAddedEvent {
    private Long userId;
    private Long wishlistId;
    private Long productId;
    private String productName;
    private BigDecimal productPrice;
    private LocalDateTime addedAt;
}
