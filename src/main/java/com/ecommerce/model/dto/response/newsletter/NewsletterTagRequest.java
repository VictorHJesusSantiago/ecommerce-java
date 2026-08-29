package com.ecommerce.model.dto.response.newsletter;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsletterTagRequest {
    private String name;
    private String description;
}
