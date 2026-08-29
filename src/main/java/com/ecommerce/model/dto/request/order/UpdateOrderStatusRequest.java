package com.ecommerce.model.dto.request.order;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatusRequest {
    @NotBlank(message = "Status is required")
    private String status;

    private String note;

    private String trackingNumber;

    private String carrier;
}
