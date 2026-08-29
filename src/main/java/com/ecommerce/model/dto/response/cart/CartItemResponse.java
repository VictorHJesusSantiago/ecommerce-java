package com.ecommerce.model.dto.response.cart;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CartItemResponse {

    private Long id;
    private Long productId;
    private String productName;
    private String productSlug;
    private String productSku;
    private String productImage;
    private Long variantId;
    private String variantName;
    private String variantOptions;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal total;
    private BigDecimal discountAmount;
    private boolean inStock;
    private int availableQuantity;
    private boolean isActive;
    private boolean isGift;
    private String giftMessage;
    private List<String> warnings;
}
