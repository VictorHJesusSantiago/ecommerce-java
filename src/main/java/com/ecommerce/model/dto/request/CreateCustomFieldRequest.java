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
public class CreateCustomFieldRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank(message = "Code is required")
    @Size(min = 2, max = 100)
    private String code;

    @NotBlank(message = "Type is required")
    private String type;

    @NotBlank(message = "Entity type is required")
    private String entityType;

    private String defaultValue;
    private boolean isRequired;
    private boolean isVisible;
    private int sortOrder;

    @Size(max = 100)
    private String group;

    @Size(max = 500)
    private String description;

    @Size(max = 200)
    private String placeholder;
}
