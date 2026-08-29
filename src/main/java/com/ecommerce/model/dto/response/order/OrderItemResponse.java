package com.ecommerce.model.dto.response.order;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItemResponse {

    private Long id;
    private Long productId;
    private String productName;
    private String productSlug;
    private String productSku;
    private String productImage;
    private Long variantId;
    private String variantName;
    private String variantOptions;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal totalDiscount;
    private BigDecimal taxAmount;
    private BigDecimal total;
    private boolean isFulfilled;
    private int fulfilledQuantity;
    private boolean requiresShipping;
    private boolean isGift;
    private String giftMessage;
}
