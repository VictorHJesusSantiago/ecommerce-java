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
public class CreateSavedSearchRequest {

    @NotBlank(message = "Search name is required")
    @Size(min = 2, max = 200)
    private String name;

    @NotBlank(message = "Query is required")
    @Size(min = 1, max = 500)
    private String query;

    @Size(max = 2000)
    private String filters;

    private boolean isPublic;
}
