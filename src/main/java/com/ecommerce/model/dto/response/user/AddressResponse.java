package com.ecommerce.model.dto.response.user;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AddressResponse {

    private Long id;
    private String addressType;
    private String recipientName;
    private String phoneNumber;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private boolean isDefault;
    private String deliveryInstructions;
    private LocalDateTime createdAt;
}
