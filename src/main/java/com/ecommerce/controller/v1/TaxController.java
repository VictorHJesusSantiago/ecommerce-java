package com.ecommerce.controller.v1;

import com.ecommerce.model.dto.response.ApiResponse;
import com.ecommerce.service.TaxService;
import com.ecommerce.service.ExchangeRateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/tax")
@RequiredArgsConstructor
@Tag(name = "Tax", description = "Tax calculation APIs")
public class TaxController {

    private final TaxService taxService;
    private final ExchangeRateService exchangeRateService;

    @PostMapping("/calculate")
    @Operation(summary = "Calculate tax for order")
    public ResponseEntity<ApiResponse<Map<String, Object>>> calculateTax(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(ApiResponse.success(taxService.calculateOrderTax(request)));
    }

    @GetMapping("/rates")
    @Operation(summary = "Get available tax rates")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getTaxRates(@RequestParam String country, @RequestParam(required = false) String state) {
        return ResponseEntity.ok(ApiResponse.success(taxService.getTaxRatesForLocation(country, state)));
    }

    @GetMapping("/rates/product/{productId}")
    @Operation(summary = "Get product tax info")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getProductTaxInfo(@PathVariable Long productId) {
        return ResponseEntity.ok(ApiResponse.success(taxService.getProductTaxInfo(productId)));
    }

    @GetMapping("/exchange-rates")
    @Operation(summary = "Get exchange rates")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getExchangeRates() {
        return ResponseEntity.ok(ApiResponse.success(exchangeRateService.getActiveExchangeRates()));
    }

    @PostMapping("/convert")
    @Operation(summary = "Convert currency amount")
    public ResponseEntity<ApiResponse<Map<String, Object>>> convertCurrency(@RequestBody Map<String, Object> request) {
        return ResponseEntity.ok(ApiResponse.success(exchangeRateService.convertCurrency(request)));
    }

    @GetMapping("/supported-currencies")
    @Operation(summary = "Get supported currencies")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getSupportedCurrencies() {
        return ResponseEntity.ok(ApiResponse.success(exchangeRateService.getSupportedCurrencies()));
    }
}
