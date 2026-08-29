package com.ecommerce.event;

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
public class CategoryCreatedEvent {
    private Long categoryId;
    private String name;
    private String slug;
    private Long parentId;
    private LocalDateTime createdAt;
}
