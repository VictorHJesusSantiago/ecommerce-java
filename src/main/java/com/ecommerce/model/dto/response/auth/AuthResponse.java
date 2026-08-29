package com.ecommerce.model.dto.response.auth;

import lombok.*;

import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AuthResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long expiresIn;
    private String email;
    private String username;
    private String fullName;
    private List<String> roles;
    private boolean requiresTwoFactor;
    private String twoFactorSetupKey;
    private String twoFactorQrCode;
}
