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
public class CreateStoreSettingRequest {

    @Size(max = 200)
    private String storeName;

    @Size(max = 500)
    private String storeDescription;

    @Size(max = 500)
    private String logoUrl;

    @Size(max = 500)
    private String faviconUrl;

    @Size(max = 200)
    private String contactEmail;

    @Size(max = 50)
    private String contactPhone;

    @Size(max = 500)
    private String contactAddress;

    @Size(max = 100)
    private String currency;

    @Size(max = 50)
    private String timezone;

    @Size(max = 200)
    private String metaTitle;

    @Size(max = 500)
    private String metaDescription;

    private boolean maintenanceMode;

    @Size(max = 1000)
    private String maintenanceMessage;

    private BigDecimal minimumOrderAmount;

    private BigDecimal freeShippingThreshold;

    @Size(max = 100)
    private String defaultWeightUnit;

    @Size(max = 50)
    private String taxCalculationMethod;

    private boolean guestCheckoutEnabled;
    private boolean inventoryTrackingEnabled;
    private boolean reviewsEnabled;
}
