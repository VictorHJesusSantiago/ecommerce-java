package com.ecommerce.model.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TwoFactorSetupRequest {

    @NotBlank(message = "Two factor code is required")
    private String code;
}
