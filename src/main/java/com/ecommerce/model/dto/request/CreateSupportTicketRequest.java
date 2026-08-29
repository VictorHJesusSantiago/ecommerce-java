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
public class CreateSupportTicketRequest {

    @NotBlank(message = "Subject is required")
    @Size(min = 5, max = 200)
    private String subject;

    @NotBlank(message = "Message is required")
    @Size(min = 10, max = 5000)
    private String message;

    @Size(max = 50)
    private String category;

    @Size(max = 20)
    private String priority;

    private Long orderId;

    private Long productId;
}
