package com.ecommerce.model.dto.request.cart;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ApplyCouponRequest {

    private String couponCode;

    private Long shippingAddressId;

    private Long shippingMethodId;
}
