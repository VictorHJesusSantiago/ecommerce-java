package com.ecommerce.model.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentMethodRequest {

    private Long id;

    @NotBlank(message = "Payment method type is required")
    private String paymentMethod;

    @NotBlank(message = "Token is required")
    private String token;

    private String cardHolderName;

    private String billingAddressId;

    private boolean isDefault;
}
