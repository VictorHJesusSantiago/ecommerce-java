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
public class CreateImportRequest {

    @NotBlank(message = "Import type is required")
    private String type;

    @NotBlank(message = "File name is required")
    private String fileName;

    @Size(max = 200)
    private String name;

    private boolean skipFirstRow;

    private String updateExisting;

    private String columnMapping;
}
