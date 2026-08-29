package com.ecommerce.model.dto.response.inventory;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseResponse {
    private Long id;
    private String name;
    private String code;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private String phone;
    private String email;
    private boolean isDefault;
    private boolean isActive;
    private Long totalItems;
    private Long totalValue;
}
