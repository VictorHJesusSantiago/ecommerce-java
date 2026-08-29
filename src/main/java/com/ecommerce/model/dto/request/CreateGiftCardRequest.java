package com.ecommerce.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateGiftCardRequest {

    @NotNull(message = "Initial balance is required")
    @DecimalMin(value = "1.00", message = "Balance must be at least 1")
    @DecimalMax(value = "10000.00", message = "Balance must be at most 10000")
    private BigDecimal initialBalance;

    @NotBlank(message = "Recipient email is required")
    @Email(message = "Invalid email format")
    private String recipientEmail;

    @Size(max = 100)
    private String recipientName;

    @Size(max = 100)
    private String senderName;

    @Size(max = 500)
    private String message;

    private Integer quantity;

    private Long expiresAfterDays;

    @Size(max = 20)
    private String currency;
}
