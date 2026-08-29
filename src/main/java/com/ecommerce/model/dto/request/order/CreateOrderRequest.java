package com.ecommerce.model.dto.request.order;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {
    @NotNull(message = "Shipping address ID is required")
    private Long shippingAddressId;

    private Long billingAddressId;

    @NotNull(message = "Shipping method is required")
    private Long shippingMethodId;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    private String paymentToken;

    private String couponCode;

    private String giftCardCode;

    private String notes;

    private String giftMessage;

    private boolean isGift;

    private List<OrderItemRequest> items;
}
