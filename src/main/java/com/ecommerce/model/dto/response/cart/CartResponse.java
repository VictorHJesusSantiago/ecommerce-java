package com.ecommerce.model.dto.response.cart;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CartResponse {

    private Long id;
    private List<CartItemResponse> items;
    private int itemCount;
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal shippingEstimate;
    private BigDecimal discountAmount;
    private BigDecimal total;
    private String currency;
    private String couponCode;
    private String couponDescription;
    private boolean hasOutOfStockItems;
    private List<String> warnings;
}
