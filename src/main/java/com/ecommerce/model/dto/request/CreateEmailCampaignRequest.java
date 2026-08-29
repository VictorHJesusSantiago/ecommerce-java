package com.ecommerce.model.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateEmailCampaignRequest {

    @NotBlank(message = "Campaign name is required")
    @Size(min = 2, max = 200)
    private String name;

    @NotBlank(message = "Subject is required")
    @Size(min = 2, max = 200)
    private String subject;

    @NotBlank(message = "Type is required")
    private String type;

    private String htmlContent;
    private String textContent;

    private String recipientSegment;

    private String scheduleType;

    private java.time.LocalDateTime scheduledAt;
}
