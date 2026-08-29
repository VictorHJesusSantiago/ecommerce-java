package com.ecommerce.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAbTestRequest {

    @NotBlank(message = "Test name is required")
    @Size(min = 2, max = 200)
    private String name;

    @Size(max = 1000)
    private String description;

    @NotBlank(message = "Type is required")
    private String type;

    private Integer trafficPercentage;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    private LocalDateTime endDate;
}
