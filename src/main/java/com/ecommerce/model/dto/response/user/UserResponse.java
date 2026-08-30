package com.ecommerce.model.dto.response.user;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserResponse {

    private Long id;
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phoneNumber;
    private String profileImageUrl;
    private String bio;
    private boolean isEnabled;
    private boolean isAccountLocked;
    private boolean isEmailVerified;
    private boolean isPhoneVerified;
    private boolean isTwoFactorEnabled;
    private String preferredLanguage;
    private String preferredCurrency;
    private String timeZone;
    private List<String> roles;
    private int addressCount;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
}
