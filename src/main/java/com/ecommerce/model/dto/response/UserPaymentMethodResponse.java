package com.ecommerce.model.dto.response;

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
public class UserPaymentMethodResponse {
    private Long id;
    private String type;
    private String cardType;
    private String last4;
    private String expiryMonth;
    private String expiryYear;
    private String cardholderName;
    private boolean isDefault;
    private boolean isActive;
    private String billingAddress;
    private LocalDateTime createdAt;
}
