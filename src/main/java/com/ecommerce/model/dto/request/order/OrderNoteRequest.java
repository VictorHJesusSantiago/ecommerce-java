package com.ecommerce.model.dto.request.order;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderNoteRequest {
    @NotBlank(message = "Note is required")
    private String note;

    private boolean isCustomerNote;

    private boolean isPublic;
}
