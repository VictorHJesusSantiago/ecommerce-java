package com.ecommerce.model.dto.request.order;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CheckoutRequest {

    @NotNull(message = "Shipping address is required")
    private Long shippingAddressId;

    private Long billingAddressId;

    @NotNull(message = "Shipping method is required")
    private Long shippingMethodId;

    @NotNull(message = "Payment method is required")
    private String paymentMethod;

    private String paymentToken;

    private String couponCode;

    private String giftCardCode;

    private String customerNote;

    private boolean sendEmailConfirmation = true;

    private boolean isGift;

    private String giftMessage;

    private List<CheckoutItemRequest> items;
}
