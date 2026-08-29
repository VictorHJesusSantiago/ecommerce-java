package com.ecommerce.controller.admin;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.service.ShippingService;
import com.ecommerce.service.TaxService;
import com.ecommerce.service.ExchangeRateService;
import com.ecommerce.model.entity.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/settings")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Settings", description = "Admin settings management APIs")
public class AdminSettingsController {

    private final ShippingService shippingService;
    private final TaxService taxService;
    private final ExchangeRateService exchangeRateService;

    @GetMapping("/shipping/zones")
    @Operation(summary = "Get all shipping zones")
    public ResponseEntity<ApiResponse<List<ShippingZone>>> getShippingZones() {
        return ResponseEntity.ok(ApiResponse.success(shippingService.getAllShippingZones()));
    }

    @GetMapping("/shipping/methods")
    @Operation(summary = "Get all shipping methods")
    public ResponseEntity<ApiResponse<List<ShippingMethod>>> getShippingMethods() {
        return ResponseEntity.ok(ApiResponse.success(shippingService.getAllShippingMethods()));
    }

    @GetMapping("/shipping/methods/{zoneId}")
    @Operation(summary = "Get shipping methods for zone")
    public ResponseEntity<ApiResponse<List<ShippingMethod>>> getShippingMethodsForZone(@PathVariable Long zoneId) {
        return ResponseEntity.ok(ApiResponse.success(shippingService.getShippingMethodsForZone(zoneId)));
    }

    @GetMapping("/tax/rates")
    @Operation(summary = "Get all tax rates")
    public ResponseEntity<ApiResponse<List<TaxRate>>> getTaxRates() {
        return ResponseEntity.ok(ApiResponse.success(taxService.getAllTaxRates()));
    }

    @GetMapping("/tax/calculate")
    @Operation(summary = "Calculate tax for location")
    public ResponseEntity<ApiResponse<java.math.BigDecimal>> calculateTax(
            @RequestParam String country, @RequestParam String state,
            @RequestParam String zipCode, @RequestParam java.math.BigDecimal amount) {
        return ResponseEntity.ok(ApiResponse.success(taxService.calculateTax(country, state, zipCode, amount)));
    }

    @GetMapping("/exchange-rates")
    @Operation(summary = "Get all exchange rates")
    public ResponseEntity<ApiResponse<List<ExchangeRate>>> getExchangeRates() {
        return ResponseEntity.ok(ApiResponse.success(exchangeRateService.getAllExchangeRates()));
    }

    @PostMapping("/exchange-rates/refresh")
    @Operation(summary = "Refresh exchange rates")
    public ResponseEntity<ApiResponse<Void>> refreshExchangeRates() {
        exchangeRateService.refreshRates();
        return ResponseEntity.ok(ApiResponse.success("Exchange rates refreshed"));
    }
}
