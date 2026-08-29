package com.ecommerce.model.dto.response.newsletter;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsletterResponse {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private boolean isSubscribed;
    private boolean isConfirmed;
    private LocalDateTime subscribedAt;
    private LocalDateTime unsubscribedAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime createdAt;
}
