package com.ecommerce.model.dto.request.payment;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessPaymentRequest {
    @NotNull(message = "Order ID is required")
    private Long orderId;

    @NotBlank(message = "Payment method is required")
    private String paymentMethod;

    private String paymentToken;

    private String cardNumber;

    private String cardExpiryMonth;

    private String cardExpiryYear;

    private String cardCvv;

    private String cardHolderName;

    private String billingAddressId;

    private String currency;

    private String returnUrl;

    private String cancelUrl;

    private String note;
}
