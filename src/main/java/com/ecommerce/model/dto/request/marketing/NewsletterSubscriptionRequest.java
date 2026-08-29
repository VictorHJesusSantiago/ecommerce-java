package com.ecommerce.model.dto.request.marketing;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NewsletterSubscriptionRequest {

    private String email;

    private String firstName;

    private String lastName;

    private List<String> tags;

    private String source;
}
