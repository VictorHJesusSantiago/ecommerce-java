package com.ecommerce.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmReturnRequest {

    @NotNull(message = "Return ID is required")
    private Long returnId;

    private String trackingNumber;

    private String carrier;

    @Size(max = 500)
    private String notes;
}
