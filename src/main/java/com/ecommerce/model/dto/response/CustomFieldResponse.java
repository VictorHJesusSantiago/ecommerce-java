package com.ecommerce.model.dto.response;

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
public class CustomFieldResponse {
    private Long id;
    private String name;
    private String code;
    private String type;
    private String entityType;
    private String defaultValue;
    private boolean isRequired;
    private boolean isVisible;
    private int sortOrder;
    private String group;
    private String description;
    private String placeholder;
    private LocalDateTime createdAt;
}
