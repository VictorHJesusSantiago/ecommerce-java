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
public class CreateContentTemplateRequest {

    @NotBlank(message = "Template name is required")
    @Size(min = 2, max = 200)
    private String name;

    @Size(max = 200)
    private String slug;

    @Size(max = 1000)
    private String description;

    private String type;
    private String category;
    private String thumbnailUrl;
    private boolean isActive;

    private String content;
}
