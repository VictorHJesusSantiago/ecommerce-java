package com.ecommerce.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateExportRequest {

    @NotBlank(message = "Export type is required")
    private String type;

    @NotBlank(message = "Format is required")
    private String format;

    @Size(max = 200)
    private String name;

    @Size(max = 2000)
    private String filterCriteria;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String[] columns;
}
