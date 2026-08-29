package com.ecommerce.model.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VerifyEmailRequest {

    @NotBlank(message = "Verification token is required")
    private String token;
}
