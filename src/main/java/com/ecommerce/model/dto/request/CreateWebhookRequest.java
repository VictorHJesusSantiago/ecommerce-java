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
public class CreateWebhookRequest {

    @NotBlank(message = "Webhook name is required")
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank(message = "URL is required")
    @Size(max = 500)
    private String url;

    @NotBlank(message = "Events are required")
    private String events;

    @Size(max = 500)
    private String description;

    private boolean isActive;

    @Min(value = 0)
    private Integer retryCount;

    @Min(value = 1)
    @Max(value = 300)
    private Integer timeout;

    @Size(max = 500)
    private String secret;
}
