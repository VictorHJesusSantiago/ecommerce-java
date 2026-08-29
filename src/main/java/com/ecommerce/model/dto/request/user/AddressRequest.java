package com.ecommerce.model.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AddressRequest {

    private Long id;

    @NotBlank(message = "Address type is required")
    private String addressType;

    @NotBlank(message = "Recipient name is required")
    private String recipientName;

    private String phoneNumber;

    @NotBlank(message = "Address line 1 is required")
    private String addressLine1;

    private String addressLine2;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Postal code is required")
    private String postalCode;

    @NotBlank(message = "Country is required")
    private String country;

    private boolean isDefault;

    private String deliveryInstructions;

    private String landmark;
}
