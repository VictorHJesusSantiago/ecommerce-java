package com.ecommerce.model.dto.response.user;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PaymentMethodResponse {

    private Long id;
    private String paymentMethod;
    private String provider;
    private String last4Digits;
    private String expiryMonth;
    private String expiryYear;
    private String cardHolderName;
    private String cardType;
    private boolean isDefault;
    private LocalDateTime lastUsedAt;
    private LocalDateTime createdAt;
}
