package com.ecommerce.model.dto.request.marketing;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePromotionRequest {
    @NotBlank(message = "Name is required")
    private String name;

    private String slug;
    private String description;
    private String content;
    private String imageUrl;
    private String bannerUrl;
    private boolean isActive = true;
    private LocalDateTime startsAt;
    private LocalDateTime expiresAt;
    private int priority;
}
