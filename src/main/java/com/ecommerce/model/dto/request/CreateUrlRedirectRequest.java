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
public class CreateUrlRedirectRequest {

    @NotBlank(message = "From URL is required")
    @Size(min = 1, max = 500)
    private String fromUrl;

    @NotBlank(message = "To URL is required")
    @Size(min = 1, max = 500)
    private String toUrl;

    @Size(max = 20)
    private String type;

    private boolean isActive;
}
