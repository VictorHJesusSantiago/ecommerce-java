package com.ecommerce.model.dto.request.order;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CheckoutItemRequest {

    private Long productId;

    private Long variantId;

    private int quantity;

    private boolean isGift;

    private String giftMessage;
}
